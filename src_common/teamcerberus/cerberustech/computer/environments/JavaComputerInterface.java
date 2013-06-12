package teamcerberus.cerberustech.computer.environments;

import java.io.FileNotFoundException;
import java.io.Reader;

public class JavaComputerInterface {
	private JavaInterpreter	javaInterpreter;

	public JavaComputerInterface(JavaInterpreter javaInterpreter) {
		this.javaInterpreter = javaInterpreter;
	}

	public void setPixel(int x, int y, int color) {
		javaInterpreter.getComputer().setPixel(x, y, color);
	}

	public void updateScreen() {
		javaInterpreter.getComputer().syncMonitor();
	}
	
	public Reader getFile(String pos, String file) throws FileNotFoundException {
		Reader reader = null;
		if (pos.toLowerCase().equals("rom")) reader = javaInterpreter
				.getComputer().getFileFromROM(file);
		else if (pos.toLowerCase().equals("cmos")) reader = javaInterpreter
				.getComputer().getFileFromCMOS(file);
		else if (pos.toLowerCase().equals("hhd")) reader = javaInterpreter
				.getComputer().getFileFromHHD(file);
		else throw new FileNotFoundException("Computer pos not found!");
		return reader;
	}
	
	public Environment getEnvironment(String name){
		return javaInterpreter.getComputer().getEnvironment(name);
	}
	
	public JavaInterpreter getCurrentInterpreter(){
		return javaInterpreter;
	}
	
	public void loadExtraEnvironments(){
		javaInterpreter.getComputer().loadExtraEnvironments();
	}
}
