package engine.server;

import java.util.ArrayList;

import engine.network.ClientPacket;
import engine.network.ServerPacket;

public abstract class AbstractServerPlayer {
	
	protected PlayerConnection connection;
	private int missingHeartBeatPackets;

	public AbstractServerPlayer(PlayerConnection con, int id){
		this.connection = con;
	}
	
	public ArrayList<ServerPacket> getRecievedPackts(){
		return connection.getRecievedPackets();
	}
	
	public void clearRecievedPackets(){
		connection.clearRecievedPackets();
	}
	
	public void parse(Parser parser){
		connection.parse(parser, this);
	}
	
	public void stack(ClientPacket p){
		connection.stack(p);
	}

	public void heartBeat() {
		missingHeartBeatPackets++;
	}

	public void clearHeartBeats() {
		missingHeartBeatPackets = 0;
	}

	public int getHeartBeats() {
		return missingHeartBeatPackets;
	}
}
