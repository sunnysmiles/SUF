package game.network;

import java.awt.Color;

import javax.swing.Icon;

import engine.network.ClientPacket;
import engine.utils.SquareIcon;
import game.client.*;
import game.client.Game.ClientGameState;

public class StateChangePacket implements ClientPacket<MainView> {

	private ClientGameState newState;
	public StateChangePacket(ClientGameState state){
		newState = state;
	}
	public void parse(Game game) {
		game.changeState(newState);
	}

}
