package teamcerberus.cerberustech.computer.environments;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Environment {
	private String name;
	private IInterpreter masterInterpreter;
	
	public Environment(String name, IInterpreter masterInterpreter){
		this.name = name;
		this.masterInterpreter = masterInterpreter;
	}
	
	public String getName(){
		return name;
	}
	
	public String getFileType(){
		return masterInterpreter.getFileType();
	}
	
	public IInterpreter getMasterInterpreter(){
		return masterInterpreter;
	}
	
	public IInterpreter createSubInterpreter(){
		return masterInterpreter.createSubInterpreter();
	}
	
	public void test(){
		BufferedImage e;
		Graphics2D g = null;
		g.setColor(Color.gray);
		g.fillRect(0, 0, 200, 200);
	}
}
