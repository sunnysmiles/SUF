package game.server.random;

import game.shared.Ledelse;
import game.shared.Lokalgruppe;
import game.shared.Medlem;
import game.shared.Region;
import game.shared.timecards.TimeCard;

import java.util.ArrayList;

public interface Randomizer {
	TimeCard getTimeCard(ArrayList<TimeCard> type1);
	Ledelse getRandomLedelse(ArrayList<Medlem> medlemmer, ArrayList<Region> regioner);
	Medlem getHvervningsOrdreMedlem(Lokalgruppe lg, String farve, int medlemCounter);
	boolean changeFarveOrdreSuccess();
	Lokalgruppe getLostMemberCardLG(ArrayList<Lokalgruppe> lokalgrupper);
	Medlem getLostMemberCardMedlem(Lokalgruppe lg);
	Lokalgruppe getMoveAarhusCardLG(ArrayList<Region> regioner);
	Medlem getMoveAarhusCardMedlem(Lokalgruppe lg);
	Lokalgruppe getMoveCapitalCardFromLG(ArrayList<Lokalgruppe> lokalgrupper);
	Medlem getMoveCapitalCardMedlem(Lokalgruppe lg);
	Lokalgruppe getMoveCapitalCardToLG(Region region);
	Medlem memberForSetRegRep(Region reg, String farve);
}
