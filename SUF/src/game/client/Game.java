package game.client;

import engine.client.Connection;
import engine.network.ServerPacket;
import engine.utils.SquareIcon;
import game.client.DataChangedListener.ChangeType;
import game.network.ClientReadyPacket;
import game.network.OrdrePacket;
import game.server.Server;
import game.server.ServerPlayer;
import game.shared.By;
import game.shared.Entry;
import game.shared.Journal;
import game.shared.Koo;
import game.shared.Ledelse;
import game.shared.Lokalgruppe;
import game.shared.Medlem;
import game.shared.MonthEntry;
import game.shared.Region;
import game.shared.Stats;
import game.shared.ordrer.Ordre;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

public class Game implements Runnable {
	public enum ClientState {
		NOT_STARTED, PRE_START, ORDRER, KOO, LEDELSE_KOO_TILLID, LEDELSE_FORSLAG, LEDELSE_KOO_VALG, LEDELSE_KOO_VALG_OPSTIL, LEDELSE_KOO_VALG_STEM
	};

	private ClientState state = ClientState.PRE_START;
	public ArrayList<By> byer;
	private ArrayList<Medlem> medlemmer;
	private ArrayList<Region> regioner;
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
	private ArrayList<DataChangedListener> dataListeners;
	private boolean ready = false;
	private Koo koo;
	private ArrayList<Medlem> kooOpstillet;

	public Game() {
		connection = new Connection();
		new Thread(connection).start();
		journal = new Journal();
		journal.addEntry(new MonthEntry("Januar"));
		state = ClientState.NOT_STARTED;
		dataListeners = new ArrayList<DataChangedListener>();
		kooOpstillet = new ArrayList<Medlem>();

	}

	private void fromServer() {
		// missingBeatPackets++;
		connection.parse(this);
		if (missingBeatPackets > 2000) {
			System.out.println("Server unresponsive");
			stopGame();
		}
	}

