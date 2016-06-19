package engine.network;

import java.io.Serializable;

import engine.client.AbstractGame;
import game.client.Game;

public interface ClientPacket <T1 extends AbstractGame> extends Serializable{
	public void parse(Game game);
}
