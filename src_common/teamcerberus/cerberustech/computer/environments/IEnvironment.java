package teamcerberus.cerberustech.computer.environments;

import java.io.PrintStream;
import java.io.Reader;

import teamcerberus.cerberustech.computer.Computer;

public interface IEnvironment {
	public String getName();
	
	public void setup(int computerId, Computer computer);
	
	public PrintStream getPrintStream();
	
	public String getFileType();
	
	public void runFile(String path, Reader file, Computer computer);
	
	public IInterpreter createSubInterpreter();
	
	public IInterpreter getMasterInterpreter();
}
