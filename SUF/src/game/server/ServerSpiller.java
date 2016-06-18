package game.server;

import java.util.ArrayList;

import engine.network.ClientPacket;
import engine.network.HeartBeatPacket;
import engine.server.AbstractServerPlayer;
import engine.server.PlayerConnection;
import game.client.Game;
import game.network.OrdreTilføjetPacket;
import game.shared.Lokalgruppe;
import game.shared.ordrer.Ordre;

public class ServerSpiller extends AbstractServerPlayer {

	private int id;
	private String farve;
	private boolean ready;
	public ArrayList<Ordre> ordrer;
	private Server server;

	public ServerSpiller(PlayerConnection con, int id, String farve,
			Server server) {
		super(con, id);
		ordrer = new ArrayList<Ordre>();
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

	public void udførOrder(Server server) {
		for(Ordre ordre : ordrer){
			ordre.udfør(this, server);
		}
		ordrer.clear();
	}

	public void tilføjOrdre(Ordre ordre) {
		for (Ordre tmp : ordrer) {
			if (tmp.getLokalgruppeID() == ordre.getLokalgruppeID()) {
				ordrer.remove(tmp);
			}
		}
		ordrer.add(ordre);
	}

	public void sendOrdre() {
		for(Ordre ordre : ordrer){
			server.toAll(new OrdreTilføjetPacket(ordre.getName(), ordre
					.getLokalgruppeID()));
		}
	}
}
