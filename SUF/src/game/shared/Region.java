package game.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import engine.utils.Util;
import game.server.random.Randomizer;

public class Region implements Serializable {
	private String navn;
	public ArrayList<Lokalgruppe> lokalgrupper;
	private Medlem regRep;
	public ArrayList<By> byer;
	private int id;

	public Region(String navn, int id) {
		this.setNavn(navn);
		this.setId(id);
		lokalgrupper = new ArrayList<Lokalgruppe>();
		setByer(new ArrayList<By>());
		regRep = null;
	}

	public void tilføjLokalgruppe(Lokalgruppe lokalgruppe) {
		lokalgrupper.add(lokalgruppe);
	}

	public void tilføjBy(By by) {
		getByer().add(by);
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public ArrayList<Lokalgruppe> getLokalgrupper() {
		return lokalgrupper;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Region copy() {
		Region region = new Region(navn, id);
		for (By by : getByer()) {
			region.tilføjBy(by.copy());
		}
		for (Lokalgruppe lg : lokalgrupper) {
			region.tilføjLokalgruppe(lg.copy());
		}
		return region;
	}

	public ArrayList<By> getByer() {
		return byer;
	}

	public void setByer(ArrayList<By> byer) {
		this.byer = byer;
	}

	// Snak med Malte om valg af regrepere, og lav en implementation der ikke er
	// retarderet
	public void setRegRep(Randomizer randomizer, Ledelse ledelsen) {
		int r, g, s, l;
		r = g = s = l = 0;
		for (Lokalgruppe lg : lokalgrupper) {
			for (Medlem m : lg.getMedlemmer()) {
				if (m.getFarve().equals("Rød"))
					r++;
				if (m.getFarve().equals("Grøn"))
					g++;
				if (m.getFarve().equals("Sort"))
					s++;
				if (m.getFarve().equals("Lilla"))
					l++;
			}
		}
		String farve = null;
		if (r > g && r > s && r > l)
			farve = "Rød";
		else if (g > r && g > s && g > l)
			farve = "Grøn";
		else if (s > r && s > g && s > l)
			farve = "Sort";
		else if (l > r && l > g && l > s)
			farve = "Lilla";
		if (farve != null) {
			this.regRep = randomizer.memberForSetRegRep(this, farve, ledelsen);
			return;
		} else if(this.regRep == null){
			this.regRep = randomizer.memberForSetRegRep(this, "Hvid", ledelsen);
		}
	}

	public Medlem getRegRep() {
		return regRep;
	}
}
