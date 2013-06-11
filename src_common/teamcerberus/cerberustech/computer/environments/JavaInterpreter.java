package teamcerberus.cerberustech.computer.environments;

import java.net.URL;

import teamcerberus.cerberustech.computer.Computer;
import beanshell.BshClassLoader;
import beanshell.ClassManagerImpl;
import beanshell.Interpreter;

public class JavaInterpreter implements IInterpreter {
	private Interpreter	beanshellInterpreter;
	private Computer computer;
	private JavaComputerInterface computerInterface;
	
	public JavaInterpreter() throws Exception {
		beanshellInterpreter = new Interpreter();
		beanshellInterpreter.setClassLoader(new BshClassLoader(
				new ClassManagerImpl(), new URL[] {}));
		computerInterface = new JavaComputerInterface(this);
		beanshellInterpreter.set("computer", computerInterface);
	}
	
	public Computer getComputer(){
		return computer;
	}
	
}
