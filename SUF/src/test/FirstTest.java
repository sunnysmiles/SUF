package test;

import game.client.Game;
import game.server.Server;
import game.server.random.RealRandomizer;

public class FirstTest {
	public static void main(String[] args){
		Server server = new Server(new RealRandomizer());
		new Thread(server).start();
		waits();
		Game g1 = new Game();
		new Thread(g1).start();
		waits();
		Game g2 = new Game();
		new Thread(g2).start();
		waits();
		server.addCommand("Start");
		
	}
	
	public static void waits(){
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
