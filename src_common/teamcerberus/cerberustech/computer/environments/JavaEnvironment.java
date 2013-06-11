package teamcerberus.cerberustech.computer.environments;

import java.io.PrintStream;
import java.io.Reader;
import java.net.URL;

import teamcerberus.cerberustech.computer.Computer;
import beanshell.BshClassLoader;
import beanshell.ClassManagerImpl;
import beanshell.EvalError;
import beanshell.Interpreter;

public class JavaEnvironment implements IEnvironment {
	private JavaInterpreter masterInterpreter;
	
	private Interpreter interpreter;
	private JavaComputerInterface computerInterface;
	
	@Override
	public String getName() {
		return "java";
	}

	@Override
	public void setup(int computerId, Computer computer) {
		try{
//			computerInterface = new JavaComputerInterface(computer);
			interpreter = new Interpreter();
			interpreter.setClassLoader(new BshClassLoader(
					new ClassManagerImpl(), new URL[] {}));
			interpreter.set("computer", computerInterface);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public PrintStream getPrintStream() {
		return null;
	}
	
	@Override
	public String getFileType() {
		return ".cjava";
	}

	@Override
	public void runFile(String path, Reader file, Computer computer) {
		try {
			interpreter.eval(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setVariable(String variable, Object instance){
		try {
			interpreter.set(variable, instance);
		} catch (EvalError e) {
			e.printStackTrace();
		}
	}

	@Override
	public IInterpreter createSubInterpreter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IInterpreter getMasterInterpreter() {
		// TODO Auto-generated method stub
		return null;
	}
}