	public void run() {
		while (running) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			fromServer();
		}
	}

	public void addOrdre(Ordre ordre) {
		if (state != ClientState.ORDRER)
			return;
		if (!farve
				.equals(lokalgruppeFraID(ordre.getLokalgruppeID()).getFarve()))
			return;
		sendPacket(new OrdrePacket(ordre));
		lokalgruppeFraID(ordre.getLokalgruppeID()).tilføjOrdre(ordre.getName());
	}

	public void sendPacket(ServerPacket<Server, ServerPlayer> packet) {
		connection.stack(packet);
		sendData();
	}

	public void stack(ServerPacket<Server, ServerPlayer> packet) {
		connection.stack(packet);
	}

	public void sendData() {
		try {
			connection.sendData();
		} catch (IOException e) {
			System.out.println("Problem sending data");
			if (e.getMessage().startsWith("Connection reset by peer:")) {
				System.out
						.println("Lost connection to server, game terminated");
				stopGame();
				return;
			} else
				System.out.println("Problem sending data");
		}
	}

	public void stopGame() {
		running = false;
	}

	public Journal getJournal() {
		return journal;
	}

	public void ready() {
		ready = true;
		switch (state) {
		case LEDELSE_KOO_VALG_OPSTIL:
			sendPacket(new kOpstillingPacket(kooOpstillet));
			kooOpstillet.clear();
		}
		sendPacket(new ClientReadyPacket());
	}

	public void clearHeartBeats() {
		missingBeatPackets = 0;
	}

	public ArrayList<Lokalgruppe> getLokalgrupper() {
		return lokalgrupper;
	}

	public ArrayList<By> getByer() {
		return byer;
	}

	public Medlem medlemFraID(int id) {
		for (Medlem m : medlemmer) {
			if (m.getID() == id)
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

	private Lokalgruppe lokalgruppeFraID(int id) {
		for (Lokalgruppe l : lokalgrupper) {
			if (l.getId() == id)
				return l;
		}
		return null;
	}

	public Lokalgruppe lokalgruppeFraNavn(String navn) {
		for (Lokalgruppe lg : lokalgrupper) {
			if (lg.getNavn().equals(navn))
				return lg;
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

	public ClientState getState() {
		return state;
	}

	public String getFarve() {
		return farve;
	}

	public void addDataListener(DataChangedListener listener) {
		dataListeners.add(listener);
	}

	public void dataChangedSignal(ChangeType type) {
		for (DataChangedListener listener : dataListeners) {
			listener.DataChanged(type);
		}
	}

	public void addJournalEntry(Entry entry) {
		journal.getCurrentEntry().addEntry(entry);
		dataChangedSignal(ChangeType.JOURNAL_ENTRY);
	}

	public void addJournalMonthEntry(MonthEntry monthEntry) {
		journal.addEntry(monthEntry);
		dataChangedSignal(ChangeType.JOURNAL_ENTRY);
	}

	public void removeMedlem(int medlemID) {
		for (Lokalgruppe lg : lokalgrupper) {
			if (lg.getMedlemmer().contains(medlemFraID(medlemID)))
				lg.getMedlemmer().remove(medlemFraID(medlemID));
		}
		medlemmer.remove(medlemFraID(medlemID));
	}

	public void setMedlemFarve(int medlemID, String farve) {
		medlemFraID(medlemID).setFarve(farve);
	}

	public void newMedlem(int lokalgruppeID, Medlem medlem) {
		lokalgruppeFraID(lokalgruppeID).tilføjMedlem(medlem);
		;
		medlemmer.add(medlem);
	}

	public void changeLokalgruppe(int lokalgruppe1ID, int lokalgruppe2ID,
			int medlemID) {
		lokalgruppeFraID(lokalgruppe1ID).fjernMedlem(medlemFraID(medlemID));
		lokalgruppeFraID(lokalgruppe2ID).tilføjMedlem(medlemFraID(medlemID));

	}

	public void addLokalGruppeOrdre(String navn, int lokalgruppeID) {
		lokalgruppeFraID(lokalgruppeID).tilføjOrdre(navn);
	}

	public void clearOrdre() {
		for (Lokalgruppe lg : lokalgrupper) {
			lg.sletOrdrer();
		}
	}

	public void setFarve(String farve) {
		this.farve = farve;
	}

	public void startGame(ArrayList<By> byer, ArrayList<Medlem> medlemmer,
			ArrayList<Region> regioner, ArrayList<Lokalgruppe> lokalgrupper,
			ArrayList<Spiller> clientSpillere, Stats stats, Ledelse ledelsen) {

		this.byer = byer;
		this.medlemmer = medlemmer;
		this.regioner = regioner;
		this.lokalgrupper = lokalgrupper;
		this.spillere = clientSpillere;
		this.month = "Januar";
		this.stats = stats;
		this.koo = new Koo();
		this.ledelsen = new Ledelse(ledelsen, getMedlemmer(), getRegioner());
		dataChangedSignal(ChangeType.GAME_STARTED);
		this.state = ClientState.PRE_START;
		sendPacket(new ClientReadyPacket());
	}

	public void changeState(ClientState newState) {
		ready = false;
		state = newState;
		dataChangedSignal(ChangeType.STATE_CHANGED);
	}

	public void opstilKoo(Medlem m) {
		if (!(state == ClientState.LEDELSE_KOO_VALG_OPSTIL))
			return;
		if (ledelsen.getAlle().contains(m)) {
			if (m.getFarve().equals(farve)) {
				if (!kooOpstillet.contains(m))
					kooOpstillet.add(m);
				else
					kooOpstillet.remove(m);
				dataChangedSignal(ChangeType.KOO_OPSTILLET);
			}
		}
	}

	public ArrayList<Medlem> getMedlemmer() {
		return medlemmer;
	}

	public ArrayList<Region> getRegioner() {
		return regioner;
	}

	public boolean isReady() {
		return ready;
	}

	public ArrayList<Medlem> translateFromServer(ArrayList<Medlem> serverList) {
		ArrayList<Medlem> clientList = new ArrayList<Medlem>();
		if (serverList.isEmpty())
			return clientList;
		for (Medlem m : serverList) {
			clientList.add(medlemFraID(m.getID()));
		}
		return clientList;
	}

	public void setOpstilling(ArrayList<Medlem> opstillet) {
		kooOpstillet.clear();
		kooOpstillet.addAll(opstillet);
		dataChangedSignal(ChangeType.KOO_OPSTILLET);
	}

	public Ledelse getLedelsen() {
		return ledelsen;
	}

}
