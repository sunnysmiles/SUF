package game.shared.ordrer;

import java.io.Serializable;

import game.server.Server;
import game.server.ServerSpiller;
import game.shared.Lokalgruppe;

public abstract class Ordre implements Serializable{
	protected int lokalgruppeID;
	
	public Ordre(int lokalgruppeID){
		this.lokalgruppeID = lokalgruppeID;
	}
	
	public abstract void udfør(ServerSpiller sp, Server server);
	
	public boolean isValid(ServerSpiller sp, Server server){
		if(sp.getFarve().equals(server.lokalgruppeFraID(lokalgruppeID).getFarve())) return true;
		return false;
	}
	
	public abstract String getName();
	
	public int getLokalgruppeID(){
		return lokalgruppeID;
	}
}
