package engine.server;

import java.net.Socket;
import java.util.ArrayList;

import engine.network.HeartBeatPacket;

public abstract class AbstractServer implements Runnable {
	private Listener listener;

	private double nsPerTick;
	private double nsPerSend;
	
	private ArrayList<PlayerConnection> playerConnections;

	protected int idCounter;
	private boolean running;
	private long lastTime;
	private long lastPrint;
	private long curTime;
	private double delta;
	private double deltaS;
	private int ticks;
	private boolean showStats;

	private Thread listenerThread;

	public abstract void tick();
	public abstract void init();
	public abstract void playerAdded(PlayerConnection s);

	public void run() {
		nsPerTick = 1000000000 / 60D;
		nsPerSend = 1000000000 / 60D;
		idCounter = 0;
		listener = new Listener(this);
		listenerThread = new Thread(listener);
		listenerThread.start();
		playerConnections = new ArrayList<PlayerConnection>();
		init();
		running = true;
		lastTime = System.nanoTime();
		lastPrint = System.currentTimeMillis();
		while (running) {
			curTime = System.nanoTime();
			delta += (curTime - lastTime) / nsPerTick;
			deltaS += (curTime - lastTime) / nsPerSend;
			lastTime = curTime;
			while (delta >= 1) {
				ticks++;
				tick();
				delta--;
			}
			if (deltaS >= 1) {
				sendData();
				deltaS = 0;
			}
			if (System.currentTimeMillis() - lastPrint >= 1000) {
				if(showStats)System.out.println("TPS: " + ticks);
				lastPrint += 1000;
				ticks = 0;
			}
		}
	}
	
	public boolean getRunning(){
		return running;
	}
	
	public void stop(){
		running = false;
		listener.stop();
			listenerThread.stop();
//			listenerThread.join();
	}
	
	public void showStats(boolean showStats){
		this.showStats = showStats;
	}
	
	private void sendData(){
		for(PlayerConnection con : playerConnections){
			con.stack(new HeartBeatPacket());
			con.sendData();
		}
	}
	
	public void addPlayer(Socket s){
		PlayerConnection con = new PlayerConnection(s);
		playerConnections.add(con);
		playerAdded(con);
		new Thread(con).start();
	}
	
	public ArrayList<PlayerConnection> getPlayerConnections(){
		return playerConnections;
	}

	public double getNsPerTick() {
		return nsPerTick;
	}

	public void setTickPerSecond(double UPS) {
		this.nsPerTick = 1000000000 / UPS;
	}

	public double getNsPerSend() {
		return nsPerSend;
	}

	public void setSendPerSecond(double SPS) {
		this.nsPerSend = 1000000000 / SPS;
	}
}
