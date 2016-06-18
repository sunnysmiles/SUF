package game.shared.timecards;

import game.network.JournalEntryPacket;
import game.server.Server;

public class Dummy2 extends TimeCard {

	public Dummy2() {
		super(2);
	}
	public void action(Server server) {
		server.toAll(new JournalEntryPacket("Der blev trukket et type-2 kort"));
	}

}
