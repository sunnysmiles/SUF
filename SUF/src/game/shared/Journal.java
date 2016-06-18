package game.shared;

import java.util.ArrayList;

public class Journal {
	public ArrayList<MonthEntry> entries;
	private MonthEntry currentEntry;

	public Journal() {
		entries = new ArrayList<MonthEntry>();
	}

	public void addEntry(MonthEntry entry) {
		entries.add(entry);
		setCurrentEntry(entry);
	}

	public ArrayList<MonthEntry> getEntries() {
		return entries;
	}

	public MonthEntry getCurrentEntry() {
		return currentEntry;
	}

	public void setCurrentEntry(MonthEntry currentEntry) {
		this.currentEntry = currentEntry;
	}

	public static String getMonth(int m)  {
		String[] months = new String[12];
		months[0] = "Januar";
		months[1] = "Februar";
		months[2] = "Marts";
		months[3] = "April";
		months[4] = "Maj";
		months[5] = "Juni";
		months[6] = "Juli";
		months[7] = "August";
		months[8] = "September";
		months[9] = "Oktober";
		months[10] = "Novmeber";
		months[11] = "December";
		return months[m];
	}
}
