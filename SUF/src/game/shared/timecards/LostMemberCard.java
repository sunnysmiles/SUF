package game.shared.timecards;

import engine.network.ClientPacket;
import game.client.MainView;
import game.network.JournalEntryPacket;
import game.network.mistetMedlemPacket;
import game.server.Server;
import game.shared.Lokalgruppe;
import game.shared.Medlem;

public class LostMemberCard extends TimeCard {

	public LostMemberCard() {
		super(3);
	}

	public void action(Server server) {
		Lokalgruppe lg = server.tilfældigLokalgruppe();
		Medlem m = lg.tilfældigMedlem();
		lg.fjernMedlem(m);
		server.medlemmer.remove(m);
		server.toAll(new JournalEntryPacket("Et medlem fra " + lg.getNavn() + " er stoppet i SUF"));
		server.toAll(new mistetMedlemPacket(m.getId()));
	}

}
