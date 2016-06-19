package game.network;

import engine.network.ClientPacket;
import game.client.MainView;

public class SetFarvePacket implements ClientPacket<MainView> {

	private String farve;
	public SetFarvePacket(String farve){
		this.farve = farve;
	}
	public void parse(MainView game) {
		game.farve = farve;
	}

}
