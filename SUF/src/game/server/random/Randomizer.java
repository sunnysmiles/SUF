package game.server.random;

import game.shared.Ledelse;
import game.shared.Lokalgruppe;
import game.shared.Medlem;
import game.shared.Region;
import game.shared.timecards.TimeCard;

import java.util.ArrayList;

public interface Randomizer {
	TimeCard getTimeCard(ArrayList<TimeCard> type1);
	Lokalgruppe randomLokalgruppeProvins(ArrayList<Region> regioner);
	Lokalgruppe randomLokalgruppe(ArrayList<Lokalgruppe> lokalgrupper);
	Region randomRegion(ArrayList<Region> regioner);
	Medlem randomgMedlemfFarve(String farve, ArrayList<Medlem> medlemmer);
	Ledelse randomLedelse(ArrayList<Medlem> medlemmer, ArrayList<Region> regioner);
	Medlem getHvervningsOrdreMedlem(Lokalgruppe lg, String farve, int medlemCounter);
	boolean changeFarveOrdreSuccess();
}
