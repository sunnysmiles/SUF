package game.shared;

import game.client.Game;
import game.client.MainView;

import java.io.Serializable;
import java.util.ArrayList;

public class Ledelse implements Serializable {

	private ArrayList<Medlem> menige;
	private ArrayList<Medlem> regionsRepr�sentanter;

	public Ledelse(ArrayList<Medlem> menige,
			ArrayList<Medlem> regionsRepr�sentanter) {
		this.menige = menige;
		this.regionsRepr�sentanter = regionsRepr�sentanter;
	}

	public Ledelse(ArrayList<Medlem> menige,
			ArrayList<Medlem> regionsRepr�sentanter, Game game) {
		this.menige = new ArrayList<Medlem>();
		this.regionsRepr�sentanter = new ArrayList<Medlem>();
		for (Medlem m : menige) {
			tilf�jMenigMedlem(game.medlemFraID(m.getId()));
		}
		updateRegionsRepr�sentanter(game.regioner);
	}

	public Ledelse(Ledelse ledelse, Game game) {
		this(ledelse.getMenige(), ledelse.getRegeionsRepr�sentanter(), game);
	}

	public Ledelse() {
		menige = new ArrayList<Medlem>();
		regionsRepr�sentanter = new ArrayList<Medlem>();
	}

	public int tilf�jMenigMedlem(Medlem m) {
		if (!menige.contains(m)) {
			menige.add(m);
			return 1;
		}
		return -1;
	}

	public int tilf�jRegionsRepr�sentant(Medlem m) {
		if(!regionsRepr�sentanter.contains(m)){
			regionsRepr�sentanter.add(m);
			return 1;
		}
		return -1;
	}
	
	public void updateRegionsRepr�sentanter(ArrayList<Region> regioner){
		regionsRepr�sentanter.clear();
		for (Region r : regioner) {
			tilf�jRegionsRepr�sentant(r.getRegRep());
		}
	}

	public ArrayList<Medlem> getAlle() {
		ArrayList<Medlem> res = new ArrayList<Medlem>();
		res.addAll(menige);
		res.addAll(regionsRepr�sentanter);
		return res;
	}

	public ArrayList<Medlem> getMenige() {
		return menige;
	}

	public ArrayList<Medlem> getRegeionsRepr�sentanter() {
		return regionsRepr�sentanter;
	}
}
