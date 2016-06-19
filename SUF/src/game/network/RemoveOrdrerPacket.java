package game.network;

import engine.network.ClientPacket;
import game.client.Game;
import game.client.MainView;
import game.shared.Lokalgruppe;

public class RemoveOrdrerPacket implements ClientPacket<MainView> {

	public void parse(Game game) {
		game.clearOrdre();
	}

}
