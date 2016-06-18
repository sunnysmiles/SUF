package game.network;

import java.util.ArrayList;

import engine.network.ClientPacket;
import engine.network.ServerPacket;
import game.client.Game;
import game.client.Game.ClientState;
import game.client.LokalgruppeView;
import game.client.Spiller;
import game.server.Server;
import game.server.ServerSpiller;
import game.shared.By;
import game.shared.Ledelse;
import game.shared.Lokalgruppe;
import game.shared.Medlem;
import game.shared.Region;
import game.shared.Stats;

public class StartGamePacket implements ClientPacket<Game> {

	private ArrayList<By> byer;
	private ArrayList<Region> regioner;
	private ArrayList<Lokalgruppe> lokalgrupper;
	private ArrayList<Medlem> medlemmer;
	private ArrayList<Spiller> clientSpillere;
	private Stats stats;
	private Ledelse ledelsen;
	
	public StartGamePacket(ArrayList<Region> regioner,
			ArrayList<Lokalgruppe> lokalgrupper, ArrayList<Medlem> medlemmer,
			ArrayList<By> byer, ArrayList<ServerSpiller> spillere, Stats stats, Ledelse ledelsen) {
		this.byer = byer;
		this.regioner = regioner;
		this.lokalgrupper = lokalgrupper;
		this.medlemmer = medlemmer;
		this.stats = stats;
		clientSpillere = new ArrayList<Spiller>();
		for(ServerSpiller sp : spillere){
			clientSpillere.add(new Spiller(sp.getFarve()));
		}
		this.ledelsen = ledelsen;
	}

	public void parse(Game game) {
		game.byer = byer;
		game.medlemmer = medlemmer;
		game.regioner = regioner;
		game.lokalgrupper = lokalgrupper;
		game.spillere = clientSpillere;
		game.month = "Januar";
		game.lgView = new LokalgruppeView(game.lokalgrupper, game);
		game.state = ClientState.ORDRER;
		game.stack(new ClientReadyPacket());
		game.stats = stats;
		game.ledelsen = new Ledelse(ledelsen, game);
	}

}
