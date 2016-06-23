package engine.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener implements Runnable{
	ServerSocket serverSocket;
	private AbstractServer server;
	private boolean running = true;
	
	public Listener(AbstractServer game){
		try {
			serverSocket = new ServerSocket(112);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.server = game;
	}
	
	public void run(){
		while(running){
			try {
				Socket s = serverSocket.accept();
				server.addPlayer(s);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	public void stop(){
		running = false;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
