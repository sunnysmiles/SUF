package game.network;

import engine.network.ClientPacket;
import game.client.Game;
import game.client.MainView;

public class removeMedlemPacket implements ClientPacket<MainView> {
	
	private int medlemID;

	public removeMedlemPacket(int medlemID){
		this.medlemID = medlemID;
	}
	
	public void parse(MainView game) {
	}

	public void parse(Game game) {
		game.removeMedlem(medlemID);
	}

}
