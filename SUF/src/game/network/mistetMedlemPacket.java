package game.network;

import engine.network.ClientPacket;
import game.client.Game;

public class mistetMedlemPacket implements ClientPacket<Game> {
	
	private int medlemID;

	public mistetMedlemPacket(int medlemID){
		this.medlemID = medlemID;
	}
	
	public void parse(Game game) {
		game.medlemmer.remove(game.medlemFraID(medlemID));
	}

}
