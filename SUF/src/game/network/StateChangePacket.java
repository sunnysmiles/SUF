package game.network;

import java.awt.Color;

import javax.swing.Icon;

import engine.network.ClientPacket;
import engine.utils.SquareIcon;
import game.client.*;
import game.client.Game.ClientState;

public class StateChangePacket implements ClientPacket<MainView> {

	private ClientState newState;
	public StateChangePacket(ClientState state){
		newState = state;
	}
	public void parse(Game game) {
		game.changeState(newState);
	}

}
