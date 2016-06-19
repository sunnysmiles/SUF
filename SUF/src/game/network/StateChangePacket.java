package game.network;

import java.awt.Color;

import javax.swing.Icon;

import engine.network.ClientPacket;
import engine.utils.SquareIcon;
import game.client.*;

public class StateChangePacket implements ClientPacket<MainView> {

	public void parse(Game game) {
		game.changeState();
	}

}
