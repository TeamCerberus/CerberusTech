package teamcerberus.cerberustech.computer.environments;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;

import luaj.Globals;
import luaj.JsePlatform;
import luaj.LuaC;
import luaj.LuaThread;
import luaj.LuaValue;
import luaj.OneArgFunction;
import luaj.ZeroArgFunction;


import teamcerberus.cerberustech.computer.Computer;

public class LuaEnvironment implements IEnvironment {
	private Globals	instance;
	private boolean	aborted;

	@Override
	public String getName() {
		return "lua";
	}

	@Override
	public void setup(int computerId, Computer computer) {
		instance = JsePlatform.debugGlobals();

		LuaValue coroutine = instance.get("coroutine");
		final LuaValue native_coroutine_create = coroutine.get("create");
		LuaValue debug = instance.get("debug");
		final LuaValue debug_sethook = debug.get("sethook");

		coroutine.set("create", new OneArgFunction() {
			public LuaValue call(LuaValue value) {
				final LuaThread thread = native_coroutine_create.call(value)
						.checkthread();
				debug_sethook.invoke(new LuaValue[] { thread,
						new ZeroArgFunction() {
							public LuaValue call() {
								if (aborted) thread.yield(LuaValue.NIL);
								return LuaValue.NIL;
							}
						}, LuaValue.NIL, LuaValue.valueOf(100000) });

				return thread;
			}
		});
		
		System.out.println(Color.green.getRGB());

		String[] removeList = new String[] { "collectgarbage", "dofile",
				"load", "loadfile", "module", "require", "package", "io", "os",
				"luajava", "debug", "newproxy" };
		for (String file : removeList)
			instance.set(file, LuaValue.NIL);
		LuaComputerInterface.bind(instance, computer);
	}

	@Override
	public PrintStream getPrintStream() {
		return null;
	}

	@Override
	public String getFileType() {
		return ".clua";
	}

	@Override
	public void runFile(String path, Reader file, Computer computer) {
		try {
			String programText = null;
			BufferedReader reader = new BufferedReader(file);
			StringBuilder fileText = new StringBuilder("");
			String line = reader.readLine();
			while (line != null) {
				fileText.append(line);
				line = reader.readLine();
				if (line != null) {
					fileText.append("\n");
				}
			}
			programText = fileText.toString();
			InputStream is = new ByteArrayInputStream(programText.getBytes());
			LuaValue chunk = LuaC.instance
					.load(is, getFileName(path), instance);
			chunk.call(LuaValue.ZERO, LuaValue.ONE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String stripFolder(String str) {
		int pos = str.lastIndexOf("/");
		if (pos == -1) return str;
		return str.substring(pos + 1, str.length());
	}

	public static String stripExtension(String str) {
		int pos = str.lastIndexOf(".");
		if (pos == -1) return str;
		return str.substring(0, pos);
	}

	public static String getFileName(String str) {
		return stripFolder(stripExtension(str));
	}
}
