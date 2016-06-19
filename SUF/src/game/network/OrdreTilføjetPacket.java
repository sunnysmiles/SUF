package game.network;

import engine.network.ClientPacket;
import game.client.MainView;

public class OrdreTilføjetPacket implements ClientPacket<MainView> {

	private String navn;
	private int lokalgruppeID;

	public OrdreTilføjetPacket(String navn, int lokalgruppeID){
		this.navn = navn;
		this.lokalgruppeID = lokalgruppeID;
	}
	
	public void parse(MainView game) {
		game.lokalgruppeFraID(lokalgruppeID).tilføjOrdre(navn);
	}

}
