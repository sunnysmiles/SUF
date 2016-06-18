package game.network;

import java.awt.Color;

import javax.swing.Icon;

import engine.network.ClientPacket;
import engine.utils.SquareIcon;
import game.client.*;

public class StateChangePacket implements ClientPacket<Game> {

	public void parse(Game game) {
		game.iconLabel.setIcon(new SquareIcon(Color.red));
	}

}
