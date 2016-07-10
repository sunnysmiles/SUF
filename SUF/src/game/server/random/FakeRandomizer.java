package game.server.random;

import game.shared.Ledelse;
import game.shared.Lokalgruppe;
import game.shared.Medlem;
import game.shared.Region;
import game.shared.timecards.TimeCard;

import java.util.ArrayList;

public class FakeRandomizer implements Randomizer {

	public TimeCard getTimeCard(ArrayList<TimeCard> cards) {
		return cards.get(0);
	}

	// Adds the first 2 members of each color as menig, and then uses the set
	// regrep algorithm of regioner
	// the regions regreps are the first member of the region with the color of
	// the region
	public Ledelse getRandomLedelse(ArrayList<Medlem> medlemmer,
			ArrayList<Region> regioner) {
		Ledelse ledelsen = new Ledelse();
		ArrayList<Medlem> medlemmerFarve = new ArrayList<Medlem>();
		for (Medlem m : medlemmer) {
			if (m.getFarve().equals("Rød"))
				medlemmerFarve.add(m);
		}
		for (int i = 0; i < 2; i++) {
			ledelsen.tilføjMenigMedlem(medlemmerFarve.get(i));
		}
		medlemmerFarve.clear();
		for (Medlem m : medlemmer) {
			if (m.getFarve().equals("Sort"))
				medlemmerFarve.add(m);
		}
		for (int i = 0; i < 2; i++) {
			ledelsen.tilføjMenigMedlem(medlemmerFarve.get(i));
		}
		medlemmerFarve.clear();
		for (Medlem m : medlemmer) {
			if (m.getFarve().equals("Grøn"))
				medlemmerFarve.add(m);
		}
		for (int i = 0; i < 2; i++) {
			ledelsen.tilføjMenigMedlem(medlemmerFarve.get(i));
		}
		medlemmerFarve.clear();
		for (Medlem m : medlemmer) {
			if (m.getFarve().equals("Lilla"))
				medlemmerFarve.add(m);
		}
		for (int i = 0; i < 2; i++) {
			ledelsen.tilføjMenigMedlem(medlemmerFarve.get(i));
		}
		for (Region r : regioner) {
			r.setRegRep(this, ledelsen);
		}
		ledelsen.updateRegionsRepræsentanter(regioner);
		return ledelsen;
	}

	// Hvervningsordre always succedes and the new medlem is always non-white
	public Medlem getHvervningsOrdreMedlem(Lokalgruppe lg, String farve,
			int medlemCounter) {
		return new Medlem(farve, medlemCounter);
	}

	// changeFarveOrdre is always successfull when there is a farve=hvid medlem
	// In the lokalgruppe, if not it fails
	public Medlem changeFarveOrdreSuccess(Lokalgruppe lg) {
		for (Medlem m : lg.getMedlemmer()) {
			if (m.getFarve().equals("Hvid"))
				return m;
		}
		return null;
	}

	// Always picks the first lokalgruppe
	public Lokalgruppe getLostMemberCardLG(ArrayList<Lokalgruppe> lokalgrupper) {
		return lokalgrupper.get(0);
	}

	// Always gets the first medlem
	public Medlem getLostMemberCardMedlem(Lokalgruppe lg) {
		return lg.getMedlemmer().get(0);
	}

	// Return first lokalgruppe in Østjylland
	public Lokalgruppe getMoveAarhusCardLG(ArrayList<Region> regioner) {
		for (Region reg : regioner) {
			if (reg.getNavn().equals("Øst-Jylland"))
				return reg.lokalgrupper.get(0);
		}
		return null;
	}

	// Returns the first member of the first lokalgruppe of østjylland
	public Medlem getMoveAarhusCardMedlem(Lokalgruppe lg) {
		return lg.getMedlemmer().get(0);
	}

	// Returns the first lokalgruppe
	public Lokalgruppe getMoveCapitalCardFromLG(
			ArrayList<Lokalgruppe> lokalgrupper) {
		return lokalgrupper.get(0);
	}

	// Returns the first medlem of the first lokalgruppe
	public Medlem getMoveCapitalCardMedlem(Lokalgruppe lg) {
		return lg.getMedlemmer().get(0);
	}

	// Gets the first lokalgruppe in hovedstaden
	public Lokalgruppe getMoveCapitalCardToLG(Region region) {
		return region.getLokalgrupper().get(0);
	}

	// Returns the first memberFarve of the first lokalgruppe in the first
	// region
	// that has a member of farve
	public Medlem memberForSetRegRep(Region reg, String farve, Ledelse ledelsen) {
		ArrayList<Medlem> medlemmerFarve = new ArrayList<Medlem>();
		for (Lokalgruppe lg : reg.getLokalgrupper()) {
			for (Medlem med : lg.getMedlemmer()) {
				if (med.getFarve().equals(farve) && !ledelsen.getAlle().contains(med)) {
					return med;
				}
			}
		}
		return null;
	}

	public ArrayList<Medlem> getHvideKooOpstillet(Ledelse ledelsen) {
		ArrayList<Medlem> opstillet = new ArrayList<Medlem>();
		ArrayList<Medlem> afFarve = new ArrayList<Medlem>();
		for (Medlem m : ledelsen.getAlle()) {
			if (m.equals("Hvid"))
				afFarve.add(m);
		}
		if (afFarve.size() > 2) {
			opstillet.add(afFarve.get(0));
			opstillet.add(afFarve.get(1));
		} else {
			opstillet.addAll(afFarve);
		}
		return opstillet;
	}

}
