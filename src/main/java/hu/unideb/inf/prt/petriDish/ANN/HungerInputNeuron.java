package hu.unideb.inf.prt.petriDish.ANN;

import hu.unideb.inf.prt.petriDish.Agent;

public class HungerInputNeuron implements Neuron {

	private Agent owner;
	
	public HungerInputNeuron(Agent owner)
	{
		this.owner=owner;
	}
	
	public double getValue() {
		return owner.getHunger();
	}

}
