package game.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import game.shared.Lokalgruppe;
import game.shared.ordrer.HvervningsOrdre;
import game.shared.ordrer.SkolingOrdre;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class LokalgruppePopup extends JPopupMenu {
	public LokalgruppePopup(Lokalgruppe listenerLG, MainView mainView,
			MouseEvent e, boolean visDetaljer) {
		JMenuItem hvervning = new JMenuItem("Hvervning");

		if (visDetaljer) {
			JMenuItem view = new ViewItem(listenerLG, mainView);
			this.add(view);
		}

		hvervning.addActionListener(new OrdreActionListener(
				new HvervningsOrdre(listenerLG.getId()), mainView));
		this.add(hvervning);

		JMenuItem skoling = new JMenuItem("skoling");
		skoling.addActionListener(new OrdreActionListener(new SkolingOrdre(
				listenerLG.getId()), mainView));
		this.add(skoling);
		this.show(e.getComponent(), e.getX(), e.getY());
	}
}

class ViewItem extends JMenuItem {
	public ViewItem(final Lokalgruppe lg, final MainView mainView) {
		super("Detaljer");
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainView.medlemView.show(lg);
			}
		});
	}
}
