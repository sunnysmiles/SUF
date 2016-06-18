package game.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class By implements Serializable{
	private ArrayList<Lokalgruppe> ekstraNabo = new ArrayList<Lokalgruppe>();
	private ArrayList<By> ekstraNaboBy = new ArrayList<By>();
	private String navn;
	private int id;
	private Region region;
	private int x;
	private int y;
	public By(String navn, int id, Region region){
		x = 0;
		y = 0;
		this.setNavn(navn);
		this.setId(id);
		this.region = region;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public By copy() {
		return new By(getNavn(), id, region);
	}
	public String getNavn() {
		return navn;
	}
	public void setNavn(String navn) {
		this.navn = navn;
	}
	
	public void tilføjEkstraNabo(Lokalgruppe lg){
		ekstraNabo.add(lg);
	}
	
	public void tilføjEkstraNabo(By by){
		ekstraNaboBy.add(by);
	}
	
	public ArrayList<By> naboByer(){
		ArrayList<By> naboer = new ArrayList<By>();
		naboer.addAll(ekstraNaboBy);
		naboer.addAll(region.byer);
		return naboer;
	}
	
	public ArrayList<Lokalgruppe> naboLokalgrupper(){
		ArrayList<Lokalgruppe> naboer = new ArrayList<Lokalgruppe>();
		naboer.addAll(ekstraNabo);
		naboer.addAll(region.lokalgrupper);
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
}
