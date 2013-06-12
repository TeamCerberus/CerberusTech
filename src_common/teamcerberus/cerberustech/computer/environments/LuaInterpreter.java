package teamcerberus.cerberustech.computer.environments;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Map.Entry;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.script.SimpleScriptContext;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import teamcerberus.cerberustech.computer.Computer;

public class LuaInterpreter implements IInterpreter, Serializable {
	private static final long	serialVersionUID	= 2222023548551603228L;
	private Computer			computer;
	private ScriptContext		defaultContext;
	private final Globals		globals;

	public LuaInterpreter(Computer computer) throws Exception {
		this.computer = computer;
		globals = JsePlatform.debugGlobals();

		String[] removeList = new String[] { "collectgarbage", "dofile",
				"load", "loadfile", "module", "require", "package", "io", "os",
				"luajava", "debug", "newproxy" };
		for (String file : removeList) {
			globals.set(file, LuaValue.NIL);
		}
		LuaComputerInterface.bind(globals, computer);

		defaultContext = new SimpleScriptContext();
		defaultContext.setBindings(new SimpleBindings(),
				ScriptContext.ENGINE_SCOPE);

		globals.set(LuaValue.INDEX, globals);
	}

	@Override
	public void executeFile(Reader reader) throws Exception {
		CompiledLuaBlock compiled = null;
		InputStream ris = new Utf8Encoder(reader);
		try {
			final LuaFunction f = LoadState.load(ris, "script", "bt", globals);
			if (f.isclosure()) {
				final Prototype p = f.checkclosure().p;
				compiled = new CompiledLuaBlock() {
					@Override
					protected LuaFunction newFunctionInstance(LuaTable env) {
						return new LuaClosure(p, env);
					}
				};
			} else {
				final Class<? extends LuaFunction> c = f.getClass();
				compiled = new CompiledLuaBlock() {
					@Override
					protected LuaFunction newFunctionInstance(LuaTable env)
							throws ScriptException {
						try {
							LuaFunction f = c.newInstance();
							f.initupvalue1(env);
							return f;
						} catch (Exception e) {
							throw new ScriptException("instantiation failed: "
									+ e.toString());
						}
					}
				};
			}
		} catch (Exception e) {
			throw e;
		} finally {
			ris.close();
		}
		compiled.eval(defaultContext);
	}

	@Override
	public void executeBlock(String block) throws Exception {
		executeFile(new StringReader(block));
	}

	@Override
	public void setVariable(String key, Object value) throws Exception {
		Bindings b = defaultContext.getBindings(ScriptContext.ENGINE_SCOPE);
		b.put(key, value);
	}

	@Override
	public Object getVariable(String key) throws Exception {
		Bindings b = defaultContext.getBindings(ScriptContext.ENGINE_SCOPE);
		return b.get(key);
	}

	@Override
	public void unsetVariable(String key) throws Exception {
		setVariable(key, LuaValue.NIL);
	}

	public Computer getComputer() {
		return computer;
	}

	@Override
	public IInterpreter createSubInterpreter() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (JavaInterpreter) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	abstract protected class CompiledLuaBlock {
		abstract protected LuaFunction newFunctionInstance(LuaTable env)
				throws ScriptException;

		public Object eval(ScriptContext context) throws ScriptException {
			Bindings b = context.getBindings(ScriptContext.ENGINE_SCOPE);
			BindingsGlobals env = new BindingsGlobals(b);
			LuaFunction f = newFunctionInstance(env);
			try {
				return f.invoke(LuaValue.NONE);
			} finally {
				env.copyout();
			}
		}
	}

	class BindingsGlobals extends Globals {
		final Bindings	b;

		BindingsGlobals(Bindings b) {
			this.b = b;
			setmetatable(globals);
			debuglib = globals.debuglib;
			for (Entry<String, Object> e : b.entrySet()) {
				rawset(toLua(e.getKey()), toLua(e.getValue()));
			}
		}

		void copyout() {
			b.clear();
			for (Varargs v = next(LuaValue.NIL); !v.arg1().isnil(); v = next(v
					.arg1())) {
				b.put(toJava(v.arg1()).toString(), toJava(v.arg(2)));
			}
		}
	}

	LuaValue toLua(Object javaValue) {
		return javaValue == null ? LuaValue.NIL
				: javaValue instanceof LuaValue ? (LuaValue) javaValue
						: CoerceJavaToLua.coerce(javaValue);
	}

	Object toJava(LuaValue v) {
		switch (v.type()) {
			case LuaValue.TNIL:
				return null;
			case LuaValue.TSTRING:
				return v.tojstring();
			case LuaValue.TUSERDATA:
				return v.checkuserdata(Object.class);
			case LuaValue.TNUMBER:
				return v.isinttype() ? (Object) new Integer(v.toint())
						: (Object) new Double(v.todouble());
			default:
				return v;
		}
	}

	private final class Utf8Encoder extends InputStream {
		private final Reader	r;
		private final int[]		buf	= new int[2];
		private int				n;

		private Utf8Encoder(Reader r) {
			this.r = r;
		}

		@Override
		public int read() throws IOException {
			if (n > 0) { return buf[--n]; }
			int c = r.read();
			if (c < 0x80) { return c; }
			n = 0;
			if (c < 0x800) {
				buf[n++] = 0x80 | c & 0x3f;
				return 0xC0 | c >> 6 & 0x1f;
			} else {
				buf[n++] = 0x80 | c & 0x3f;
				buf[n++] = 0x80 | c >> 6 & 0x3f;
				return 0xE0 | c >> 12 & 0x0f;
			}
		}
	}

	@Override
	public String getFileType() {
		return ".clua";
	}
}
