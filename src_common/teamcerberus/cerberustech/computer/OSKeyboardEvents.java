package teamcerberus.cerberustech.computer;

import java.util.HashMap;

public enum OSKeyboardEvents {
	KeyPushed(1),
	KeyReleased(2);
	
	private int eventID;
	
	private OSKeyboardEvents(int eventID) {
		this.eventID = eventID;
	}
	
	public int getID(){
		return eventID;
	}
	
	public static HashMap<Integer, OSKeyboardEvents> events;
	public static int getEventID(OSKeyboardEvents event){return event.eventID;}
	public static OSKeyboardEvents getEventFromID(int id){return events.get(id);}
	static{
		events = new HashMap<Integer, OSKeyboardEvents>();
		for(OSKeyboardEvents e : OSKeyboardEvents.values())
			events.put(e.getID(), e);
	}
}