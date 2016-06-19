package game.network;

import engine.network.ClientPacket;
import game.client.MainView;

public class mistetMedlemPacket implements ClientPacket<MainView> {
	
	private int medlemID;

	public mistetMedlemPacket(int medlemID){
		this.medlemID = medlemID;
	}
	
	public void parse(MainView game) {
		game.medlemmer.remove(game.medlemFraID(medlemID));
	}

}
