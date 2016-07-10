package game.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;


import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import engine.utils.SquareIcon;
import game.shared.Ledelse;
import game.shared.Medlem;

public class LedelseView extends JFrame {
	private static final long serialVersionUID = 1L;
	private Ledelse ledelsen = null;
	private JScrollPane listScroller;
	private JPanel panel;
	
	public LedelseView(){
		this.setTitle("Ledelsen i SUF");
	}

	public void show(final Ledelse ledelsen) {
		if(ledelsen == null) return;
		this.ledelsen = ledelsen;
		if (listScroller != null)
			this.remove(listScroller);
		panel = new JPanel();
		JLabel label = null;
		ArrayList<Medlem> ledelseMedlemmer = ledelsen.getAlle();
		for (Medlem m : ledelseMedlemmer) {
			if(m == null) continue;
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

	public void update() {
		if (ledelsen != null)
			this.show(ledelsen);
	}
}
