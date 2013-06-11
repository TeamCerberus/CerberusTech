package teamcerberus.cerberustech.computer.environments;

import java.io.PrintStream;

import teamcerberus.cerberustech.computer.Computer;

public interface IEnvironment {
	public String getName();
	
	public void setup(int computerId, Computer computer);
	
	public PrintStream getPrintStream();
	
	public String getFileType();
	
	public IInterpreter createSubInterpreter();
	
	public IInterpreter getMasterInterpreter();
}
