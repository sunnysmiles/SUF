package game.shared;

import java.io.Serializable;
import java.util.ArrayList;

import engine.utils.Util;

public class Lokalgruppe implements Serializable {
	private ArrayList<Lokalgruppe> ekstraNabo = new ArrayList<Lokalgruppe>();
	private ArrayList<By> ekstraNaboBy = new ArrayList<By>();
	private ArrayList<Medlem> medlemmer;
	private String ordre;
	private String navn;
	private int id;
	private Region reg;
	private int x;
	private int y;
	private String farve = null;

	public Lokalgruppe(String navn, int id, Region reg) {
		x = 0;
		y = 0;
		this.setId(id);
		this.setNavn(navn);
		setMedlemmer(new ArrayList<Medlem>());
		this.setReg(reg);
		ordre = "Ingen";

	}

	public void tilføjMedlem(Medlem medlem) {
		getMedlemmer().add(medlem);
		if (farve == null) {
			farve = medlem.getFarve();
		}
		else setFarve();
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}


	public void fjernMedlem(Medlem medlem) {
		getMedlemmer().remove(medlem);
		setFarve();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Lokalgruppe copy() {
		Lokalgruppe lokalgruppe = new Lokalgruppe(navn, id, getReg());
		for (Medlem m : getMedlemmer()) {
			lokalgruppe.tilføjMedlem(m.copy());
		}
		return lokalgruppe;
	}

	public ArrayList<Medlem> getMedlemmer() {
		return medlemmer;
	}

	public void setMedlemmer(ArrayList<Medlem> medlemmer) {
		this.medlemmer = medlemmer;
	}

	public String getFarve() {
		return farve;
	}

	public void setFarve() {
		int r, g, s, l;
		r = g = s = l = 0;
		for (Medlem m : medlemmer) {
			if (m.getFarve().equals("Rød"))
				r++;
			if (m.getFarve().equals("Sort"))
				s++;
			if (m.getFarve().equals("Lilla"))
				l++;
			if (m.getFarve().equals("Grøn"))
				g++;
		}
		if (r > g && r > s && r > l)
			farve =  "Rød";
		else if (g > r && g > s && g > l)
			farve = "Grøn";
		else if (s > r && s > g && s > l)
			farve = "Sort";
		else if (l > r && l > g && l > s)
			farve = "Lilla";
	}

	public void tilføjOrdre(String ordre) {
		this.ordre = ordre;
	}

	public void sletOrdrer() {
		ordre = "";
	}

	public ArrayList<Lokalgruppe> getEkstraNabo() {
		return ekstraNabo;
	}

	public void setEkstraNabo(ArrayList<Lokalgruppe> ekstraNabo) {
		this.ekstraNabo = ekstraNabo;
	}

	public void tilføjEkstraNabo(Lokalgruppe lg) {
		ekstraNabo.add(lg);
	}

	public void tilføjEkstraNabo(By by) {
		ekstraNaboBy.add(by);
	}

	public ArrayList<By> naboByer() {
		ArrayList<By> naboer = new ArrayList<By>();
		naboer.addAll(ekstraNaboBy);
		naboer.addAll(getReg().byer);
		return naboer;
	}

	public ArrayList<Lokalgruppe> naboLokalgrupper() {
		ArrayList<Lokalgruppe> naboer = new ArrayList<Lokalgruppe>();
		naboer.addAll(ekstraNabo);
		naboer.addAll(getReg().lokalgrupper);
		naboer.remove(this);
		return naboer;
	}

	public ArrayList<By> getEkstraNaboBy() {
		return ekstraNaboBy;
	}

	public void setEkstraNaboBy(ArrayList<By> ekstraNaboBy) {
		this.ekstraNaboBy = ekstraNaboBy;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Region getReg() {
		return reg;
	}

	public void setReg(Region reg) {
		this.reg = reg;
	}

	public String getOrdre() {
		return ordre;
	}
}
