package game.shared.ordrer;

import engine.utils.Util;
import game.network.JournalEntryPacket;
import game.server.Server;
import game.server.ServerSpiller;
import game.shared.Lokalgruppe;
import game.shared.Medlem;

public class HvervningsOrdre extends Ordre {
	public HvervningsOrdre(int id) {
		super(id);
	}

	public void udf�r(ServerSpiller sp, Server server) {
		Lokalgruppe lg = server.lokalgruppeFraID(lokalgruppeID);
		if (Util.getRandom(1, 101) > 50) {
			Medlem m;
			if (Util.getRandom(1, 101) > 25) {
				m = new Medlem(sp.getFarve(), server.medlemCounter);
				server.toAll(new JournalEntryPacket(
						"Hvervnings fors�get virkede lokalgruppen "
								+ lg.getNavn()
								+ " fik et nyt medlem med farven "
								+ sp.getFarve()));
			} else {
				m = new Medlem("Hvid", server.medlemCounter);
				server.toAll(new JournalEntryPacket(
						"Hvervnings fors�get virkede lokalgruppen "
								+ lg.getNavn()
								+ " fik et nyt medlem med farven hvid"));
			}
			lg.tilf�jMedlem(m);
			server.medlemmer.add(m);
			server.medlemCounter++;
		}
		 else
		 server.toAll(new JournalEntryPacket(
		 "Hvervnings fors�get virkede ikke i lokalgruppe "
		 + lg.getNavn()));
	}

	public String getName() {
		return "Hvervnings Ordre";
	}

}
