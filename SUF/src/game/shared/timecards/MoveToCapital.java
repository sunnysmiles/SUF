package game.shared.timecards;

import game.network.JournalEntryPacket;
import game.network.changeLokalgruppePacket;
import game.server.Server;
import game.shared.Lokalgruppe;
import game.shared.Medlem;
import game.shared.Region;

public class MoveToCapital extends TimeCard {

	public MoveToCapital() {
		super(3);
	}

	public void action(Server server) {
		Lokalgruppe lg = server.tilfældigLokalgruppe();
		Region reg = server.regionFraNavn("Hovedstaden");
		Lokalgruppe tolg = reg.tilfældigLokalgruppe();
		Medlem m = lg.tilfældigMedlem();
		lg.fjernMedlem(m);
		tolg.tilføjMedlem(m);
		server.toAll(new JournalEntryPacket("Et medlem fra " + lg.getNavn()
				+ " lokalgruppen er flyttet til " + tolg.getNavn()));
		server.toAll(new changeLokalgruppePacket(m.getID(), lg.getId(), tolg
				.getId()));
	}

}
