package hu.unideb.inf.prt.petriDish.ANN;

import hu.unideb.inf.prt.petriDish.Agent;

/**
 * Input neuron, whose value is its owners hunger.
 * @author Ferenc Barta
 *
 */

public class HungerInputNeuron implements Neuron {
	/**
	 * The agent that uses this neuron.
	 */
	private Agent owner;

	/**
	 * Constructor.
	 * @param owner	The agent that will use the neuron.
	 */
	public HungerInputNeuron(Agent owner) {
		this.owner = owner;
	}
	
	/**
	 * Calling preCalc() before getValue() is not needed here.
	 */
	public void preCalc() {
	}
	/**
	 * Returns the value of the neuron, which is the owners hunger.
	 * @see hu.unideb.inf.prt.petriDish.Agent#getHunger()
	 * @return The value of the neuron
	 */
	public double getValue() {
		return owner.getHunger();
	}

}
