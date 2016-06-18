package game.network;

import engine.network.ClientPacket;
import game.client.Game;
import game.shared.MonthEntry;

public class JournalMonthPacket implements ClientPacket<Game> {

	private String title;

	public JournalMonthPacket(String title) {
		this.title = title;
	}

	public void parse(Game game) {
		game.journal.addEntry(new MonthEntry(title));
	}

}
