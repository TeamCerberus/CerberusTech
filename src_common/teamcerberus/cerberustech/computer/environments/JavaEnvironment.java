package teamcerberus.cerberustech.computer.environments;

import java.io.File;
import java.io.PrintStream;
import java.io.Reader;
import java.net.URL;

import beanshell.Interpreter;
import beanshell.classpath.BshClassLoader;
import beanshell.classpath.ClassManagerImpl;

public class JavaEnvironment implements IEnvironment {
//	private PrintStream	outputStream;

	@Override
	public String getName() {
		return "java";
	}

	@Override
	public void setup(int computerId) {

	}

	@Override
	public PrintStream getPrintStream() {
		return null;
	}

	@Override
	public boolean isFileSupported(File file) {
		return file.getName().endsWith(getFileType());
	}

	@Override
	public String getFileType() {
		return ".java";
	}

	@Override
	public void runFile(Reader file, JavaComputerInterface computerInterface) {
		try {
			Interpreter interpreter = new Interpreter();
			interpreter.setClassLoader(new BshClassLoader(
					new ClassManagerImpl(), new URL[] {}));
			interpreter.set("computer", computerInterface);
			interpreter.eval(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
