package game.network;

import engine.network.ServerPacket;
import game.server.Server;
import game.server.ServerSpiller;

public class ClientReadyPacket implements ServerPacket<Server, ServerSpiller> {

	public void parse(Server server, ServerSpiller serverPlayer) {
		serverPlayer.setReady(true);
	}

}
