package game.client;

public interface DataChangedListener {
	public enum ChangeType {
		JOURNAL_ENTRY
	};
	public void DataChanged(ChangeType type);
}
