package engine.network;

import engine.client.AbstractGame;
import engine.network.ClientPacket;
import engine.network.ServerPacket;
import engine.server.AbstractServer;
import engine.server.AbstractServerPlayer;
import game.client.Game;

public class HeartBeatPacket implements ServerPacket<AbstractServer, AbstractServerPlayer>, ClientPacket<AbstractGame> {
	
	
	public void parse(AbstractServer server, AbstractServerPlayer serverPlayer) {
		serverPlayer.clearHeartBeats();
	}
	@Override
	public void parse(Game game) {
		game.clearHeartBeats();
	}

}
