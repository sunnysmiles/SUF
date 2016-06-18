package engine.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import engine.network.ClientPacket;
import engine.network.ServerPacket;

public class PlayerConnection implements Runnable{
	private ArrayList<ServerPacket> recievedPackets;
	private ObjectOutputStream oOutStream;
	private InputStream inStream;
	private ObjectInputStream oInStream;
	private ArrayList<ClientPacket> toSendPackets;
	private boolean running;

	public PlayerConnection(Socket s){
		recievedPackets = new ArrayList<ServerPacket>();
		toSendPackets = new ArrayList<ClientPacket>();
		try {
			oOutStream = new ObjectOutputStream(s.getOutputStream());
			oOutStream.flush();
			inStream = s.getInputStream();
			oInStream = new ObjectInputStream(inStream);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		running = true;
		while (running) {
			try {
				if (inStream.available() > 0) {
					ServerPacket p = (ServerPacket) oInStream.readObject();
					addToRecieved(p);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void sendData() {
		for(ClientPacket p : toSendPackets){
			try {
				oOutStream.writeObject(p);
			} catch (IOException e) {
			}
		}
		toSendPackets.clear();
	}
	public synchronized void stack(ClientPacket p){
		toSendPackets.add(p);
	}
	
	public synchronized void addToRecieved(ServerPacket p){
		recievedPackets.add(p);
	}
	public synchronized ArrayList<ServerPacket> getRecievedPackets(){
		return recievedPackets;
	}
	
	public synchronized void parse(Parser parser, AbstractServerPlayer abstractServerPlayer){
		parser.parse(abstractServerPlayer);
	}
	
	public synchronized void clearRecievedPackets(){
		recievedPackets.clear();
	}

	public void stop() {
		running = false;
	}
}
