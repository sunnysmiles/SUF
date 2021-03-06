package game.server.random;

import engine.utils.Util;
import game.network.JournalEntryPacket;
import game.shared.Ledelse;
import game.shared.Lokalgruppe;
import game.shared.Medlem;
import game.shared.Region;
import game.shared.timecards.TimeCard;

import java.util.ArrayList;
import java.util.Random;

public class RealRandomizer implements Randomizer {

	// This class defines atom-functions for fecthing random object from lists.
	// All public functions must be tied to one unique usage
	// This way creating a deterministic test-environment becomes easy

	public static int getRandom(int l, int h) {
		Random r = new Random();
		if (h < l)
			return -1;
		if (h == l)
			return l;
		return r.nextInt(h - l) + l;
	}

	public TimeCard getTimeCard(ArrayList<TimeCard> cards) {
		return cards.get(getRandom(0, cards.size()));
	}

	private Lokalgruppe randomLokalgruppeProvins(ArrayList<Region> regioner) {
		ArrayList<Lokalgruppe> provinsgrupper = new ArrayList<Lokalgruppe>();
		for (Region reg : regioner) {
			if (reg.getNavn().equals("�st-Jylland") || reg.getNavn().equals("")
					|| reg.getNavn().equals("Nord-Jylland")
					|| reg.getNavn().equals("S�nder-Jylland")) {
				for (Lokalgruppe lg : reg.getLokalgrupper()) {
					if (!lg.getNavn().equals("Aarhus")) {
						provinsgrupper.add(lg);
					}
				}
			}
		}
		return provinsgrupper.get(getRandom(0, provinsgrupper.size()));
	}

	private Region randomRegion(ArrayList<Region> regioner) {
		return regioner.get(getRandom(0, regioner.size()));
	}

	private Lokalgruppe randomLokalgruppe(ArrayList<Lokalgruppe> lokalgrupper) {
		return lokalgrupper.get(getRandom(0, lokalgrupper.size()));
	}

	// TODO: This works but it's kinda silly
	private Medlem randomMedlemFarve(String farve, ArrayList<Medlem> medlemmer) {
		ArrayList<Medlem> afFarve = new ArrayList<Medlem>();
		for (Medlem m : medlemmer) {
			if (m.getFarve().equals(farve))
				afFarve.add(m);
		}
		if (afFarve.isEmpty())
			return null;
		else
			return randomMedlem(afFarve);
	}

	public Ledelse getRandomLedelse(ArrayList<Medlem> medlemmer,
			ArrayList<Region> regioner) {
		Ledelse ledelsen = new Ledelse();
		ArrayList<Medlem> toCheck = new ArrayList<Medlem>();
		for (Medlem m : medlemmer) {
			if (m.getFarve().equals("R�d"))
				toCheck.add(m);
		}
		for (int i = 0; i < 2; i++) {
			while (ledelsen
					.tilf�jMenigMedlem(randomMedlemFarve("R�d", toCheck)) == -1)
				;
		}
		toCheck.clear();
		for (Medlem m : medlemmer) {
			if (m.getFarve().equals("Sort"))
				toCheck.add(m);
		}
		for (int i = 0; i < 2; i++) {
			while (ledelsen
					.tilf�jMenigMedlem(randomMedlemFarve("Sort", toCheck)) == -1)
				;
		}
		toCheck.clear();
		for (Medlem m : medlemmer) {
			if (m.getFarve().equals("Gr�n"))
				toCheck.add(m);
		}
		for (int i = 0; i < 2; i++) {
			while (ledelsen
					.tilf�jMenigMedlem(randomMedlemFarve("Gr�n", toCheck)) == -1)
				;
		}
		toCheck.clear();
		for (Medlem m : medlemmer) {
			if (m.getFarve().equals("Lilla"))
				toCheck.add(m);
		}
		for (int i = 0; i < 2; i++) {
			while (ledelsen.tilf�jMenigMedlem(randomMedlemFarve("Lilla",
					toCheck)) == -1)
				;
		}
		for (Region r : regioner) {
			r.setRegRep(this, ledelsen);
		}
		ledelsen.updateRegionsRepr�sentanter(regioner);
		return ledelsen;
	}

	public Medlem getHvervningsOrdreMedlem(Lokalgruppe lg, String farve,
			int medlemCounter) {
		Medlem m = null;
		if (getRandom(1, 100) > 50) {
			if (getRandom(1, 100) > 25) {
				m = new Medlem(farve, medlemCounter);
			} else {
				m = new Medlem("Hvid", medlemCounter);
			}
		}
		return m;
	}

