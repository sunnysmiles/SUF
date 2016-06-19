package test;

import static org.junit.Assert.*;
import game.client.Game;
import game.server.Server;
import game.server.random.RealRandomizer;

import org.junit.Before;
import org.junit.Test;

public class Test2 {

	private Game g1;
	private Game g2;
	private Server server;

	@Before
	public void setUp() throws Exception {
		server = new Server(new RealRandomizer());
		new Thread(server).start();
		waits();
		g1 = new Game();
		new Thread(g1).start();
		waits();
		g2 = new Game();
		new Thread(g2).start();
		waits();
		server.addCommand("Start");
		waits();
	}

	@Test
	public void test() {
		assertEquals("Rød", g1.farve);
		assertEquals("Sort", g2.farve);
	}
	
	public static void waits(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
