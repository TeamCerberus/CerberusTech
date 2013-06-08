package teamcerberus.cerberustech.computer.environments;

import java.io.FileNotFoundException;
import java.io.Reader;

import teamcerberus.cerberustech.computer.Computer;
import teamcerberus.cerberustech.computer.ComputerEventListener;

public class JavaComputerInterface {
	private Computer	computer;

	public JavaComputerInterface(Computer computer) {
		this.computer = computer;
	}

	public void setPixel(int x, int y, int color) {
		computer.getMonitorPixels()[x][y] = color;
	}
	
	public void updateScreen(){
		computer.syncMonitor();
	}

	public void runFile(String environment, String file) throws FileNotFoundException {
		runFile(environment, "hhd", file);
	}

	public void runFile(String environment, String pos, String file) throws FileNotFoundException {
		computer.getEnvironment(environment).runFile(getFile(environment, pos, file), this);
	}
	
	public Reader getFile(String environment, String file) throws FileNotFoundException{
		return getFile(environment, "hhd", file);
	}
	
	public Reader getFile(String environment, String pos, String file) throws FileNotFoundException{
		Reader reader = null;
		if(environment.toLowerCase().equals("rom"))
			reader = computer.getFileFromROM(file);
		else if(environment.toLowerCase().equals("cmos"))
			reader = computer.getFileFromCMOS(file);
		else if(environment.toLowerCase().equals("hhd"))
			reader = computer.getFileFromHHD(file);
		else
			throw new FileNotFoundException("Computer pos not found!");
		return reader;
	}
	
	public void addEventListener(ComputerEventListener listener){
		computer.addEventListener(listener);
	}
	
	public void removeEventListener(ComputerEventListener listener){
		computer.removeEventListener(listener);
	}
}
