package game.network;

import engine.network.ClientPacket;
import game.client.MainView;
import game.shared.Lokalgruppe;

public class SletOrdrerPacket implements ClientPacket<MainView> {
	
	public void parse(MainView game) {
		for(Lokalgruppe lg : game.lokalgrupper){
			lg.sletOrdrer();
		}
	}

}
