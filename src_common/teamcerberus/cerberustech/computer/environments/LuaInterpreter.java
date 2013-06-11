package teamcerberus.cerberustech.computer.environments;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;

import luaj.LuaScriptEngine;
import luaj.LuaValue;
import teamcerberus.cerberustech.computer.Computer;

public class LuaInterpreter implements IInterpreter, Serializable {
	private static final long	serialVersionUID	= 2222023548551603228L;
	private Computer			computer;
	private LuaScriptEngine		engine;

	public LuaInterpreter(Computer computer) throws Exception {
		this.computer = computer;
		engine = new LuaScriptEngine(computer);
	}

	@Override
	public void executeFile(Reader reader) throws Exception {
		engine.eval(reader);
	}

	@Override
	public void executeBlock(String block) throws Exception {
		engine.eval(block);
	}

	@Override
	public void setVariable(String key, Object value) throws Exception {
		engine.put(key, value);
	}

	@Override
	public Object getVariable(String key) throws Exception {
		return engine.get(key);
	}

	@Override
	public void unsetVariable(String key) throws Exception {
		setVariable(key, LuaValue.NIL);
	}

	public Computer getComputer() {
		return computer;
	}

	public IInterpreter deepClone() {
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
}
