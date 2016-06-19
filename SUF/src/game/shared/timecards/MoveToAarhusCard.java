package game.shared.timecards;

import game.network.JournalEntryPacket;
import game.network.changeLokalgruppePacket;
import game.server.Server;
import game.shared.Entry;
import game.shared.Lokalgruppe;
import game.shared.Medlem;

public class MoveToAarhusCard extends TimeCard {

	public MoveToAarhusCard() {
		super(3);
	}

	public void action(Server server) {
		Lokalgruppe lg = server.randomLokalgruppeProvins();
		Medlem medlem = lg.tilfældigMedlem();
		lg.fjernMedlem(medlem);
		Lokalgruppe aarhus = server.lokalgruppeFraNavn("Aarhus");
		aarhus.tilføjMedlem(medlem);
		server.toAll(new JournalEntryPacket(new Entry(
				"Et medlem er flyttet fra " + lg.getNavn() + " til Aarhus")));
		server.toAll(new changeLokalgruppePacket(medlem.getID(), lg.getId(), aarhus.getId()));
	}

}
