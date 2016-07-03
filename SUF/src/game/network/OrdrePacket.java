package game.network;

import engine.network.ServerPacket;
import game.server.Server;
import game.server.ServerSpiller;
import game.shared.ordrer.Ordre;

public class OrdrePacket implements ServerPacket<Server, ServerSpiller> {

	private Ordre ordre;

	public OrdrePacket(Ordre ordre) {
		this.ordre = ordre;
	}

	public void parse(Server server, ServerSpiller serverPlayer) {
		if (ordre.isValid(serverPlayer, server)) {
			server.tilføjOrdre(ordre, serverPlayer);
		}
	}

}
