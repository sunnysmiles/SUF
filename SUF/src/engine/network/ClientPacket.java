package engine.network;

import java.io.Serializable;

import engine.client.AbstractGame;

public interface ClientPacket <T1 extends AbstractGame> extends Serializable{
	public void parse(T1 game);
}
