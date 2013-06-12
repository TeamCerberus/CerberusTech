package teamcerberus.cerberustech.network;

import java.util.HashMap;

public enum Channels {
	computer(0, 0), computer_keyboardEvent(0, 1), computer_mouseEvent(0, 2), computer_powerEvent(
			0, 3);

	public int	id, sub;

	private Channels(int id, int sub) {
		this.id = id;
		this.sub = sub;
	}

	public boolean isSubChannelOf(Channels master) {
		return master.id == id && master.sub == 0;
	}

	public boolean isSameChannel(Channels other) {
		return other.id == id;
	}

	public static HashMap<String, Channels>	channels;
	static {
		channels = new HashMap<String, Channels>();
		for (Channels c : Channels.values()) {
			channels.put(c.id + "-" + c.sub, c);
		}
	}
}