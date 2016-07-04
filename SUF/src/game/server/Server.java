package game.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import engine.network.ClientPacket;
import engine.network.ServerPacket;
import engine.server.AbstractServer;
import engine.server.AbstractServerPlayer;
import engine.server.Parser;
import engine.server.PlayerConnection;
import game.client.MainView;
import game.client.Game.ClientGameState;
import game.network.JournalMonthPacket;
import game.network.OrdreAddedPacket;
import game.network.SetFarvePacket;
import game.network.StartGamePacket;
import game.network.StateChangePacket;
import game.server.random.FakeRandomizer;
import game.server.random.Randomizer;
import game.server.random.RealRandomizer;
import game.shared.By;
import game.shared.Journal;
import game.shared.Ledelse;
import game.shared.Lokalgruppe;
import game.shared.Medlem;
import game.shared.Region;
import game.shared.Stats;
import game.shared.ordrer.Ordre;
import game.shared.timecards.Dummy2;
import game.shared.timecards.Dummy3;
import game.shared.timecards.LostMemberCard;
import game.shared.timecards.MoveToAarhusCard;
import game.shared.timecards.MoveToCapital;
import game.shared.timecards.TimeCard;

public class Server extends AbstractServer implements Parser {

	private ArrayList<ServerSpiller> serverSpillere;
	private ServerArt art;

	public ArrayList<Medlem> medlemmer;
	public ArrayList<Lokalgruppe> lokalgrupper;
	public ArrayList<Region> regioner;
	public ArrayList<By> byer;
	public Ledelse ledelsen;
	public ArrayList<TimeCard> type1;
	public ArrayList<TimeCard> type2;
	public ArrayList<TimeCard> type3;
	public int medlemCounter = 0;
	private int byCounter = 0;
	private int regionCounter = 0;
	private int lokalgruppeCounter = 0;
	private BufferedReader reader;
	private Scanner scanner;
	public Stats stats;
	public int month = 0;
	private ArrayList<String> commands;
	public boolean gameStarted = false;
	public ArrayList<Ordre> ordrer;
	public Hashtable<Ordre, ServerSpiller> ordrerOwner;

	public enum ServerState {
		ORDRER, KOO, LEDELSE_KOO_TILLID, LEDELSE_KOO_VALG, LEDELSE_FORSLAG, PRE_START, PRE_INIT
	};

	private ServerState state = ServerState.PRE_INIT;
	public Randomizer randomizer;

	public Server(Randomizer randomizer) {
		this.randomizer = randomizer;
	}

	public synchronized void addCommand(String s) {
		commands.add(s);
	}

	private synchronized void checkCommands() {
		for (String s : commands) {
			switch (s) {
			case "Start":
				if (!gameStarted) {
					startGame();
					System.out.println("Game Started");
				}
				break;
			default:
				System.out.println("Unrecognized command");
				break;
			}
		}
		commands.clear();
	}

	public void tick() {
		checkCommands();
		if (!gameStarted)
			return;
		standardUpdate();
		switch (state) {
		case PRE_START:
			if (isReady()) {
				resetReady();
				tidenGår();
				state = ServerState.ORDRER;
				toAll(new StateChangePacket(ClientGameState.ORDRER));
			}
			break;
		case ORDRER:
			if (isReady()) {
				resetReady();
				sendOrdre();
				state = ServerState.KOO;
				toAll(new StateChangePacket(ClientGameState.KOO));
			}
			break;
		case KOO:
			if (isReady()) {
				resetReady();
				udførOrdre();
				month++;
				if (month > 11)
					month = 0;
				toAll(new JournalMonthPacket(Journal.getMonth(month)));
				state = ServerState.PRE_START;
				toAll(new StateChangePacket(ClientGameState.PRE_START));
			}
			break;
		default:
			break;
		}
	}


	public void standardUpdate() {
		parseFromServerPlayers();
		for (ServerSpiller serverPlayer : serverSpillere) {
			serverPlayer.update();
		}
	}

	private void resetReady() {
		for (ServerSpiller sp : serverSpillere) {
			sp.setReady(false);
		}
	}

	private boolean isReady() {
		for (ServerSpiller sp : serverSpillere) {
			if (!sp.isReady())
				return false;
		}
		return true;
	}

	private void parseFromServerPlayers() {
		ArrayList<ServerSpiller> toBeRemoved = new ArrayList<ServerSpiller>();
		for (ServerSpiller serverPlayer : serverSpillere) {
			serverPlayer.heartBeat();
			serverPlayer.parse(this);
			// This prevents concurrency errors
			/*
			 * if (serverPlayer.getHeartBeats() > 2000)
			 * toBeRemoved.add(serverPlayer);
			 */
		}
		for (ServerSpiller serverPlayer : toBeRemoved) {
			removeServerPlayer(serverPlayer);
		}
	}

