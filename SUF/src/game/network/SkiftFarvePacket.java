package game.network;

import engine.network.ClientPacket;
import game.client.MainView;

public class SkiftFarvePacket implements ClientPacket<MainView> {
	
	private String farve;
	private int medlemID;
	
	public SkiftFarvePacket(String farve, int medlemID){
		this.farve = farve;
		this.medlemID = medlemID;
	}
	
	public void parse(MainView game) {
		game.medlemFraID(medlemID).setFarve(farve);
	}

}
