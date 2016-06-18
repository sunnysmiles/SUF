package game.client;

import game.client.Game.ClientState;
import game.shared.Lokalgruppe;
import game.shared.ordrer.HvervningsOrdre;
import game.shared.ordrer.SkolingOrdre;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class LokalgruppeClickListener implements MouseListener {
	private Game game;

	public LokalgruppeClickListener(Game game) {
		this.game = game;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON3)
			return;
		if (game.state != ClientState.ORDRER)
			return;
		for (Lokalgruppe lg : game.lokalgrupper) {
			int x = (int) Math.floor((float) (lg.getX() + game.offSet)
					* ((float) game.getCanvas().getWidth() / 1300f));
			int y = (int) Math.floor((float) lg.getY()
					* ((float) game.getCanvas().getHeight() / 700f));

			if (lg.getNavn().equals("Sydhavsøerne"))
				System.out.println(x + " " + e.getX() + " " + y + " "
						+ e.getY());
			if (!lg.getFarve().equals(game.farve))
				continue;

			if (e.getX() >= x && e.getX() <= x + 10)
				if (e.getY() >= y && e.getY() <= y + 10) {
					new LokalgruppePopup(lg, game, e, true);
				}
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
