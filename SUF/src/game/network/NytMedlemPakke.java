package game.network;

import engine.network.ClientPacket;
import game.client.Game;
import game.shared.Medlem;

public class NytMedlemPakke implements ClientPacket<Game> {
	
	private Medlem medlem;
	private int lokalgruppeID;

	public NytMedlemPakke(Medlem medlem, int lokalgruppeID){
		this.medlem = medlem.copy();
		this.lokalgruppeID = lokalgruppeID;
	}
	
	public void parse(Game game) {
		game.lokalgruppeFraID(lokalgruppeID).tilføjMedlem(medlem);;
		game.medlemmer.add(medlem);
	}

}
