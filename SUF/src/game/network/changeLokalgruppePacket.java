package game.network;

import engine.network.ClientPacket;
import game.client.Game;
import game.client.MainView;

public class changeLokalgruppePacket implements ClientPacket<MainView> {

	private int medlemID;
	private int lokalgruppe1ID;
	private int lokalgruppe2ID;
	public changeLokalgruppePacket(int medlemID, int lokalgruppe1ID, int lokalgruppe2ID){
		this.medlemID = medlemID;
		this.lokalgruppe1ID = lokalgruppe1ID;
		this.lokalgruppe2ID = lokalgruppe2ID;
	}
	public void parse(Game game) {
		game.changeLokalgruppe(lokalgruppe1ID, lokalgruppe2ID, medlemID);
	}

}
