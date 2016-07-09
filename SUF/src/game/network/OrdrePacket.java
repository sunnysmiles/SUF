package game.network;

import engine.network.ServerPacket;
import game.server.Server;
import game.server.ServerPlayer;
import game.shared.ordrer.Ordre;

public class OrdrePacket implements ServerPacket<Server, ServerPlayer> {

	private Ordre ordre;

	public OrdrePacket(Ordre ordre) {
		this.ordre = ordre;
	}

	public void parse(Server server, ServerPlayer serverPlayer) {
		if (ordre.isValid(serverPlayer, server)) {
			server.tilføjOrdre(ordre, serverPlayer);
		}
	}

}
