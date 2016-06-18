package game.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import engine.utils.SquareIcon;
import game.shared.Lokalgruppe;
import game.shared.Medlem;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MedlemmerView extends JFrame {
	private Game game;
	private Lokalgruppe lg;
	private JScrollPane listScroller;
	private JPanel panel;

	public MedlemmerView(Game game) {
		this.game = game;
	}

	public void show(final Lokalgruppe lg) {
		this.lg = lg;
		if (listScroller != null)
			this.remove(listScroller);
		panel = new JPanel();
		JLabel label = new JLabel();
		label.setFont(new Font("Serif", Font.PLAIN, 24));
		label.setText(lg.getNavn() + " - " + lg.getOrdre());
		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new LokalgruppePopup(lg, game, e, false);
			}
		});
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
		panel.add(label);
		for (Medlem m : lg.getMedlemmer()) {
			label = new JLabel();
			label.setFont(new Font("Serif", Font.PLAIN, 20));
			label.setText(m.navn);
			if (m.getFarve().equals("Rød")) {
				label.setIcon(new SquareIcon(Color.red));
			}
			if (m.getFarve().equals("Sort")) {
				label.setIcon(new SquareIcon(Color.black));
			}
			if (m.getFarve().equals("Lilla")) {
				label.setIcon(new SquareIcon(new Color(165, 110, 255, 255)));
			}
			if (m.getFarve().equals("Grøn")) {
				label.setIcon(new SquareIcon(Color.green));
			}
			if (m.getFarve().equals("Hvid")) {
				label.setIcon(new SquareIcon(Color.white));
			}
			panel.add(label);
		}
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		listScroller = new JScrollPane(panel);

		this.add(listScroller);
		this.setPreferredSize(new Dimension(600, 700));
		this.pack();
		this.repaint();
		this.setVisible(true);
	}
	public void update(){
		this.show(lg);
	}
}
