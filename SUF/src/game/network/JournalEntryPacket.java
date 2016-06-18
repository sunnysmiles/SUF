package game.network;

import engine.network.ClientPacket;
import game.client.Game;
import game.shared.Entry;

public class JournalEntryPacket implements ClientPacket<Game>{

	private Entry entry;
	
	public JournalEntryPacket(Entry entry){
		this.entry = entry;
	}
	
	public JournalEntryPacket(String string) {
		entry = new Entry(string);
	}

	public void parse(Game game) {
		game.journal.getCurrentEntry().addEntry(entry);
		game.journalView.update();
	}

}
