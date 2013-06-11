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
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import teamcerberus.cerberustech.CerberusTech;
import teamcerberus.cerberustech.computer.environments.IEnvironment;
import teamcerberus.cerberustech.computer.environments.JavaEnvironment;
import teamcerberus.cerberustech.computer.environments.LuaEnvironment;

public class Computer implements Runnable {
	private HashMap<String, IEnvironment>		environments;
	private File								computerFolder;
	private File								cmosFolder;
	private File								hhdFolder;
	private File								romFolder;
	private int									computerId;
	private int[][]								monitorPixels;
	private IComputerTE							te;
	private LinkedList<ComputerEventListener>	eventListeners;

	public Computer(int computerId, IComputerTE te) {
		this.computerId = computerId;
		this.te = te;
		eventListeners = new LinkedList<ComputerEventListener>();
		clearMonitor();
		updateSaveFolder();
	}

	@Override
	public void run() {
		try {
			setupEnvironments();
			runFile("rom", "bios.cjava");
		} catch (Exception e) {
			e.printStackTrace();
		}
		monitorPixels = new int[200][200];
		syncMonitor();
	}

	public void runFile(String pos, String file) throws Exception{
		getEnvironmentForFile(file).getMasterInterpreter().executeFile(getFileFromPos(pos, file));
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

	public Reader getFileFromPos(String pos, String file)
			throws FileNotFoundException {
		Reader reader = null;
		if (pos.toLowerCase().equals("rom")) reader = getFileFromROM(file);
		else if (pos.toLowerCase().equals("cmos")) reader = getFileFromCMOS(file);
		else if (pos.toLowerCase().equals("hhd")) reader = getFileFromHHD(file);
		else throw new FileNotFoundException("Computer pos not found!");
		return reader;
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
		addEnvironment(new LuaEnvironment());

		for (Entry<String, IEnvironment> entry : environments.entrySet()) {
			entry.getValue().setup(computerId, this);
		}
	}

	public IEnvironment getEnvironment(String enviroment) {
		return environments.get(enviroment);
	}

	public void addEnvironment(IEnvironment environment) {
		environments.put(environment.getName(), environment);
	}

	public IEnvironment getEnvironmentForFile(String file) {
		String ext = "";
		int pos = file.lastIndexOf(".");
		if (pos != -1) ext = file.substring(pos, file.length());
		for (Entry<String, IEnvironment> entry : environments.entrySet()) {
			if (entry.getValue().getFileType().equals(ext)) return entry
					.getValue();
		}
		return null;
	}

	public int[][] getMonitorPixels() {
		return monitorPixels;
	}

	public void keyboardEvent(OSKeyboardEvents eventFromID,
			OSKeyboardLetters fromID) {
		for (ComputerEventListener list : eventListeners) {
			list.keyboardEvent(eventFromID, fromID);
		}
	}

	public void addEventListener(ComputerEventListener listener) {
		eventListeners.add(listener);
	}

	public void removeEventListener(ComputerEventListener listener) {
		eventListeners.remove(listener);
	}

	public void setPixel(int x, int y, int color) {
		monitorPixels[x][y] = color;
	}
}
