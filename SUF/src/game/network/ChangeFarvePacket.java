package game.network;

import engine.network.ClientPacket;
import game.client.Game;
import game.client.MainView;

public class ChangeFarvePacket implements ClientPacket<MainView> {
	
	private String farve;
	private int medlemID;
	
	public ChangeFarvePacket(String farve, int medlemID){
		this.farve = farve;
		this.medlemID = medlemID;
	}
	
	public void parse(Game game) {
		game.setMedlemFarve(medlemID, farve);
	}

}
