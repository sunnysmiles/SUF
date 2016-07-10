package game.server.random;

import game.shared.Ledelse;
import game.shared.Lokalgruppe;
import game.shared.Medlem;
import game.shared.Region;
import game.shared.timecards.TimeCard;

import java.util.ArrayList;
import java.util.Collection;

public interface Randomizer {
	TimeCard getTimeCard(ArrayList<TimeCard> type1);
	Ledelse getRandomLedelse(ArrayList<Medlem> medlemmer, ArrayList<Region> regioner);
	Medlem getHvervningsOrdreMedlem(Lokalgruppe lg, String farve, int medlemCounter);
	Medlem changeFarveOrdreSuccess(Lokalgruppe lg);
	Lokalgruppe getLostMemberCardLG(ArrayList<Lokalgruppe> lokalgrupper);
	Medlem getLostMemberCardMedlem(Lokalgruppe lg);
	Lokalgruppe getMoveAarhusCardLG(ArrayList<Region> regioner);
	Medlem getMoveAarhusCardMedlem(Lokalgruppe lg);
	Lokalgruppe getMoveCapitalCardFromLG(ArrayList<Lokalgruppe> lokalgrupper);
	Medlem getMoveCapitalCardMedlem(Lokalgruppe lg);
	Lokalgruppe getMoveCapitalCardToLG(Region region);
	Medlem memberForSetRegRep(Region reg, String farve, Ledelse ledelsen);
	ArrayList<Medlem> getHvideKooOpstillet(Ledelse ledelsen);
}