	private void removeServerPlayer(ServerSpiller serverPlayer) {
		System.out.println("Removed Player with id " + serverPlayer.getID());
		serverPlayer.stop();
		getPlayerConnections().remove(serverPlayer.getPlayerConnection());
		serverSpillere.remove(serverPlayer);
	}

	public void init() {
		ordrerOwner = new Hashtable<Ordre,ServerSpiller>();
		ordrer = new ArrayList<Ordre>();
		commands = new ArrayList<String>();
		new Thread(new ServerConsole(this)).start();
		stats = new Stats();
		serverSpillere = new ArrayList<ServerSpiller>();
		medlemmer = new ArrayList<Medlem>();
		regioner = new ArrayList<Region>();
		lokalgrupper = new ArrayList<Lokalgruppe>();
		byer = new ArrayList<By>();
		type1 = new ArrayList<TimeCard>();
		type2 = new ArrayList<TimeCard>();
		type3 = new ArrayList<TimeCard>();
		art = new ServerArt();
		try {
			art.load();
		} catch (IOException e) {
			System.out.println("Art failed to load");
			e.printStackTrace();
		}
		idCounter = 0;
		parseScenarie();

		// Scenariet er parset alt initialisering der kræver at alt data er der
		// sker her.

		// TODO Lav en ordenlig start-ledelse
		ledelsen = randomizer.getRandomLedelse(medlemmer, regioner);

		for (int i = 0; i < 10; i++) {
			type1.add(new LostMemberCard());
		}
		for (int i = 0; i < 10; i++) {
			type1.add(new MoveToAarhusCard());
		}
		for (int i = 0; i < 10; i++) {
			type1.add(new MoveToCapital());
		}
		for (int i = 0; i < 10; i++) {
			type2.add(new Dummy2());
		}
		for (int i = 0; i < 10; i++) {
			type3.add(new Dummy3());
		}
		state = ServerState.PRE_START;
	}

	private void startGame() {
		gameStarted = true;
		toAll(new StartGamePacket(regioner, lokalgrupper, medlemmer, byer,
				serverSpillere, stats, ledelsen));
		for (ServerSpiller sp : serverSpillere) {
			sp.stack(new SetFarvePacket(sp.getFarve()));
		}
	}

	private void tidenGår() {
		for (int i = 0; i < 3; i++) {
			TimeCard c = randomizer.getTimeCard(type1);
			c.action(this);
			type1.remove(c);
		}
		for (int i = 0; i < 2; i++) {
			TimeCard c = randomizer.getTimeCard(type2);
			c.action(this);
			type2.remove(c);
		}
		TimeCard c = randomizer.getTimeCard(type3);
		c.action(this);
		type3.remove(c);
	}

	public Lokalgruppe lokalgruppeFraNavn(String navn) {
		for (Lokalgruppe lg : lokalgrupper) {
			if (lg.getNavn().equals(navn))
				return lg;
		}
		return null;
	}

	private By byFraNavn(String navn) {
		for (By lg : byer) {
			if (lg.getNavn().equals(navn))
				return lg;
		}
		return null;
	}

	public void toAll(ClientPacket<MainView> packet) {
		for (ServerSpiller sp : serverSpillere) {
			sp.stack(packet);
		}
	}

