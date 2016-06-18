package engine.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener extends Thread{
	ServerSocket serverSocket;
	private AbstractServer server;
	
	public Listener(AbstractServer game){
		try {
			serverSocket = new ServerSocket(112);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.server = game;
	}
	
	public void run(){
		while(true){
			try {
				Socket s = serverSocket.accept();
				server.addPlayer(s);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
