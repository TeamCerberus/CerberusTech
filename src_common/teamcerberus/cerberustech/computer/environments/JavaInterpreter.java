package teamcerberus.cerberustech.computer.environments;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;

import teamcerberus.cerberustech.computer.Computer;
import bsh.Interpreter;
import bsh.classpath.BshClassLoader;
import bsh.classpath.ClassManagerImpl;

public class JavaInterpreter implements IInterpreter, Serializable {
	private static final long		serialVersionUID	= 7412745232976447886L;
	private Interpreter				beanshellInterpreter;
	private Computer				computer;
	private JavaComputerInterface	computerInterface;

	public JavaInterpreter(Computer computer) throws Exception {
		this.computer = computer;
		beanshellInterpreter = new Interpreter();
		beanshellInterpreter.setClassLoader(new BshClassLoader(
				new ClassManagerImpl(), new URL[] {}));
		computerInterface = new JavaComputerInterface(this);
		beanshellInterpreter.set("computer", computerInterface);
	}

	@Override
	public void executeFile(Reader reader) throws Exception {
		beanshellInterpreter.eval(reader);
	}

	@Override
	public void executeBlock(String block) throws Exception {
		beanshellInterpreter.eval(block);
	}

	@Override
	public void setVariable(String key, Object value) throws Exception {
		beanshellInterpreter.set(key, value);
	}

	@Override
	public Object getVariable(String key) throws Exception {
		return beanshellInterpreter.get(key);
	}

	@Override
	public void unsetVariable(String key) throws Exception {
		beanshellInterpreter.unset(key);
	}

	public Computer getComputer() {
		return computer;
	}

	@Override
	public IInterpreter createSubInterpreter() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (JavaInterpreter) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	@Override
	public String getFileType() {
		return ".cjava";
	}
}
