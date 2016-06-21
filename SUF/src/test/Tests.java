package test;

import static org.junit.Assert.*;
import game.client.Game;
import game.server.Server;
import game.server.random.RealRandomizer;

import org.junit.Before;
import org.junit.Test;

public class Tests {

	private Game g1;
	private Game g2;
	private Server server;

	@Before
	public void setUp() throws Exception {
		server = new Server(new RealRandomizer());
		new Thread(server).start();
		waitsShort();
		g1 = new Game();
		new Thread(g1).start();
		waitsShort();
		g2 = new Game();
		new Thread(g2).start();
		waitsLong();
		server.addCommand("Start");
		while(!server.gameStarted)
			waitsShort();
		waitsShort();
	}

	@Test
	public void test() {
		assertEquals("Rød", g1.farve);
		assertEquals("Sort", g2.farve);
	}
	
	public static void waitsLong(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void waitsShort(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
