package game.shared;

import java.io.Serializable;

public class Medlem implements Serializable{
	private String farve;
	private int id;
	public String navn = "Navn Mangler";
	
	public Medlem(String farve, int id){
		this.setId(id);
		this.farve = farve;
	}
	
	public Medlem(String farve){
		this.farve = farve;
	}
	public String getFarve() {
		return farve;
	}

	public void setFarve(String farve) {
		this.farve = farve;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Medlem copy() {
		return new Medlem(farve, id);
	}
}
