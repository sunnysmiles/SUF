package game.shared;

import java.util.ArrayList;

public class MonthEntry {
	public ArrayList<Entry> entries;
	public String title;
	public MonthEntry(String title){
		this.setTitle(title);
		entries = new ArrayList<Entry>();
	}
	
	public void addEntry(Entry e){
		entries.add(e);
	}
	public ArrayList<Entry> getEntries(){
		return entries;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
