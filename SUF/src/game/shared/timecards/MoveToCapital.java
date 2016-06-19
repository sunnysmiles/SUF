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
		Lokalgruppe lg = server.tilf�ldigLokalgruppe();
		Region reg = server.regionFraNavn("Hovedstaden");
		Lokalgruppe tolg = reg.tilf�ldigLokalgruppe();
		Medlem m = lg.tilf�ldigMedlem();
		lg.fjernMedlem(m);
		tolg.tilf�jMedlem(m);
		server.toAll(new JournalEntryPacket("Et medlem fra " + lg.getNavn()
				+ " lokalgruppen er flyttet til " + tolg.getNavn()));
		server.toAll(new changeLokalgruppePacket(m.getID(), lg.getId(), tolg
				.getId()));
	}

}
