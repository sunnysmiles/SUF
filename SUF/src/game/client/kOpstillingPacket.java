package game.client;

import java.util.ArrayList;

import engine.network.ClientPacket;
import engine.network.ServerPacket;
import game.server.Server;
import game.server.ServerPlayer;
import game.shared.Medlem;

public class kOpstillingPacket implements ServerPacket<Server, ServerPlayer>, ClientPacket<MainView> {

	private ArrayList<Medlem> opstillet;

	public kOpstillingPacket(ArrayList<Medlem> opstillet){
		this.opstillet = opstillet;
	}
	
	public void parse(Server server, ServerPlayer serverPlayer) {
		opstillet = server.translateFromClient(opstillet);
		server.godkendOpstilling(opstillet, serverPlayer);
	}

	public void parse(Game game) {
		opstillet = game.translateFromServer(opstillet);
		game.setOpstilling(opstillet);
	}

}
