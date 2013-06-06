package teamcerberus.cerberustech.computer.environments;

import java.io.File;
import java.io.PrintStream;

public interface IEnvironment {
	public void setup(int computerId);
	
	public PrintStream getPrintStream();
	
	public boolean isFileSupported(File file);
	
	public String getFileType();
}
