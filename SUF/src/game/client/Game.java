package game.client;

import engine.client.Connection;
import engine.network.ServerPacket;
import game.network.ClientReadyPacket;
import game.server.Server;
import game.server.ServerSpiller;
import game.shared.By;
import game.shared.Journal;
import game.shared.Ledelse;
import game.shared.Lokalgruppe;
import game.shared.Medlem;
import game.shared.MonthEntry;
import game.shared.Region;
import game.shared.Stats;

import java.io.IOException;
import java.util.ArrayList;

public class Game {
	public enum ClienGameState {
		PRE_START, ORDRER, KOO, LEDELSE_KOO_TILLID, LEDELSE_FORSLAG, LEDELSE_KOO_VALG
	};
	
	public ClienGameState state = ClienGameState.PRE_START;
	public ArrayList<By> byer;
	public ArrayList<Medlem> medlemmer;
	public ArrayList<Region> regioner;
	private ArrayList<Lokalgruppe> lokalgrupper;
	public ArrayList<Spiller> spillere;
	private Journal journal;
	public String month;
	public String farve;
	public Stats stats;
	public Ledelse ledelsen;
	private Connection connection;
	private int missingBeatPackets = 0;
	private boolean running = true;
	
	public Game(){
		connection = new Connection();
		new Thread(connection).start();
		journal = new Journal();
		journal.addEntry(new MonthEntry("Januar"));
		state = ClienGameState.PRE_START;
	}
	
	public void update() {
		fromServer();
		switch (state) {
		case PRE_START:
			missingBeatPackets = 0;
			break;
		case ORDRER:
			break;
		default:
			missingBeatPackets = 0;
			break;
		}
	}
	
	private void fromServer() {
		missingBeatPackets++;
		connection.parse(this);
		if (missingBeatPackets > 2000) {
			System.out.println("Server unresponsive");
			stopGame();
		}
	}
	
	public void sendPacket(ServerPacket<Server, ServerSpiller> packet){
		connection.stack(packet);
		sendData();
	}
	
	public void stack(ServerPacket<Server, ServerSpiller> packet) {
		connection.stack(packet);
	}
	
	public void sendData(){
		try {
			connection.sendData();
		} catch (IOException e) {
			System.out.println("Problem sending data");
			if (e.getMessage().startsWith(
					"Connection reset by peer:")) {
				System.out
						.println("Lost connection to server, game terminated");
				stopGame();
				return;
			} else
				System.out.println("Problem sending data");
		}
	}
	
	public void stopGame(){
		running  = false;
	}
	
	public Journal getJournal(){
		return journal;
	}
	
	public void ready(){
		sendPacket(new ClientReadyPacket());
	}
	
	
	public void clearHeartBeats() {
		missingBeatPackets = 0;
	}
	
	public ArrayList<Lokalgruppe> getLokalgrupper(){
		return lokalgrupper;
	}
	
	public ArrayList<By> getByer(){
		return byer;
	}
	
	public Medlem medlemFraID(int id) {
		for (Medlem m : medlemmer) {
			if (m.getId() == id)
				return m;
		}
		return null;
	}

	public Region regionFraID(int id) {
		for (Region r : regioner) {
			if (r.getId() == id)
				return r;
		}
		return null;
	}

	public Lokalgruppe lokalgruppeFraID(int id) {
		for (Lokalgruppe l : lokalgrupper) {
			if (l.getId() == id)
				return l;
		}
		return null;
	}

	public By byFraID(int id) {
		for (By by : byer) {
			if (by.getId() == id)
				return by;
		}
		return null;
	}
}
