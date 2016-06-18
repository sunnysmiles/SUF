package game.shared.timecards;

import game.server.Server;

public abstract class TimeCard {
	private int type;
	
	public TimeCard(int type){
		this.setType(type);
	}
	
	public abstract void action(Server server);

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
