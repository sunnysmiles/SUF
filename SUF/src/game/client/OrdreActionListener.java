package game.client;

import game.network.OrdrePacket;
import game.shared.ordrer.Ordre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrdreActionListener implements ActionListener {

	private Ordre ordre;
	private MainView mainView;

	public OrdreActionListener(Ordre ordre, MainView mainView) {
		this.ordre = ordre;
		this.mainView = mainView;
	}

	public void actionPerformed(ActionEvent e) {
		mainView.getGame().addOrdre(ordre);
		mainView.lgView.update();
		if(mainView.medlemView.isVisible()){
			mainView.medlemView.update();
		}
	}

}