	public Medlem changeFarveOrdreSuccess(Lokalgruppe lg) {
		ArrayList<Medlem> medlemmerHvid = new ArrayList<Medlem>();
		if (getRandom(0, 2) == 0)
			return null;
		for (Medlem m : lg.getMedlemmer()) {
			if (m.getFarve().equals("Hvid"))
				medlemmerHvid.add(m);
		}
		if (medlemmerHvid.size() > 0)
			return this.randomMedlem(medlemmerHvid);
		else
			return null;
	}

	public Lokalgruppe getLostMemberCardLG(ArrayList<Lokalgruppe> lokalgrupper) {
		return this.randomLokalgruppe(lokalgrupper);
	}

	private Medlem randomMedlem(Lokalgruppe lg) {
		return lg.getMedlemmer().get(getRandom(0, lg.getMedlemmer().size()));
	}

	// Returns null if no member farve
	// TODO Rewrite to be more sensical
	public Medlem randomMemberOfLokalgruppeFarve(String farve, Lokalgruppe lg) {
		Medlem m = null;
		for (Medlem med : lg.getMedlemmer()) {
			if (med.getFarve().equals(farve))
				m = med;
		}
		if (m == null)
			return m;
		do {
			m = lg.getMedlemmer().get(getRandom(0, lg.getMedlemmer().size()));
		} while (!m.getFarve().equals(farve));
		return m;
	}

	public Medlem getLostMemberCardMedlem(Lokalgruppe lg) {
		return randomMedlem(lg);
	}

	public Lokalgruppe getMoveAarhusCardLG(ArrayList<Region> regioner) {
		return this.randomLokalgruppeProvins(regioner);
	}

	public Medlem getMoveAarhusCardMedlem(Lokalgruppe lg) {
		return randomMedlem(lg);
	}

	public Lokalgruppe getMoveCapitalCardFromLG(
			ArrayList<Lokalgruppe> lokalgrupper) {
		return this.randomLokalgruppe(lokalgrupper);
	}

	public Medlem getMoveCapitalCardMedlem(Lokalgruppe lg) {
		return this.randomMedlem(lg);
	}

	public Lokalgruppe getMoveCapitalCardToLG(Region region) {
		return this.randomLokalgruppe(region);
	}

	// If the region has no lokalgrupper returns null
	private Lokalgruppe randomLokalgruppe(Region region) {
		if (region.getLokalgrupper().size() > 0)
			return region.getLokalgrupper().get(
					getRandom(0, region.getLokalgrupper().size()));
		return null;
	}

	private Medlem randomMemberFarve(Region reg, String farve) {
		Medlem m = null;
		ArrayList<Medlem> medlemmerFarve = new ArrayList<Medlem>();
		boolean hasFarve = false;
		for (Lokalgruppe lg : reg.getLokalgrupper()) {
			for (Medlem med : lg.getMedlemmer()) {
				if (med.getFarve().equals(farve)) {
					medlemmerFarve.add(med);
					hasFarve = true;
				}
			}
		}
		if (!hasFarve)
			return m;
		while (m == null) {
			m = this.randomMedlem(medlemmerFarve);
		}
		return m;
	}

	private Medlem randomMedlem(ArrayList<Medlem> medlemmer) {
		return medlemmer.get(getRandom(0, medlemmer.size()));
	}

	public Medlem memberForSetRegRep(Region reg, String farve, Ledelse ledelsen) {
		ArrayList<Medlem> potential = new ArrayList<Medlem>();
		for (Lokalgruppe lg : reg.getLokalgrupper())
			for (Medlem m : lg.getMedlemmer()) {
				if(m.getFarve().equals(farve) && !ledelsen.getAlle().contains(m)){
					potential.add(m);
				}
			}
		return randomMedlem(potential);
	}

	@Override
	public ArrayList<Medlem> getHvideKooOpstillet(Ledelse ledelsen) {
		ArrayList<Medlem> opstillet = new ArrayList<Medlem>();
		int c = 0;
		float procent = ledelsen.getProcentAfFarve("Hvid");
		if (procent >= 0.10)
			c = 1;
		if (procent >= 0.20)
			c = 2;
		if (procent > 0.35)
			c = 3;
		for (int i = 0; i < c; i++) {
			opstillet.add(randomMedlemFarve("Hvid", ledelsen.getAlle()));
		}
		return opstillet;
	}
}
