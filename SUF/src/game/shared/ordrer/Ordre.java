package game.shared.ordrer;

import java.io.Serializable;

import game.server.Server;
import game.server.ServerPlayer;
import game.shared.Lokalgruppe;

public abstract class Ordre implements Serializable{
	protected int lokalgruppeID;
	
	public Ordre(int lokalgruppeID){
		this.lokalgruppeID = lokalgruppeID;
	}
	
	public abstract void udfør(ServerPlayer sp, Server server);
	
	public boolean isValid(ServerPlayer sp, Server server){
		if(sp.getFarve().equals(server.lokalgruppeFraID(lokalgruppeID).getFarve())) return true;
		System.out.println("Ordrer not valid");
		return false;
	}
	
	public abstract String getName();
	
	public int getLokalgruppeID(){
		return lokalgruppeID;
	}
}
