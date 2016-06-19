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

	public Lokalgruppe randomLokalgruppeProvins(ArrayList<Region> regioner) {
		ArrayList<Lokalgruppe> provinsgrupper = new ArrayList<Lokalgruppe>();
		for (Region reg : regioner) {
			if (reg.getNavn().equals("Øst-Jylland") || reg.getNavn().equals("")
					|| reg.getNavn().equals("Nord-Jylland")
					|| reg.getNavn().equals("Sønder-Jylland")) {
				for (Lokalgruppe lg : reg.getLokalgrupper()) {
					if (!lg.getNavn().equals("Aarhus")) {
						provinsgrupper.add(lg);
					}
				}
			}
		}
		return provinsgrupper.get(getRandom(0, provinsgrupper.size()));
	}

	public Region randomRegion(ArrayList<Region> regioner) {
		return regioner.get(getRandom(0, regioner.size()));
	}

	public Lokalgruppe randomLokalgruppe(ArrayList<Lokalgruppe> lokalgrupper) {
		return lokalgrupper.get(getRandom(0, lokalgrupper.size()));
	}

	public Medlem randomgMedlemfFarve(String farve, ArrayList<Medlem> medlemmer) {
		Medlem m;
		do {
			m = medlemmer.get(getRandom(0, medlemmer.size()));
		} while (!m.getFarve().equals(farve));
		return m;
	}

	public Ledelse randomLedelse(ArrayList<Medlem> medlemmer,
			ArrayList<Region> regioner) {
		Ledelse ledelsen = new Ledelse();
		for (int i = 0; i < 2; i++) {
			while (ledelsen.tilføjMenigMedlem(randomgMedlemfFarve("Rød",
					medlemmer)) == -1)
				;
		}
		for (int i = 0; i < 2; i++) {
			while (ledelsen.tilføjMenigMedlem(randomgMedlemfFarve("Sort",
					medlemmer)) == -1)
				;
		}
		for (int i = 0; i < 2; i++) {
			while (ledelsen.tilføjMenigMedlem(randomgMedlemfFarve("Grøn",
					medlemmer)) == -1)
				;
		}
		for (int i = 0; i < 2; i++) {
			while (ledelsen.tilføjMenigMedlem(randomgMedlemfFarve("Lilla",
					medlemmer)) == -1)
				;
		}
		for (Region r : regioner) {
			r.setRegRep();
		}
		ledelsen.updateRegionsRepræsentanter(regioner);
		return ledelsen;
	}

	public Medlem getHvervningsOrdreMedlem(Lokalgruppe lg, String farve, int medlemCounter) {
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
	public boolean changeFarveOrdreSuccess() {
		return (getRandom(1,3) == 2);
	}

	public Lokalgruppe getLostMemberCardLG(ArrayList<Lokalgruppe> lokalgrupper) {
		return this.randomLokalgruppe(lokalgrupper);
	}

	private Medlem randomMedlem(Lokalgruppe lg) {
		return lg.getMedlemmer().get(getRandom(0, lg.getMedlemmer().size()));
	}
	
	//Returns null if no member farve
	//TODO Rewrite to be more sensical
	public Medlem randomMemberOfLokalgruppeFarve(String farve, Lokalgruppe lg) {
		Medlem m = null;
		for(Medlem med : lg.getMedlemmer()){
			if(med.getFarve().equals(farve))
				m = med;
		}
		if(m == null)
			return m;
		do {
			m = lg.getMedlemmer().get(getRandom(0, lg.getMedlemmer().size()));
		} while (!m.getFarve().equals(farve));
		return m;
	}

	public Medlem getLostMemberCardMedlem(Lokalgruppe lg) {
		return randomMedlem(lg);
	}
}
