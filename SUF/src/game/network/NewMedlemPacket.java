package game.network;

import engine.network.ClientPacket;
import game.client.Game;
import game.client.MainView;
import game.shared.Medlem;

public class NewMedlemPacket implements ClientPacket<MainView> {
	
	private Medlem medlem;
	private int lokalgruppeID;

	public NewMedlemPacket(Medlem medlem, int lokalgruppeID){
		this.medlem = medlem.copy();
		this.lokalgruppeID = lokalgruppeID;
	}
	
	public void parse(Game game) {
		game.newMedlem(lokalgruppeID, medlem);
	}

}
