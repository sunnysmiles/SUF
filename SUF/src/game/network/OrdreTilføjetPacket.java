package game.network;

import engine.network.ClientPacket;
import game.client.Game;

public class OrdreTilføjetPacket implements ClientPacket<Game> {

	private String navn;
	private int lokalgruppeID;

	public OrdreTilføjetPacket(String navn, int lokalgruppeID){
		this.navn = navn;
		this.lokalgruppeID = lokalgruppeID;
	}
	
	public void parse(Game game) {
		game.lokalgruppeFraID(lokalgruppeID).tilføjOrdre(navn);
	}

}
