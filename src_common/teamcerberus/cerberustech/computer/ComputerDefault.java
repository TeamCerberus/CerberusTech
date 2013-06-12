package teamcerberus.cerberustech.computer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ComputerDefault {
	public static String	biosFile	= "//--------> Graphics Setup <--------\nimport java.awt.image.BufferedImage;\nBufferedImage canvas = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);\nGraphics2D graphics = canvas.createGraphics();\nFont font = new Font(\"monospaced\", Font.PLAIN, 10);\ncomputer.getCurrentInterpreter().setVariable(\"display_canvas\", canvas);\ncomputer.getCurrentInterpreter().setVariable(\"display_graphics\", graphics);\ncomputer.getCurrentInterpreter().setVariable(\"display_font\", font);\n\n//--------> Public Methods <--------\n\npublic void fillScreen(Color color){\n\tgraphics.setColor(color);\n\tgraphics.fillRect(0, 0, 200, 200);\n}\n\npublic void drawText(int x, int y, String text, Color color){\n\tgraphics.setFont(font);\n\tgraphics.setColor(color);\n\tgraphics.drawString(text, x, y);\n}\n\npublic void updateScreen(){\n\tfor (int x = 0; x < canvas.getWidth(); x++)\n\t\tfor (int y = 0; y < canvas.getHeight(); y++)\n\t\t\tcomputer.setPixel(x, y, canvas.getRGB(x, y));\n\tcomputer.updateScreen();\n}\n\npublic String getName(){\n\treturn \"CerbBIOS\";\n}\n\npublic String getVersion(){\n\treturn \"1.0.0\";\n}\n\n//--------> Run Point <--------\nSystem.out.println(\"Welcome to \"+getName()+\"(\"+getVersion()+\")\");\nfillScreen(Color.gray);\ndrawText(85, 197, \"Loading \"+getName()+\"...\", Color.white);\nupdateScreen();\ncomputer.loadExtraEnvironments();\nThread.sleep(500);\nfillScreen(Color.black);\ncomputer.updateScreen();\ncomputer.getCurrentInterpreter().setVariable(\"bios\", this);\n//computer.runFile(\"java\", \"rom\", \"os.cjava\");";

	public static void initComputerFolder(File computerFolder) {
		File cmosFolder = new File(computerFolder, "cmos");
		File hhdFolder = new File(computerFolder, "hhd");
		File romFolder = new File(computerFolder, "rom");
		cmosFolder.mkdirs();
		hhdFolder.mkdirs();
		romFolder.mkdirs();
		checkFile(new File(romFolder, "bios.cjava"), biosFile);

	}

	private static void checkFile(File file, String defaultFile) {
		if (!file.exists()) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writer.write(defaultFile);
				writer.close();
				System.out.println("File Created: " + file);
			} catch (Exception e) {
				System.out.println("File Creation Failed: " + file);
			}
		}
	}
}
