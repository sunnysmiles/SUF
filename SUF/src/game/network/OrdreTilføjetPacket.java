package game.network;

import engine.network.ClientPacket;
import game.client.Game;

public class OrdreTilf�jetPacket implements ClientPacket<Game> {

	private String navn;
	private int lokalgruppeID;

	public OrdreTilf�jetPacket(String navn, int lokalgruppeID){
		this.navn = navn;
		this.lokalgruppeID = lokalgruppeID;
	}
	
	public void parse(Game game) {
		game.lokalgruppeFraID(lokalgruppeID).tilf�jOrdre(navn);
	}

}
