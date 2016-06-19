package game.network;

import engine.network.ClientPacket;
import game.client.MainView;
import game.shared.MonthEntry;

public class JournalMonthPacket implements ClientPacket<MainView> {

	private String title;

	public JournalMonthPacket(String title) {
		this.title = title;
	}

	public void parse(MainView game) {
		game.journal.addEntry(new MonthEntry(title));
	}

}
