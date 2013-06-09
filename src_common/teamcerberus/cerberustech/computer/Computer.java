package teamcerberus.cerberustech.computer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;

import net.minecraft.client.Minecraft;
import teamcerberus.cerberustech.CerberusTech;
import teamcerberus.cerberustech.computer.environments.IEnvironment;
import teamcerberus.cerberustech.computer.environments.JavaComputerInterface;
import teamcerberus.cerberustech.computer.environments.JavaEnvironment;

public class Computer implements Runnable {
	private HashMap<String, IEnvironment>		environments;
	private File								computerFolder;
	private File								cmosFolder;
	private File								hhdFolder;
	private File								romFolder;
	private int									computerId;
	private int[][]								monitorPixels;
	private IComputerTE							te;
	private JavaComputerInterface				javaComputerInterface;
	private LinkedList<ComputerEventListener>	eventListeners;

	public Computer(int computerId, IComputerTE te) {
		this.computerId = computerId;
		this.te = te;
		javaComputerInterface = new JavaComputerInterface(this);
		eventListeners = new LinkedList<ComputerEventListener>();
		clearMonitor();
		updateSaveFolder();
		setupEnvironments();
	}

	@Override
	public void run() {
		try {
			getEnvironment("java").runFile(getFileFromROM("bios.cjava"),
					javaComputerInterface);
		} catch (Exception e) {
			e.printStackTrace();
		}
		monitorPixels = new int[200][200];
		syncMonitor();
	}

	public Reader getFileFromJar(String file)
			throws UnsupportedEncodingException {
		InputStream fileStream = Minecraft.class.getResourceAsStream(file);
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(
				fileStream, "UTF-8"));
		return fileReader;
	}

	public Reader getFileFromCMOS(String file) throws FileNotFoundException {
		return new FileReader(new File(cmosFolder, file));
	}

	public Reader getFileFromHHD(String file) throws FileNotFoundException {
		return new FileReader(new File(hhdFolder, file));
	}

	public Reader getFileFromROM(String file) throws FileNotFoundException {
		return new FileReader(new File(romFolder, file));
	}

	public void clearMonitor() {
		monitorPixels = new int[200][200];
	}

	public void syncMonitor() {
		te.sendPacket();
	}

	public void updateSaveFolder() {
		if (computerId == -1) return;
		computerFolder = new File(CerberusTech.getWorldFolder(), "computers/"
				+ computerId);
		cmosFolder = new File(computerFolder, "cmos");
		hhdFolder = new File(computerFolder, "hhd");
		romFolder = new File(computerFolder, "rom");
		computerFolder.mkdirs();
		ComputerDefault.initComputerFolder(computerFolder);
	}

	public void setupEnvironments() {
		environments = new HashMap<String, IEnvironment>();
		addEnvironment(new JavaEnvironment());
	}

	public IEnvironment getEnvironment(String enviroment) {
		return environments.get(enviroment);
	}

	public void addEnvironment(IEnvironment environment) {
		environments.put(environment.getName(), environment);
	}

	public int[][] getMonitorPixels() {
		return monitorPixels;
	}

	public void keyboardEvent(OSKeyboardEvents eventFromID,
			OSKeyboardLetters fromID) {
		for(ComputerEventListener list : eventListeners){
			list.keyboardEvent(eventFromID, fromID);
		}
	}
	
	public void addEventListener(ComputerEventListener listener){
		eventListeners.add(listener);
	}
	
	public void removeEventListener(ComputerEventListener listener){
		eventListeners.remove(listener);
	}

	public void setPixel(int x, int y, int color) {
		monitorPixels[x][y] = color;
	}
}
