package game.shared.ordrer;

import engine.utils.Util;
import game.network.JournalEntryPacket;
import game.network.SkiftFarvePacket;
import game.server.Server;
import game.server.ServerSpiller;
import game.shared.Lokalgruppe;
import game.shared.Medlem;

public class SkolingOrdre extends Ordre {

	public SkolingOrdre(int id) {
		super(id);
	}

	public void udfør(ServerSpiller sp, Server server) {
		Lokalgruppe lg = server.lokalgruppeFraID(lokalgruppeID);
		String farve = sp.getFarve();
		if (Util.getRandom(1, 11) > 5) {
			for (Medlem m : lg.getMedlemmer()) {
				if (m.getFarve().equals("Hvid")) {
					m.setFarve(farve);
					server.toAll(new SkiftFarvePacket(farve, m.getId()));
					server.toAll(new JournalEntryPacket(
							"Skolingsordren virkede et hvidt medlem fra lokalgruppen " + lg.getNavn()
									+ " har skiftet farve til " + farve));
					return;
				}
			}
		} 
		 else
		 server.toAll(new JournalEntryPacket("Skolingen i lokalgruppen "
		 + lg.getNavn()
		 + " fik ikke nogle medlemmer til at skifte farve"));
	}

	public String getName() {
		return "Skolings ordre";
	}

}
