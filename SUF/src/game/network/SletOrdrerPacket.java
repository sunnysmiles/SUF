package game.network;

import engine.network.ClientPacket;
import game.client.Game;
import game.shared.Lokalgruppe;

public class SletOrdrerPacket implements ClientPacket<Game> {
	
	public void parse(Game game) {
		for(Lokalgruppe lg : game.lokalgrupper){
			lg.sletOrdrer();
		}
	}

}
