package teamcerberus.cerberustech.computer.environments;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.HashMap;

import teamcerberus.cerberustech.computer.Computer;
import teamcerberus.cerberustech.computer.ComputerEventListener;

public class JavaComputerInterface {
	private Computer						computer;

	public JavaComputerInterface(Computer computer) {
		this.computer = computer;
	}

	public void setPixel(int x, int y, int color) {
		computer.setPixel(x, y, color);
	}

	public void updateScreen() {
		computer.syncMonitor();
	}

	public void runFile(String environment, String file)
			throws FileNotFoundException {
		runFile(environment, "hhd", file);
	}

	public void runFile(String environment, String pos, String file)
			throws FileNotFoundException {
		computer.getEnvironment(environment).runFile(
				getFile(pos, file), this);
	}

	public Reader getFile(String file)
			throws FileNotFoundException {
		return getFile("hhd", file);
	}

	public Reader getFile(String pos, String file)
			throws FileNotFoundException {
		Reader reader = null;
		if (pos.toLowerCase().equals("rom")) reader = computer
				.getFileFromROM(file);
		else if (pos.toLowerCase().equals("cmos")) reader = computer
				.getFileFromCMOS(file);
		else if (pos.toLowerCase().equals("hhd")) reader = computer
				.getFileFromHHD(file);
		else throw new FileNotFoundException("Computer pos not found!");
		return reader;
	}

	public void addEventListener(ComputerEventListener listener) {
		computer.addEventListener(listener);
	}

	public void removeEventListener(ComputerEventListener listener) {
		computer.removeEventListener(listener);
	}
	
	public void mountStaticJavaVariable(String name, Object instance){
		((JavaEnvironment)computer.getEnvironment("java")).setVariable(name, instance);
	}
}
