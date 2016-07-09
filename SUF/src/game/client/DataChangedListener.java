package game.client;

public interface DataChangedListener {
	public enum ChangeType {
		JOURNAL_ENTRY, GAME_STARTED, STATE_CHANGED, KOO_OPSTILLET
	};
	public void DataChanged(ChangeType type);
}
