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
import game.shared.ordrer.SkolingsOrdre;

import org.junit.After;
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
		waitForState(ClientGameState.ORDRER, g1, g2);
	}

	//Checks farve assignment, timecards-routine, and ordre-packets
	@Test
	public void test() {
		assertEquals("Rød", g1.farve);
		assertEquals("Sort", g2.farve);
		assertEquals(
				server.lokalgruppeFraNavn("Aalborg").getMedlemmer().size(), 7);
		g1.addOrdre(new HvervningsOrdre(g1.lokalgruppeFraNavn("Aalborg")
				.getId()));
		readyWaitForState(ClientGameState.KOO, g1, g2);
		readyWaitForState(ClientGameState.PRE_START, g1, g2);
		assertEquals(
				server.lokalgruppeFraNavn("Aalborg").getMedlemmer().size(), 8);
		readyWaitForState(ClientGameState.ORDRER, g1, g2);
		assertEquals(
				server.lokalgruppeFraNavn("Aalborg").getMedlemmer().size(), 5);
	}
	
	public static void printJournal(Game g){
		for (MonthEntry me : g.getJournal().getEntries()) {
			for (Entry e : me.getEntries()) {
				System.out.println(e.getText());
			}
		}
	}
	
	@Test
	public void testSkiftFarve() {
		assertEquals(3, server.lokalgruppeFraNavn("Kolding").numberOfFarve("Sort"));
		assertEquals(2, server.lokalgruppeFraNavn("Kolding").numberOfFarve("Hvid"));
		g2.addOrdre(new SkolingsOrdre(server.lokalgruppeFraNavn("Kolding").getId()));
		readyWaitForState(ClientGameState.KOO, g1, g2);
		readyWaitForState(ClientGameState.PRE_START, g1, g2);
		assertEquals(server.lokalgruppeFraNavn("Kolding").numberOfFarve("Sort"), 4);
		assertEquals(server.lokalgruppeFraNavn("Kolding").numberOfFarve("Hvid"), 1);
	}
	
	@After
	public void cleanUp(){
		server.stop();
		waitsLong();
	}
	
	public static void readyWaitForState(ClientGameState state, Game g1, Game g2){
		g1.ready();
		g2.ready();
		while(g1.getState() != state || g2.getState() != state)
			waitsShort();
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
