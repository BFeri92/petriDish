package hu.unideb.inf.prt.petriDish.view.swing;

import hu.unideb.inf.prt.petriDish.Agent;
import hu.unideb.inf.prt.petriDish.Entity;
import hu.unideb.inf.prt.petriDish.FoodEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PDDisplayPanel extends JPanel {
	private int xPos = 0;
	private int yPos = 0;
	private List<Entity> entityList = null;

	public PDDisplayPanel() {
		this.setIgnoreRepaint(true);
		this.setFocusable(true);
	}

	public void moveDown() {
		yPos -= 20;
		repaint();
	}

	public void moveUp() {
		yPos += 20;
		repaint();
	}

	public void moveLeft() {
		xPos -= 20;
		repaint();
	}

	public void moveRight() {
		xPos += 20;
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D graph = (Graphics2D) g;
		graph.setBackground(new Color(1, 0, 0));
		graph.clearRect(0, 0, this.getWidth(), this.getHeight());
		if (entityList != null) {
			for (Entity e : entityList) {
				int eCenterX = Math.round(e.getxPos()) - xPos;
				int eCenterY = Math.round(e.getyPos()) - yPos;
				if (e.getClass().equals(FoodEntity.class)) {
					graph.setColor(new Color(255, 255, 0));
					graph.fillOval(eCenterX - e.radius, eCenterY - e.radius,
							2 * e.radius, 2 * e.radius);
				} else if (e.getClass().equals(Agent.class)) {
					double rad = Math.toRadians(((Agent) e).getHeading());
					double hunger = ((Agent) e).getHunger();
					if (hunger > 1)
						hunger = 1;
					graph.setColor(new Color((int) Math.round(250 * hunger),
							(int) Math.round(250 * (1 - hunger)), (int) 0));
					graph.fillOval(eCenterX - e.radius, eCenterY - e.radius,
							2 * e.radius, 2 * e.radius);
					graph.setColor(new Color(255, 255, 255));
					graph.drawLine(
							eCenterX,
							eCenterY,
							(int) Math.round(eCenterX + e.radius
									* Math.cos(rad)),
							(int) Math.round(eCenterY + e.radius
									* Math.sin(rad)));
				}
			}
		}
	}

	public void useEntityList(List<Entity> l) {
		this.entityList = l;
		this.repaint();
	}

}
