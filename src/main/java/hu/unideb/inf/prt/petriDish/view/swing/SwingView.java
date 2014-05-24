package hu.unideb.inf.prt.petriDish.view.swing;

import javax.swing.JOptionPane;

import hu.unideb.inf.prt.petriDish.Game;
import hu.unideb.inf.prt.petriDish.view.View;

public class SwingView implements View {
	private MainWindow window;

	public SwingView() {
		window = new MainWindow();
		window.setVisible(true);
	}

	public void render() {
		window.useEntityList(Game.getInstance().getEntities());
		window.fillWorldInformation(Game.getInstance().getGeneration(), Game
				.getInstance().getWorldAge(), Game.getInstance()
				.getAgentsAlive());
	}

	public void error(String messg) {
		JOptionPane.showMessageDialog(null, messg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void close() {
		window.dispose();
	}

}
