package teamcerberus.cerberustech.computer.environments;

import java.io.File;
import java.io.PrintStream;

import teamcerberus.cerberustech.util.PrefixOutputStream;

public class JavaEnvironment implements IEnvironment{
	private PrintStream outputStream;
	
	@Override
	public void setup(int computerId) {
		// TODO Auto-generated method stub
//		outputStream = new PrefixOutputStream(new PrintStream()).setPrefix("Test");
	}

	@Override
	public PrintStream getPrintStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFileSupported(File file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getFileType() {
		// TODO Auto-generated method stub
		return null;
	}

}
