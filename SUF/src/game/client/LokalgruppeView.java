package game.client;

import engine.utils.SquareIcon;
import game.shared.Lokalgruppe;
import game.shared.ordrer.HvervningsOrdre;
import game.shared.ordrer.SkolingOrdre;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

public class LokalgruppeView extends JFrame {
	private ArrayList<Lokalgruppe> grupper;
	private Game game;
	private JPanel panel;
	private JScrollPane listScroller;

	public LokalgruppeView(ArrayList<Lokalgruppe> grupper, Game game) {
		this.grupper = grupper;
		this.game = game;
		update();
	}

	public void update() {
		// String[] labels = new String[3];
		// labels[0] = "Første";
		// labels[1] = "Anden";
		// labels[2] = "tredje";
		// JList<String> list = new JList<String>(labels);
		if (listScroller != null)
			this.remove(listScroller);
		panel = new JPanel();
		for (Lokalgruppe lg : grupper) {
			JLabel label = new JLabel();
			label.setFont(new Font("Serif", Font.PLAIN, 20));
			label.setText(lg.getNavn() + "; Region: " + lg.getReg().getNavn()
					+ "; Ordre: " + lg.getOrdre());
			if (lg.getFarve().equals("Rød")) {
				label.setIcon(new SquareIcon(Color.red));
			}
			if (lg.getFarve().equals("Sort")) {
				label.setIcon(new SquareIcon(Color.black));
			}
			if (lg.getFarve().equals("Lilla")) {
				label.setIcon(new SquareIcon(new Color(165, 110, 255, 255)));
			}
			if (lg.getFarve().equals("Grøn")) {
				label.setIcon(new SquareIcon(Color.green));
			}
			if (lg.getFarve().equals("Hvid")) {
				label.setIcon(new SquareIcon(Color.white));
			}
			final Lokalgruppe listenerLG = lg;
			label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					new LokalgruppePopup(listenerLG, game, e, true);
				}
			});
			panel.add(label);
		}
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		listScroller = new JScrollPane(panel);
		this.add(listScroller);
		this.setPreferredSize(new Dimension(600, 700));
		this.pack();
		this.repaint();
	}
}

