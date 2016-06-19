package game.network;

import engine.network.ClientPacket;
import game.client.MainView;

public class skiftLokalgruppePacket implements ClientPacket<MainView> {

	private int medlemID;
	private int lokalgruppe1ID;
	private int lokalgruppe2ID;
	public skiftLokalgruppePacket(int medlemID, int lokalgruppe1ID, int lokalgruppe2ID){
		this.medlemID = medlemID;
		this.lokalgruppe1ID = lokalgruppe1ID;
		this.lokalgruppe2ID = lokalgruppe2ID;
	}
	public void parse(MainView game) {
		game.lokalgruppeFraID(lokalgruppe1ID).fjernMedlem(game.medlemFraID(medlemID));
		game.lokalgruppeFraID(lokalgruppe2ID).tilføjMedlem(game.medlemFraID(medlemID));
	}

}
