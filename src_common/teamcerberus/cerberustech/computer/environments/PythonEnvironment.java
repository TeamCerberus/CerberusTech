package teamcerberus.cerberustech.computer.environments;

import java.io.PrintStream;

import teamcerberus.cerberustech.computer.Computer;

public class PythonEnvironment implements IEnvironment{
	private PythonInterpreter masterInterpreter;
	
	@Override
	public String getName() {
		return "python";
	}

	@Override
	public void setup(int computerId, Computer computer) {
		try {
			masterInterpreter = new PythonInterpreter(computer);
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
		return ".cpython";
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
