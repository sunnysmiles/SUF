package game.client;

import java.io.Serializable;

public class Spiller implements Serializable{
	private String farve;
	public Spiller(String farve){
		this.setFarve(farve);
	}
	public String getFarve() {
		return farve;
	}
	public void setFarve(String farve) {
		this.farve = farve;
	}
}
