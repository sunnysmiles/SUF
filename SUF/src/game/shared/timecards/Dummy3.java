package game.shared.timecards;

import game.network.JournalEntryPacket;
import game.server.Server;

public class Dummy3 extends TimeCard {

	public Dummy3(){
		super(3);
	}
	
	public void action(Server server) {
		server.toAll(new JournalEntryPacket("Der blev trukket et kort af type 3"));
	}

}
