package game.client;

import game.client.Game.ClienGameState;
import game.shared.Lokalgruppe;
import game.shared.ordrer.HvervningsOrdre;
import game.shared.ordrer.SkolingOrdre;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class LokalgruppeClickListener implements MouseListener {
	private MainView mainView;

	public LokalgruppeClickListener(MainView mainView) {
		this.mainView = mainView;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON3)
			return;
		//Check if in command-state
		if (mainView.getGame().getState() != ClienGameState.ORDRER)
			return;
		//Iterate over all lokalgrupper
		for (Lokalgruppe lg : mainView.getGame().getLokalgrupper()) {
			//Check if the lokalgruppe belongs to us 
			if (!lg.getFarve().equals(mainView.getGame().getFarve()))
				continue;
			
			//Calculate the real X and Y coordinates
			int x = (int) Math.floor((float) (lg.getX() + mainView.offSet)
					* ((float) mainView.getCanvas().getWidth() / 1300f));
			int y = (int) Math.floor((float) lg.getY()
					* ((float) mainView.getCanvas().getHeight() / 700f));
			
			//Check if the lokalgruppe is clicked
			if (e.getX() >= x && e.getX() <= x + 10)
				if (e.getY() >= y && e.getY() <= y + 10) {
					new LokalgruppePopup(lg, mainView, e, true);
				}
		}
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

}
