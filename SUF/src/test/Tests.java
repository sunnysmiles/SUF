package test;

import static org.junit.Assert.*;
import game.client.Game;
import game.client.Game.ClientGameState;
import game.server.Server;
import game.server.Server.ServerState;
import game.server.random.FakeRandomizer;
import game.server.random.RealRandomizer;
import game.shared.Entry;
import game.shared.MonthEntry;
import game.shared.ordrer.HvervningsOrdre;

import org.junit.Before;
import org.junit.Test;

public class Tests {

	private Game g1;
	private Game g2;
	private Server server;

	@Before
	public void setUp() throws Exception {
		server = new Server(new FakeRandomizer());
		new Thread(server).start();
		waitsShort();
		g1 = new Game();
		new Thread(g1).start();
		waitsShort();
		g2 = new Game();
		new Thread(g2).start();
		waitsShort();
		server.addCommand("Start");
		while (g1.getState() != ClientGameState.ORDRER)
			waitsShort();
	}

	@Test
	public void test() {
		assertEquals("Rød", g1.farve);
		assertEquals("Sort", g2.farve);
		for (MonthEntry me : g1.getJournal().getEntries()) {
			for (Entry e : me.getEntries()) {
				System.out.println(e.getText());
			}
		}
		assertEquals(
				server.lokalgruppeFraNavn("Aalborg").getMedlemmer().size(), 7);
		g1.addOrdre(new HvervningsOrdre(g1.lokalgruppeFraNavn("Aalborg")
				.getId()));
		g1.ready();
		g2.ready();
		waitForState(ClientGameState.KOO, g1, g2);
		g1.ready();
		g2.ready();
		waitForState(ClientGameState.PRE_START, g1, g2);
		assertEquals(
				server.lokalgruppeFraNavn("Aalborg").getMedlemmer().size(), 8);
		server.stop();
		waitsLong();
	}
	
	@Test
	public void test2() {
		assertEquals("Rød", g1.farve);
		assertEquals("Sort", g2.farve);
		for (MonthEntry me : g1.getJournal().getEntries()) {
			for (Entry e : me.getEntries()) {
				System.out.println(e.getText());
			}
		}
		assertEquals(
				server.lokalgruppeFraNavn("Aalborg").getMedlemmer().size(), 7);
		g1.addOrdre(new HvervningsOrdre(g1.lokalgruppeFraNavn("Aalborg")
				.getId()));
		g1.ready();
		g2.ready();
		waitForState(ClientGameState.KOO, g1, g2);
		g1.ready();
		g2.ready();
		waitForState(ClientGameState.PRE_START, g1, g2);
		assertEquals(
				server.lokalgruppeFraNavn("Aalborg").getMedlemmer().size(), 8);
	}

	public static void waitForState(ClientGameState state, Game g1, Game g2){
		while(g1.getState() != state || g2.getState() != state)
			waitsShort();
	}
	public static void waitsLong() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void waitsShort() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
