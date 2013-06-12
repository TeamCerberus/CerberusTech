package teamcerberus.cerberustech.computer.environments;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;

import teamcerberus.cerberustech.computer.Computer;


public class PythonInterpreter implements IInterpreter, Serializable {
	private static final long	serialVersionUID	= -8346828344749539308L;
	private org.python.util.PythonInterpreter jythonInterpreter;
	
	public PythonInterpreter(Computer computer) throws Exception{
		jythonInterpreter = new org.python.util.PythonInterpreter();
		
		setVariable("computer", new PythonComputerInterface());
	}
	
	@Override
	public void executeFile(Reader reader) throws Exception {
		String programText = null;
		BufferedReader reader1 = new BufferedReader(reader);
		StringBuilder fileText = new StringBuilder("");
		String line = reader1.readLine();
		while (line != null) {
			fileText.append(line);
			line = reader1.readLine();
			if (line != null) {
				fileText.append("\n");
			}
		}
		programText = fileText.toString();
		executeBlock(programText);
		
	}

	@Override
	public void executeBlock(String block) throws Exception {
		jythonInterpreter.exec(block);
	}

	@Override
	public void setVariable(String key, Object value) throws Exception {
		jythonInterpreter.set(key, value);
	}

	@Override
	public Object getVariable(String key) throws Exception {
		return jythonInterpreter.get(key);
	}

	@Override
	public void unsetVariable(String key) throws Exception {
		setVariable(key, null);
	}
	
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
		return ".cpython";
	}
}
