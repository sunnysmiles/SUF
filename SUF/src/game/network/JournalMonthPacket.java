package game.network;

import engine.network.ClientPacket;
import game.client.Game;
import game.client.MainView;
import game.shared.MonthEntry;

public class JournalMonthPacket implements ClientPacket<MainView> {

	private String title;

	public JournalMonthPacket(String title) {
		this.title = title;
	}

	public void parse(Game game) {
		game.addJournalMonthEntry(new MonthEntry(title));
	}

}
