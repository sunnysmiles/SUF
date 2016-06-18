package engine.network;

import java.io.Serializable;

import engine.server.AbstractServer;
import engine.server.AbstractServerPlayer;

public interface ServerPacket <T1 extends AbstractServer,T2 extends AbstractServerPlayer> extends Serializable{
	public void parse(T1 server, T2 serverPlayer);
}
