package teamcerberus.cerberustech.computer.event;

import teamcerberus.cerberustech.computer.OSKeyboardLetters;

public class EventKeyRelease extends ComputerEvent{
	public OSKeyboardLetters key;
	
	public EventKeyRelease(OSKeyboardLetters key){
		this.key = key;
	}
	
}
