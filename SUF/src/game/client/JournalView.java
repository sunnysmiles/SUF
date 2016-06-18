package game.client;

import game.shared.Entry;
import game.shared.Journal;
import game.shared.MonthEntry;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

public class JournalView extends JFrame {
	private Journal journal;
	JTextArea area = new JTextArea(30, 30);

	public JournalView(Journal journal) {
		this.journal = journal;
		Font font = new Font("Verdana", Font.BOLD, 16);
		area.setFont(font);
		area.setText("");
		area.setWrapStyleWord(true);
		area.setEditable(false);
		JScrollPane scrollingArea = new JScrollPane(area);

		// ... Get the content pane, set layout, add to center
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(scrollingArea, BorderLayout.CENTER);

		// ... Set window characteristics.
		this.setContentPane(content);
		this.setTitle("Journal");
		this.pack();
	}

	public void update() {
		area.setText("");
		if (journal.entries == null)
			return;
		for (int i = journal.entries.size()-1; i >= 0; i--) {
			MonthEntry e = journal.entries.get(i);
			area.setText(area.getText() + e.title + "\n");
			if (e.entries == null)
				continue;
			for (Entry entry : e.entries) {
				area.setText(area.getText() + entry.text + "\n");
			}
		}
		area.setVisible(true);
		this.pack();
		System.out.println(area.getText());
	}

	public void close() {
		this.setVisible(false);
	}

	public void open() {
		this.setVisible(true);
	}
}
