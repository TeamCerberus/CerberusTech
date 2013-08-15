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
import teamcerberus.cerberustech.computer.environments.Environment;
import teamcerberus.cerberustech.computer.environments.JavaInterpreter;
import teamcerberus.cerberustech.computer.environments.LuaInterpreter;
import teamcerberus.cerberustech.computer.environments.PythonInterpreter;
import teamcerberus.cerberustech.computer.event.EventKeyPush;
import teamcerberus.cerberustech.computer.event.EventKeyRelease;
import teamcerberus.cerberustech.plugin.PluginRegistry;

public class Computer implements Runnable {
	private HashMap<String, Environment> environments;
	private File computerFolder;
	private File cmosFolder;
	private File hhdFolder;
	private File romFolder;
	private int computerId;
	private int[][] monitorPixels;
	private IComputerTE te;
	private LinkedList<ComputerEventListener> eventListeners;
	private int[] redstoneOutput;
	private int[] redstoneInput;

	public Computer(int computerId, IComputerTE te) {
		this.computerId = computerId;
		this.te = te;
		eventListeners = new LinkedList<ComputerEventListener>();
		clearMonitor();
		updateSaveFolder();
		redstoneOutput = new int[LocalDirection.values().length];
		redstoneInput = new int[LocalDirection.values().length];
	}
	

	@Override
	public void run() {
		try {
			environments = new HashMap<String, Environment>();
			loadEnvironment(new Environment("java", new JavaInterpreter(this)));
			runFile("rom", "bios.cjava");
		} catch (Exception e) {
			e.printStackTrace();
		}
		monitorPixels = new int[200][200];
		syncMonitor();
	}

	public void loadExtraEnvironments() {
		try {
			loadEnvironment(new Environment("lua", new LuaInterpreter(this)));
			loadEnvironment(new Environment("python", new PythonInterpreter(
					this)));
			PluginRegistry.loadEnvironments(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runFile(String pos, String file) throws Exception {
		getEnvironmentForFile(file).getMasterInterpreter().executeFile(
				getFileFromPos(pos, file));
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
		if (pos.toLowerCase().equals("rom")) {
			reader = getFileFromROM(file);
		} else if (pos.toLowerCase().equals("cmos")) {
			reader = getFileFromCMOS(file);
		} else if (pos.toLowerCase().equals("hhd")) {
			reader = getFileFromHHD(file);
		} else {
			throw new FileNotFoundException("Computer pos not found!");
		}
		return reader;
	}

	public void clearMonitor() {
		monitorPixels = new int[200][200];
	}

	public void syncMonitor() {
		te.sendPacket();
	}

	public void updateSaveFolder() {
		if (computerId == -1) {
			return;
		}
		computerFolder = new File(CerberusTech.getWorldFolder(), "computers/"
				+ computerId);
		cmosFolder = new File(computerFolder, "cmos");
		hhdFolder = new File(computerFolder, "hhd");
		romFolder = new File(computerFolder, "rom");
		computerFolder.mkdirs();
		ComputerDefault.initComputerFolder(computerFolder);
	}

	public Environment getEnvironment(String enviroment) {
		return environments.get(enviroment);
	}

	public void loadEnvironment(Environment environment) {
		environments.put(environment.getName(), environment);
	}

	public Environment getEnvironmentForFile(String file) {
		String ext = "";
		int pos = file.lastIndexOf(".");
		if (pos != -1) {
			ext = file.substring(pos, file.length());
		}
		for (Entry<String, Environment> entry : environments.entrySet()) {
			if (entry.getValue().getFileType().equals(ext)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public int[][] getMonitorPixels() {
		return monitorPixels;
	}

	public void keyboardEvent(OSKeyboardEvents eventFromID,
			OSKeyboardLetters fromID) {
		for (ComputerEventListener list : eventListeners) {
			list.handleEvent(eventFromID == OSKeyboardEvents.KeyPushed ? new EventKeyPush(fromID) : new EventKeyRelease(fromID));
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

	public int getRedstoneOutput(LocalDirection side) {
		return redstoneOutput[side.id];
	}

	public void setRedstoneOutput(LocalDirection side, int value) {
		redstoneOutput[side.id] = value;
		te.notifyNeighbors();
	}

	public int getRedstoneInput(LocalDirection side) {
		return redstoneInput[side.id];
	}

	public void pollRedstoneInputs() {
		int meta = te.getSideFacing();
		pollRedstoneInputSide(0, meta, 0, 1, 0, 1);
		pollRedstoneInputSide(1, meta, 0, -1, 0, 0);
		pollRedstoneInputSide(2, meta, 0, 0, 1, 3);
		pollRedstoneInputSide(3, meta, 0, 0, -1, 2);
		pollRedstoneInputSide(4, meta, 1, 0, 0, 5);
		pollRedstoneInputSide(5, meta, -1, 0, 0, 4);
	}
	
	public void pollRedstoneInputSide(int ws, int meta, int x, int y, int z, int os) {
		LocalDirection dir = LocalDirection.convertWorldSide(ws, meta);
		redstoneInput[dir.id] = te.isBlockProvidingPowerOnSide(x, y, z, os);
	}
}
