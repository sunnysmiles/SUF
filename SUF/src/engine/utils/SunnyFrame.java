package engine.utils;

import engine.client.AbstractGame;
import javax.swing.JFrame;

public class SunnyFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SunnyFrame(AbstractGame g) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(0, 0);
		setSize(g.getPixelsWidth()
				* g.getMulti(), g.getPixelsHeight() * g.getMulti());
		g.setFrame(this);
		add(g.getCanvas());
		setResizable(true);
		setIgnoreRepaint(true);
		setVisible(true);
		new Thread(g).start();
	}
}
