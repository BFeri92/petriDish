package hu.unideb.inf.prt.petriDish.ANN;

import hu.unideb.inf.prt.petriDish.Agent;
import hu.unideb.inf.prt.petriDish.Entity;
import hu.unideb.inf.prt.petriDish.FoodEntity;
import hu.unideb.inf.prt.petriDish.Game;

import java.util.List;

public class EyeInputNeuron implements Neuron {

	private Agent owner;
	
	public EyeInputNeuron(Agent owner)
	{
		this.owner=owner;
	}
	
	public double getValue() {
		List<Entity> entities = Game.getInstance().getEntities();
		double minDst=Agent.viewDistanceSquared+1;
		for (Entity e : entities)
		{
			if (e.getClass().equals(FoodEntity.class) && owner.sees(e))
			{
				if (minDst>owner.distanceSquared(e)) 
					minDst=owner.distanceSquared(e);
			}
		}
		if (minDst<Agent.viewDistanceSquared+1)
		{
			return minDst/Agent.viewDistanceSquared;
		}
		return 0;
	}

}
