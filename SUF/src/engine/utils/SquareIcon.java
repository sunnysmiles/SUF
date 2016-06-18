package engine.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class SquareIcon implements Icon {
	private Color color;

	public SquareIcon(Color color) {
		this.color = color;
	}

	public int getIconHeight() {
		return 20;
	}

	public int getIconWidth() {
		return 20;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(color);
		g.fillRect(x, y, 20, 20);
	}
}