	private void parseScenarie() {
		int number = 0;
		try {
			reader = new BufferedReader(new FileReader("res/scenarie1.txt"));
			String line = reader.readLine();
			while (line != null) {
				number++;
				scanner = new Scanner(line);
				String navn;
				ArrayList<Medlem> nyeMedlemmer;
				Lokalgruppe lokalgruppe = null;
				Region nyRegion = null;
				while (scanner.hasNext()) {
					String f = scanner.next();
					if (f.startsWith("#")) {
						navn = f.substring(1);
						f = scanner.next();
						String regionNavn = f.substring(1);
						for (Region region : regioner) {
							if (region.getNavn().equals(regionNavn))
								nyRegion = region;
						}
						if (nyRegion == null) {
							nyRegion = new Region(regionNavn, regionCounter);
							regionCounter++;
							regioner.add(nyRegion);
						}
						lokalgruppe = new Lokalgruppe(navn, lokalgruppeCounter,
								nyRegion);
						lokalgruppeCounter++;
						nyRegion.tilføjLokalgruppe(lokalgruppe);
						lokalgrupper.add(lokalgruppe);
						while (scanner.hasNext()) {
							f = scanner.next();
							while (f.startsWith("@")) {
								f = scanner.next();
							}
							if (f.startsWith("X:")) {
								lokalgruppe.setX(Integer.parseInt(f
										.substring(2)));
								continue;
							}
							if (f.startsWith("Y:")) {
								lokalgruppe.setY(Integer.parseInt(f
										.substring(2)));
								continue;
							}
							int antal = Integer.parseInt(f);
							f = scanner.next();
							parseMembers(antal, f, lokalgruppe);
						}
					} else if (f.startsWith("£")) {
						navn = f.substring(1);
						f = scanner.next();
						String regionNavn = f.substring(1);
						for (Region region : regioner) {
							if (region.getNavn().equals(regionNavn))
								nyRegion = region;
						}
						if (nyRegion == null) {
							nyRegion = new Region(regionNavn, regionCounter);
							regionCounter++;
							regioner.add(nyRegion);
						}
						By by = new By(navn, byCounter, nyRegion);
						byCounter++;
						nyRegion.tilføjBy(by);
						byer.add(by);
						while (scanner.hasNext()) {
							f = scanner.next();
							if (f.startsWith("X:")) {
								by.setX(Integer.parseInt(f.substring(2)));
							}
							if (f.startsWith("Y:")) {
								by.setY(Integer.parseInt(f.substring(2)));
							}
						}
					}
				}
				line = reader.readLine();
			}
			reader = new BufferedReader(new FileReader("res/scenarie1.txt"));
			line = reader.readLine();
			number = 0;
			while (line != null) {
				number++;
				Lokalgruppe thisLG = null;
				By thisBy = null;
				scanner = new Scanner(line);
				while (scanner.hasNext()) {
					String f = scanner.next();
					if (f.startsWith("#")) {
						thisLG = this.lokalgruppeFraNavn(f.substring(1));
					}
					if (f.startsWith("£")) {
						thisBy = this.byFraNavn(f.substring(1));
					}
					if (f.startsWith("@")) {
						Lokalgruppe lg = this
								.lokalgruppeFraNavn(f.substring(1));
						if (lg == null) {
							By b = this.byFraNavn(f.substring(1));
							if (b == null) {
								System.out.println("Fejl: " + number);
							}
							if (thisLG != null)
								thisLG.tilføjEkstraNabo(b);
							else if (thisBy != null)
								thisBy.tilføjEkstraNabo(b);
						} else if (thisLG != null)
							thisLG.tilføjEkstraNabo(lg);
						else if (thisBy != null)
							thisBy.tilføjEkstraNabo(lg);
					}
				}
				line = reader.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseMembers(int antal, String f, Lokalgruppe lokalgruppe) {
		for (int i = 0; i < antal; i++) {
			Medlem medlem = new Medlem(f, medlemCounter);
			medlemCounter++;
			lokalgruppe.tilføjMedlem(medlem);
			medlemmer.add(medlem);
		}
	}

	public void playerAdded(PlayerConnection pCon) {
		String farve;
		if (idCounter == 0)
			farve = "Rød";
		else if (idCounter == 1)
			farve = "Sort";
		else if (idCounter == 2)
			farve = "Grøn";
		else
			farve = "Lilla";
		ServerSpiller serverPlayer = new ServerSpiller(pCon, idCounter, farve,
				this);
		System.out.println("Player added with id " + idCounter);
		idCounter++;
		serverSpillere.add(serverPlayer);
	}

	public void parse(AbstractServerPlayer serverPlayer) {
		for (ServerPacket p : serverPlayer.getRecievedPackts()) {
			p.parse(this, serverPlayer);
		}
		serverPlayer.clearRecievedPackets();
	}

	public static void main(String[] args) {
		new Thread(new Server(new FakeRandomizer())).start();
	}

	public Region regionFraNavn(String navn) {
		for (Region reg : regioner) {
			if (reg.getNavn().equals(navn))
				return reg;
		}
		return null;
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

	public ArrayList<Lokalgruppe> getLokalgrupper() {
		return lokalgrupper;
	}

	public ArrayList<Region> getRegioner() {
		return regioner;
	}

	public ServerState getState() {
		return state;
	}

	
	public  void udførOrdre() {
		for(Ordre ordre : ordrer){
			ordre.udfør(ordrerOwner.get(ordre), this);
		}
		ordrer.clear();
		ordrerOwner.clear();
	}

	public  void tilføjOrdre(Ordre ordre, ServerSpiller serverPlayer) {
		ArrayList<Ordre> toBeRemoved = new ArrayList<Ordre>();
		for (Ordre tmp : ordrer) {
			if (tmp.getLokalgruppeID() == ordre.getLokalgruppeID()) {
				toBeRemoved.add(tmp);
			}
		}
		for(Ordre tmp : toBeRemoved){
			ordrer.remove(tmp);
			ordrerOwner.remove(tmp);
		}
		toBeRemoved.clear();
		ordrer.add(ordre);
		ordrerOwner.put(ordre, serverPlayer);
	}

	public  void sendOrdre() {
		for(Ordre ordre : ordrer){
			toAll(new OrdreAddedPacket(ordre.getName(), ordre
					.getLokalgruppeID()));
		}
	}
}
