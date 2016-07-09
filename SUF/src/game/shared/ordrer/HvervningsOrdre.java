package game.shared.ordrer;

import engine.utils.Util;
import game.network.JournalEntryPacket;
import game.server.Server;
import game.server.ServerPlayer;
import game.shared.Lokalgruppe;
import game.shared.Medlem;

public class HvervningsOrdre extends Ordre {
	public HvervningsOrdre(int id) {
		super(id);
	}

	public void udfør(ServerPlayer sp, Server server) {
		Lokalgruppe lg = server.lokalgruppeFraID(lokalgruppeID);
		Medlem m = server.randomizer.getHvervningsOrdreMedlem(lg,
				sp.getFarve(), server.medlemCounter);
		if (m != null) {
			lg.tilføjMedlem(m);
			server.medlemmer.add(m);
			server.medlemCounter++;
			if(!m.getFarve().equals("Hvid")){
				server.toAll(new JournalEntryPacket(
						"Hvervnings forsøget virkede lokalgruppen "
								+ lg.getNavn()
								+ " fik et nyt medlem med farven "
								+ sp.getFarve()));
			}
			else{
				server.toAll(new JournalEntryPacket(
						"Hvervnings forsøget virkede lokalgruppen "
								+ lg.getNavn()
								+ " fik et nyt medlem med farven hvid"));
			}
		} else {
			server.toAll(new JournalEntryPacket(
					"Hvervnings forsøget virkede ikke i lokalgruppe "
							+ lg.getNavn()));
		}

	}

	public String getName() {
		return "Hvervnings Ordre";
	}

}
