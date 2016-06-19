package game.network;

import engine.network.ClientPacket;
import game.client.Game;
import game.client.MainView;

public class OrdreAddedPacket implements ClientPacket<MainView> {

	private String navn;
	private int lokalgruppeID;

	public OrdreAddedPacket(String navn, int lokalgruppeID) {
		this.navn = navn;
		this.lokalgruppeID = lokalgruppeID;
	}

	public void parse(Game game) {
		game.addLokalGruppeOrdre(navn, lokalgruppeID);
	}

}
