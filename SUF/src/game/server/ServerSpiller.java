package game.server;

import java.util.ArrayList;

import engine.network.ClientPacket;
import engine.network.HeartBeatPacket;
import engine.server.AbstractServerPlayer;
import engine.server.PlayerConnection;
import game.client.MainView;
import game.network.OrdreAddedPacket;
import game.shared.Lokalgruppe;
import game.shared.ordrer.Ordre;

public class ServerSpiller extends AbstractServerPlayer {

	private int id;
	private String farve;
	private boolean ready;
	private Server server;
	private boolean ordrerCompleted = false;

	public boolean isOrdrerCompleted() {
		return ordrerCompleted;
	}

	public void setOrdrerCompleted(boolean ordrerCompleted) {
		this.ordrerCompleted = ordrerCompleted;
	}

	public ServerSpiller(PlayerConnection con, int id, String farve,
			Server server) {
		super(con, id);

		this.id = id;
		this.setFarve(farve);
		ready = false;
		this.server = server;
	}

	public void update() {
		stack(new HeartBeatPacket());
		connection.sendData();
	}

	public void stop() {
		connection.stop();
	}

	public PlayerConnection getPlayerConnection() {
		return connection;
	}

	public int getID() {
		return id;
	}

	public String getFarve() {
		return farve;
	}

	public void setFarve(String farve) {
		this.farve = farve;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public boolean isReady() {
		return ready;
	}

}
