package teamcerberus.cerberustech.computer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;

import teamcerberus.cerberustech.CerberusTech;

public class IDGenerator {
	public static HashMap<String, Integer> ids;
	
	public static int getNextID(String name) {
		int id = getLastID(name) + 1;
		setLastID(name, id);
		return id;
	}

	public static int getLastID(String name) {
		if(ids.containsKey(name))
			return ids.get(name);
		return 0;
	}
	
	public static void readIDs() {
		try {
			ids = new HashMap<String, Integer>();
			FileInputStream fstream = new FileInputStream(getSaveFile());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String l;
			while((l = br.readLine()) != null){
				String[] p = l.split("=");
				ids.put(p[0], Integer.parseInt(p[1]));
			}
			in.close();
		} catch (Exception e) {
		}
	}
	
	public static void saveIDs(){
		try {
			FileWriter fstream = new FileWriter(getSaveFile());
			BufferedWriter out = new BufferedWriter(fstream);
			boolean pf = false;
			for(Entry<String, Integer> e : ids.entrySet()){
				if(pf)
					out.newLine();
				out.write(e.getKey()+"="+e.getValue());
				pf = true;
			}
			out.close();
		} catch (Exception e) {
		}
	}
	
	public static void setLastID(String name, int id) {
		ids.put(name, id);
		saveIDs();
	}

	public static String getSaveFile() {
		File f = new File(CerberusTech.getWorldFolder(), "computers/lastid");
		f.getParentFile().mkdirs();
		return f.getPath();
	}
	
	static{
		readIDs();
	}
}
