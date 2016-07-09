package game.network;

import engine.network.ServerPacket;
import game.server.Server;
import game.server.ServerPlayer;

public class ClientReadyPacket implements ServerPacket<Server, ServerPlayer> {

	public void parse(Server server, ServerPlayer serverPlayer) {
		serverPlayer.setReady(true);
	}

}
