package teamcerberus.cerberustech.computer.environments;

import java.io.PrintStream;

import teamcerberus.cerberustech.computer.Computer;

public class JavaEnvironment implements IEnvironment {
	private JavaInterpreter masterInterpreter;
	
	@Override
	public String getName() {
		return "java";
	}
	
	@Override
	public String getFileType() {
		return ".cjava";
	}
	
	@Override
	public void setup(int computerId, Computer computer) {
		try {
			masterInterpreter = new JavaInterpreter(computer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PrintStream getPrintStream() {
		return null;
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
