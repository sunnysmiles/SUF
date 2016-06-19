package game.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerConsole implements Runnable{

	BufferedReader br;
	Server server;
	
	public ServerConsole(Server server){
		br = new BufferedReader(new InputStreamReader(System.in));
		this.server = server;
	}
	
	public void run() {
		while(true){
        String s = "";
        try {
			s = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	server.addCommand(s);
		}
	}

}
