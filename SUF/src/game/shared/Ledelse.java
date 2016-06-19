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
			ArrayList<Medlem> regionsRepræsentanter, Game game) {
		this.menige = new ArrayList<Medlem>();
		this.regionsRepræsentanter = new ArrayList<Medlem>();
		for (Medlem m : menige) {
			tilføjMenigMedlem(game.medlemFraID(m.getId()));
		}
		updateRegionsRepræsentanter(game.regioner);
	}

	public Ledelse(Ledelse ledelse, Game game) {
		this(ledelse.getMenige(), ledelse.getRegeionsRepræsentanter(), game);
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
		if(!regionsRepræsentanter.contains(m)){
			regionsRepræsentanter.add(m);
			return 1;
		}
		return -1;
	}
	
	public void updateRegionsRepræsentanter(ArrayList<Region> regioner){
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
}
