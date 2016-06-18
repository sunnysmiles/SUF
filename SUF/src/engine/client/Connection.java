package engine.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import engine.network.ClientPacket;
import engine.network.ServerPacket;

public class Connection implements Runnable {
	Socket con;
	public InputStream inStream;
	public OutputStream outStream;
	public ObjectInputStream oInStream;
	public ObjectOutputStream oOutStream;
	ArrayList<ServerPacket> packets;
	private ArrayList<ClientPacket> recievedPackets;
	private boolean running;

	/*
	 * Connection is instantiated by the FrameGame class when the a server
	 * connection is established. The class handles the communication Between
	 * the client and the server.
	 */
	public Connection() {
		packets = new ArrayList<ServerPacket>();
		recievedPackets = new ArrayList<ClientPacket>();
		try {
			con = new Socket("localhost", 112);
			outStream = con.getOutputStream();
			oOutStream = new ObjectOutputStream(outStream);
			oOutStream.flush();
			inStream = con.getInputStream();
			oInStream = new ObjectInputStream(inStream);
		} catch (UnknownHostException e) {
			System.out.println("unknown host");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		running = true;
		while (running) {
			try {
				if (inStream.available() > 0) {
					ClientPacket p = (ClientPacket) oInStream.readObject();
					addToRecieved(p);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stop(){
		running = false;
	}
	
	
	public synchronized void parse(AbstractGame game){
		for(ClientPacket p : recievedPackets)
			p.parse(game);
		clearRecievedPackets();
	}
	private synchronized void addToRecieved(ClientPacket p) {
		recievedPackets.add(p);
	}

	public synchronized ArrayList<ClientPacket> getRecievedPackets() {
		return recievedPackets;
	}

	public synchronized void clearRecievedPackets() {
		recievedPackets.clear();
	}

	public void sendData() throws IOException {
		for (ServerPacket p : packets) {
			oOutStream.writeObject(p);
		}
		packets.clear();
	}

	public void stack(ServerPacket packet) {
		packets.add(packet);
	}
}