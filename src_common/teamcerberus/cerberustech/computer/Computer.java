package teamcerberus.cerberustech.computer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import net.minecraft.client.Minecraft;

import teamcerberus.cerberustech.CerberusTech;
import teamcerberus.cerberustech.computer.environments.IEnvironment;
import teamcerberus.cerberustech.computer.environments.JavaEnvironment;

public class Computer implements Runnable {
	private HashMap<String, IEnvironment>	environments;
	private File							computerFolder;
	private int								computerId;
	private int[][]							monitorPixels;
	private IComputerTE						te;

	public Computer(int computerId, IComputerTE te) {
		this.computerId = computerId;
		this.te = te;
		clearMonitor();
		updateSaveFolder();
		setupEnvironments();
	}

	@Override
	public void run() {
		 try {
			environments.get("java").runFile(getFileFromJar("/CerberusOS/CerberusOS.ctj"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		syncMonitor();
	}

	public Reader getFileFromJar(String file) throws UnsupportedEncodingException {
		InputStream fileStream = Minecraft.class.getResourceAsStream(file);
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(
				fileStream, "UTF-8"));
		return fileReader;
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
		computerFolder.mkdirs();
	}

	public void setupEnvironments() {
		environments = new HashMap<String, IEnvironment>();
		addEnvironment(new JavaEnvironment());
	}

	public void addEnvironment(IEnvironment environment) {
		environments.put(environment.getName(), environment);
	}

	public int[][] getMonitorPixels() {
		return monitorPixels;
	}
}
