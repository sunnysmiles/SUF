package game.network;

import engine.network.ClientPacket;
import game.client.DataChangedListener.ChangeType;
import game.client.Game;
import game.client.MainView;
import game.shared.Entry;

public class JournalEntryPacket implements ClientPacket<MainView>{

	private Entry entry;
	
	public JournalEntryPacket(Entry entry){
		this.entry = entry;
	}
	
	public JournalEntryPacket(String string) {
		entry = new Entry(string);
	}

	public void parse(Game game) {
		game.addJournalEntry(entry);
	}

}
