package game.shared;

import game.client.Game;
import game.client.MainView;

import java.io.Serializable;
import java.util.ArrayList;

public class Ledelse implements Serializable {

	private ArrayList<Medlem> menige;
	private ArrayList<Medlem> regionsRepræsentanter;

	public Ledelse(ArrayList<Medlem> menige,
			ArrayList<Medlem> regionsRepræsentanter) {
		this.menige = menige;
		this.regionsRepræsentanter = regionsRepræsentanter;
	}

	public Ledelse(ArrayList<Medlem> menige,
			ArrayList<Medlem> regionsRepræsentanter,
			ArrayList<Medlem> medlemsListe, ArrayList<Region> regioner) {
		this.menige = new ArrayList<Medlem>();
		this.regionsRepræsentanter = new ArrayList<Medlem>();
		for (Medlem m : menige) {
			for (Medlem lm : medlemsListe) {
				if (m.getID() == lm.getID())
					tilføjMenigMedlem(lm);
			}
		}
		updateRegionsRepræsentanter(regioner);
	}

	public Ledelse(Ledelse ledelse, ArrayList<Medlem> medlemsListe,
			ArrayList<Region> regioner) {
		this(ledelse.getMenige(), ledelse.getRegeionsRepræsentanter(),
				medlemsListe, regioner);
	}

	public Ledelse() {
		menige = new ArrayList<Medlem>();
		regionsRepræsentanter = new ArrayList<Medlem>();
	}

	public int tilføjMenigMedlem(Medlem m) {
		if (!menige.contains(m)) {
			menige.add(m);
			return 1;
		}
		return -1;
	}

	public int tilføjRegionsRepræsentant(Medlem m) {
		if (!regionsRepræsentanter.contains(m)) {
			regionsRepræsentanter.add(m);
			return 1;
		}
		return -1;
	}

	public void updateRegionsRepræsentanter(ArrayList<Region> regioner) {
		regionsRepræsentanter.clear();
		for (Region r : regioner) {
			tilføjRegionsRepræsentant(r.getRegRep());
		}
	}

	public ArrayList<Medlem> getAlle() {
		ArrayList<Medlem> res = new ArrayList<Medlem>();
		res.addAll(menige);
		res.addAll(regionsRepræsentanter);
		return res;
	}

	public ArrayList<Medlem> getMenige() {
		return menige;
	}

	public ArrayList<Medlem> getRegeionsRepræsentanter() {
		return regionsRepræsentanter;
	}

	public float getProcentAfFarve(String farve){
		int fCount = 0;
		ArrayList<Medlem> medlemmer = getAlle();
		for(Medlem m : medlemmer){
			if(m.getFarve().equals(farve)) fCount++;
		}
		return fCount / medlemmer.size();
	}
}
