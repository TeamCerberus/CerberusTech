package teamcerberus.cerberustech.computer.environments;

import java.io.PrintStream;

import teamcerberus.cerberustech.computer.Computer;

public class LuaEnvironment implements IEnvironment {
	private LuaInterpreter masterInterpreter;

	@Override
	public String getName() {
		return "lua";
	}

	@Override
	public void setup(int computerId, Computer computer) {
		try {
			masterInterpreter = new LuaInterpreter(computer);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public IInterpreter createSubInterpreter() {
		return masterInterpreter.deepClone();
	}

	@Override
	public IInterpreter getMasterInterpreter() {
		return masterInterpreter;
	}
}
