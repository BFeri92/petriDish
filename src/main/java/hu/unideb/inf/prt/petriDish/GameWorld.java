package hu.unideb.inf.prt.petriDish;

import hu.unideb.inf.prt.petriDish.ANN.FeedForwardNeuron.WeigthNumberNotMatchException;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameWorld {

	private List<Entity> entities;
	private List<Entity> dead;
	GameConfiguration conf;
	private long stepCount;

	public GameWorld(WorldDescriptor descr)
			throws WeigthNumberNotMatchException {
		stepCount = 0;
		conf = descr.configuration();
		entities = Collections.synchronizedList(new LinkedList<Entity>());
		dead = Collections.synchronizedList(new LinkedList<Entity>());
		Random rng = new Random();
		// adding agents
		for (int i = 0; i < conf.getAgentCount(); i++) {
			entities.add(new Agent(descr.getGenotypes().get(
					rng.nextInt(descr.getGenotypes().size())), rng.nextInt(conf
					.getWorldSize() + 1), rng.nextInt(conf.getWorldSize() + 1),
					rng.nextInt(360)));
		}
		// adding food
		for (int i = 0; i < conf.getInitialFoodAmount(); i++) {
			entities.add(new FoodEntity(rng.nextInt(conf.getWorldSize() + 1),
					rng.nextInt(conf.getWorldSize() + 1)));
		}
	}

	public long getStepCount() {
		return stepCount;
	}

	public GameConfiguration getConfiguration() {
		return conf;
	}

	public void step() {
		stepCount++;
		int foodCount = 0;
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext()) {
			Entity curr = it.next();
			if (curr.getClass().equals(Agent.class)) {
				Agent a = (Agent) curr;
				a.exec();
				a.apply();
				if (a.getHunger() >= 1) {
					it.remove();
					dead.add(curr);
				}
				/*
				 * if (a.getxPos()<0)
				 * a.setxPos(Math.round(conf.getWorldSize()+a.getxPos())); if
				 * (a.getxPos()>conf.getWorldSize())
				 * a.setxPos(Math.round(a.getxPos()-conf.getWorldSize()));
				 * 
				 * if (a.getyPos()<0)
				 * a.setyPos(Math.round(conf.getWorldSize()+a.getyPos())); if
				 * (a.getyPos()>conf.getWorldSize())
				 * a.setyPos(Math.round(a.getyPos()-conf.getWorldSize()));
				 */
				if (a.getxPos() < 0)
					a.setxPos(0);
				if (a.getxPos() > conf.getWorldSize())
					a.setxPos(conf.getWorldSize());

				if (a.getyPos() < 0)
					a.setyPos(0);
				if (a.getyPos() > conf.getWorldSize())
					a.setyPos(conf.getWorldSize());
			} else if (curr.getClass().equals(FoodEntity.class))
				foodCount++;
		}

		for (int i = 0; i < entities.size(); i++) {
			for (int j = 0; j < entities.size(); j++) {
				Entity e1 = entities.get(i);
				Entity e2 = entities.get(j);
				if (j != i && e1.getClass().equals(Agent.class)
						&& e2.getClass().equals(FoodEntity.class)
						&& e1.colides(e2)) {
					((Agent) e1).setHunger(((Agent) e1).getHunger()
							- ((FoodEntity) e2).getSustenance());
					entities.remove(j);
					j--;
				}
			}
		}
		if (foodCount < 50) {
			double foodIncrProb = getConfiguration().getFoodAmountIncrese();
			Random rng = new Random();
			for (; foodCount > 0; foodCount--) {
				if (foodIncrProb > rng.nextDouble()) {
					entities.add(new FoodEntity(
							rng.nextInt(conf.getWorldSize() + 1), rng
									.nextInt(conf.getWorldSize() + 1)));
				}
			}
		}
	}

	public List<Entity> getEntities() {
		return Collections.unmodifiableList(entities);
	}

	public List<Entity> getDead() {
		return Collections.unmodifiableList(dead);
	}
}
