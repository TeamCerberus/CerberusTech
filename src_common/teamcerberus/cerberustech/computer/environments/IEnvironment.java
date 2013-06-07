package teamcerberus.cerberustech.computer.environments;

import java.io.File;
import java.io.PrintStream;
import java.io.Reader;

public interface IEnvironment {
	public String getName();
	
	public void setup(int computerId);
	
	public PrintStream getPrintStream();
	
	public boolean isFileSupported(File file);
	
	public String getFileType();
	
	public void runFile(Reader file, JavaComputerInterface computerInterface);
}
