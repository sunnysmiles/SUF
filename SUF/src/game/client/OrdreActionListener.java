package game.client;

import game.network.OrdrePacket;
import game.shared.ordrer.Ordre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrdreActionListener implements ActionListener {

	private Ordre ordre;
	private Game game;

	public OrdreActionListener(Ordre ordre, Game game) {
		this.ordre = ordre;
		this.game = game;
	}

	public void actionPerformed(ActionEvent e) {
		if(!game.farve.equals(game.lokalgruppeFraID(ordre.getLokalgruppeID()).getFarve()))
			return;
		game.getConnection().stack(new OrdrePacket(ordre));
		game.lokalgruppeFraID(ordre.getLokalgruppeID()).tilføjOrdre(
				ordre.getName());
		game.lgView.update();
		if(game.medlemView.isVisible()){
			game.medlemView.update();
		}
	}

}
