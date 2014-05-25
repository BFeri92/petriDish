package hu.unideb.inf.prt.petriDish.ANN;

import hu.unideb.inf.prt.petriDish.Agent;
import hu.unideb.inf.prt.petriDish.Entity;
import hu.unideb.inf.prt.petriDish.FoodEntity;
import hu.unideb.inf.prt.petriDish.Game;

import java.util.List;
/**
 * Input neuron, whose value depends on the closest food entity that can be seen by its owner.
 * @author Ferenc Barta
 *
 */
public class EyeInputNeuron implements Neuron {
	/**
	 * The agent that uses this neuron.
	 */
	private Agent owner;
	/**
	 * Stored value from {@link preCalc()}.
	 */
	private double stored;

	/**
	 * Constructor.
	 * @param owner	The agent that will use the neuron.
	 */
	public EyeInputNeuron(Agent owner) {
		this.owner = owner;
	}

	/**
	 * Calculates and stores the value of the neuron.
	 * Required to call before calling {@link getValue()}.
	 */
	public void preCalc() {
		List<Entity> entities = Game.getInstance().getEntities();
		double minDst = Agent.visualRange + 1;
		for (Entity e : entities) {
			if (e.getClass().equals(FoodEntity.class) && owner.sees(e)) {
				double distance = Math.sqrt(owner.distanceSquared(e));
				if (minDst > distance)
					minDst = distance;
			}
		}
		if (minDst < Agent.visualRange + 1) {
			stored = minDst / Agent.visualRange;
			return;
		}
		stored = 0;
		return;
	}

	/**
	 * Returns the value of the neuron.
	 * Evaluating with {@link preCalc()} method before calling this 
	 * method is needed to return the correct value.
	 * @see hu.unideb.inf.prt.petriDish.Agent#sees(Entity)
	 * @return The returned value will between 0 and 1 and will be linear to 
	 * the distance of the owner and the closest food entity that is 
	 * visible for the owner.
	 */
	public double getValue() {
		return stored;
	}

}
