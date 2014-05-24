package hu.unideb.inf.prt.petriDish;

import hu.unideb.inf.prt.petriDish.ANN.FeedForwardNeuron.WeigthNumberNotMatchException;

import static org.junit.Assert.*;
import org.junit.Test;

public class GameWorldTest {

	@Test
	public void test() {
		GameConfiguration conf = new GameConfiguration();
		conf.setAgentCount(1);
		conf.setInitialFoodAmount(1);
		conf.setFoodAmountIncrese(1);
		conf.setHiddenLayers(1);
		conf.setNodesPerHiddenLayer(1);
		conf.setWorldSize(1000);
		WorldDescriptor wd = new WorldDescriptor(conf);
		try {
			Entity first;
			Entity second;
			do {
				GameWorld world = new GameWorld(wd);
				assertEquals(0, world.getStepCount());
				assertEquals(2, world.getEntities().size());
				assertEquals(0, world.getDead().size());
				first = world.getEntities().get(0);
				second = world.getEntities().get(1);
				if (first.colides(second)) continue;
				world.step();
				assertEquals(1, world.getStepCount());
				assertEquals(3, world.getEntities().size());
				assertEquals(0, world.getDead().size());
				} while (first.colides(second));
		} catch (WeigthNumberNotMatchException e) {
			fail("Gene count not match in configuration and world descriptors genoms.");
		}
	}

}
