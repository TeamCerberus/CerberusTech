package teamcerberus.cerberustech.computer.event;

import teamcerberus.cerberustech.computer.OSKeyboardLetters;

public class EventKeyPush extends ComputerEvent{
	public OSKeyboardLetters key;
	
	public EventKeyPush(OSKeyboardLetters key){
		this.key = key;
	}
	
}
