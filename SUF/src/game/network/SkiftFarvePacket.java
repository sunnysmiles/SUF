package game.network;

import engine.network.ClientPacket;
import game.client.Game;

public class SkiftFarvePacket implements ClientPacket<Game> {
	
	private String farve;
	private int medlemID;
	
	public SkiftFarvePacket(String farve, int medlemID){
		this.farve = farve;
		this.medlemID = medlemID;
	}
	
	public void parse(Game game) {
		game.medlemFraID(medlemID).setFarve(farve);
	}

}
