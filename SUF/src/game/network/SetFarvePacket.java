package game.network;

import engine.network.ClientPacket;
import game.client.Game;

public class SetFarvePacket implements ClientPacket<Game> {

	private String farve;
	public SetFarvePacket(String farve){
		this.farve = farve;
	}
	public void parse(Game game) {
		game.farve = farve;
	}

}
