package teamcerberus.cerberustech.network;

import java.util.HashMap;

public enum Senders {
	client(0),
	server(1);
	
	public int id;
	private Senders(int id){
		this.id = id;
	}
	
	public static HashMap<Integer, Senders> senders;
	static{
		senders = new HashMap<Integer, Senders>();
		for(Senders s : Senders.values())
			senders.put(s.id, s);
	}
}