package teamcerberus.cerberustech.computer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ComputerDefault {
	public static String biosFile =
	"public void fillScreen(Color color){\n"+
	"\tfor(int y = 0; y < 200; y++){\n"+
	"\t\tfor(int x = 0; x < 200; x++){\n"+
	"\t\t\tcomputer.setPixel(x, y, color.getRGB());\n"+
	"\t\t\tcomputer.updateScreen();\n"+
	"\t\t}\n"+
	"\t}\n"+
	"}\n"+
	"\n"+
	"public String getName(){\n"+
	"\treturn \"CerbBIOS\";\n"+
	"}\n"+
	"\n"+
	"public String getVersion(){\n"+
	"\treturn \"1.0.0\";\n"+
	"}\n"+
	"\n"+
	"//--------> Run Point <--------\n"+
	"System.out.println(\"Welcome to \"+getName()+\"(\"+getVersion()+\")\");\n"+
	"fillScreen(Color.gray);\n"+
	"Thread.sleep(500);\n"+
	"fillScreen(Color.black);\n"+
	"computer.mountStaticJavaVariable(\"bios\", this);\n"+
	"//computer.runFile(\"java\", \"rom\", \"os.cjava\");";
	
	public static void initComputerFolder(File computerFolder){
		File cmosFolder = new File(computerFolder, "cmos");
		File hhdFolder = new File(computerFolder, "hhd");
		File romFolder = new File(computerFolder, "rom");
		cmosFolder.mkdirs();
		hhdFolder.mkdirs();
		romFolder.mkdirs();
		checkFile(new File(romFolder, "bios.cjava"), biosFile);
		
	}
	
	private static void checkFile(File file, String defaultFile){
		if(!file.exists()){
			try{
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writer.write(defaultFile);
				writer.close();
				System.out.println("File Created: "+file);
			}catch(Exception e){
				System.out.println("File Creation Failed: "+file);
			}
		}
	}
}
