package teamcerberus.cerberustech.computer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import teamcerberus.cerberustech.CerberusTech;

public class ComputerIdGenerator {
	public static int getNextID() {
		int id = getLastID() + 1;
		setLastID(id);
		return id;
	}

	public static int getLastID() {
		int id = 0;
		try {
			FileInputStream fstream = new FileInputStream(getSaveFile());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			id = Integer.parseInt(br.readLine());
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Read Error: " + e.getMessage());
		}
		return id;
	}

	public static void setLastID(int id) {
		try {
			FileWriter fstream = new FileWriter(getSaveFile());
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("" + id);
			out.close();
		} catch (Exception e) {
			System.err.println("Write Error: " + e.getMessage());
		}
	}

	public static String getSaveFile() {
		File f = new File(CerberusTech.getWorldFolder(), "computers/lastid");
		f.getParentFile().mkdirs();
		return f.getPath();
	}
}
