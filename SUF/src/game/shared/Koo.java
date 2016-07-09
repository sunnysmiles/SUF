package game.shared;

import java.util.ArrayList;

public class Koo {
	private int monthsLeft;
	private ArrayList<Medlem> medlemmer;
	
	public Koo(){
		setMonthsLeft(0);
		medlemmer = new ArrayList<Medlem>();
	}
	
	public void elect(ArrayList<Medlem> newMedlemmer){
		medlemmer.clear();
		setMonthsLeft(6);
		for(Medlem m : newMedlemmer){
			medlemmer.add(m);
		}
	}

	public int getMonthsLeft() {
		return monthsLeft;
	}

	public void setMonthsLeft(int monthsLeft) {
		this.monthsLeft = monthsLeft;
	}
	
}
