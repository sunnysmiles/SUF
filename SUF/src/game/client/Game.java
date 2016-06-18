package game.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.client.AbstractGame;
import engine.client.Connection;
import engine.network.ServerPacket;
import engine.utils.Input;
import engine.utils.Screen;
import engine.utils.SquareIcon;
import engine.utils.SunnyFrame;
import game.network.ClientReadyPacket;
import game.server.Server;
import game.server.ServerSpiller;
import game.shared.By;
import game.shared.Journal;
import game.shared.Ledelse;
import game.shared.Lokalgruppe;
import game.shared.Medlem;
import game.shared.MonthEntry;
import game.shared.Region;
import game.shared.Stats;

public class Game extends AbstractGame {

	private Connection connection;
	private Art art;
	private Input input;
	private Screen screen;

	public enum ClientState {
		PRE_START, ORDRER, KOO, LEDELSE_KOO_TILLID, LEDELSE_FORSLAG, LEDELSE_KOO_VALG
	};

	public ClientState state = ClientState.PRE_START;
	public ArrayList<By> byer;
	public ArrayList<Medlem> medlemmer;
	public ArrayList<Region> regioner;
	public ArrayList<Lokalgruppe> lokalgrupper;
	public ArrayList<Spiller> spillere;
	public Journal journal;
	public String month;
	public JournalView journalView;
	public LokalgruppeView lgView;
	public int offSet;
	public String farve;
	public Stats stats;
	public MedlemmerView medlemView = new MedlemmerView(this);
	public final JLabel iconLabel = new JLabel();
	public Ledelse ledelsen;

	public void init() {
		offSet = 0;
		connection = connectToServer();
		missingBeatPackets = 0;
		art = new Art();
		try {
			art.load();
		} catch (IOException e) {
			System.out.println("Failed loading the art");
			e.printStackTrace();
		}
		input = getInput();
		screen = getScreen();
		journal = new Journal();
		journal.addEntry(new MonthEntry("Januar"));
		state = ClientState.PRE_START;
		// Views
		journalView = new JournalView(journal);
		// Buttons
		JPanel panel = new JPanel();
		JButton ready = new JButton();
		ready.setText("Afslut tur");
		JButton lgButton = new JButton();
		lgButton.setText("Lokalgrupper");
		JButton journal = new JButton();
		journal.setText("Journal");
		JButton ledelse = new JButton();
		ledelse.setText("Ledelse");
		panel.add(ledelse);
		panel.add(journal);
		panel.add(lgButton);
		panel.add(ready);
		iconLabel.setIcon(new SquareIcon(Color.red));
		panel.add(iconLabel);
		frame.add(panel, BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
		this.setMulit(1);
		this.setPixelsHeight(700);
		this.setPixelsWidth(1300);
		// Button Actions
		ready.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iconLabel.setIcon(new SquareIcon(Color.green));
				connection.stack(new ClientReadyPacket());
			}
		});
		journal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				journalView.update();
				journalView.open();
			}
		});
		final Game game = this;
		lgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// if (lokalgrupper != null)
				// lgView = new LokalgruppeView(lokalgrupper, game);
				// lgView.setVisible(true);
				if (lgView != null) {
					lgView.update();
					lgView.setVisible(true);
				}
			}
		});
		this.getCanvas().addMouseListener(new LokalgruppeClickListener(this));
	}

	private void draw() {
		offSet = (this.getCanvas().getWidth() - art.danmark.getWidth()) / 2;
		Art.drawSquare(screen, 0, 0, screen.getWidth(), screen.getHeight(),
				0xff0D5DD1);
		art.danmark.blit(screen, offSet, 0);
		for (Lokalgruppe lg : lokalgrupper) {
			if (lg.getX() != 0 || lg.getY() != 0) {
				int col = 0;
				if (lg.getFarve().equals("Rød"))
					col = 0xffff0000;
				if (lg.getFarve().equals("Hvid"))
					col = 0xffffffff;
				if (lg.getFarve().equals("Sort"))
					col = 0xff000000;
				if (lg.getFarve().equals("Lilla"))
					col = 0xffA56EFF;
				if (lg.getFarve().equals("Grøn"))
					col = 0xff00ff00;
				Art.drawSquare(screen, lg.getX() + offSet, lg.getY(), 10, 10,
						col);
			}
		}
		for (By lg : byer) {
			if (lg.getX() != 0 || lg.getY() != 0) {
				Art.drawSquare(screen, lg.getX() + offSet, lg.getY(), 10, 10,
						0xff5e5e5e);
			}
		}
	}

	public void update() {
		fromServer();
		switch (state) {
		case PRE_START:
			missingBeatPackets = 0;
			break;
		case ORDRER:
			draw();
			break;
		default:
			draw();
			missingBeatPackets = 0;
			break;
		}
	}

	private void fromServer() {
		missingBeatPackets++;
		connection.parse(this);
		if (missingBeatPackets > 2000) {
			System.out.println("Server unresponsive");
			stopGame();
		}
	}

	public static void main(String[] args) {
		new SunnyFrame(new Game());
	}

	public Medlem medlemFraID(int id) {
		for (Medlem m : medlemmer) {
			if (m.getId() == id)
				return m;
		}
		return null;
	}

	public Region regionFraID(int id) {
		for (Region r : regioner) {
			if (r.getId() == id)
				return r;
		}
		return null;
	}

	public Lokalgruppe lokalgruppeFraID(int id) {
		for (Lokalgruppe l : lokalgrupper) {
			if (l.getId() == id)
				return l;
		}
		return null;
	}

	public By byFraID(int id) {
		for (By by : byer) {
			if (by.getId() == id)
				return by;
		}
		return null;
	}

	public void stack(ServerPacket<Server, ServerSpiller> packet) {
		connection.stack(packet);
	}
}
