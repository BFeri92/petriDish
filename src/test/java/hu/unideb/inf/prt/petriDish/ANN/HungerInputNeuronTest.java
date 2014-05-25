package hu.unideb.inf.prt.petriDish.ANN;

import hu.unideb.inf.prt.petriDish.Agent;
import hu.unideb.inf.prt.petriDish.Genotype;
import hu.unideb.inf.prt.petriDish.ANN.FeedForwardNeuron.WeigthNumberNotMatchException;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Test for {@link HungerInputNeuron}.
 * @author Ferenc Barta
 *
 */
public class HungerInputNeuronTest {

	/**
	 * Tests if initial hunger is 0.
	 */
	@Test
	public void testGetValue() {
		Genotype g = new Genotype(1, 1);
		Agent a;
		try {
			a = new Agent(g, 0, 0, 0);
			HungerInputNeuron neuron = new HungerInputNeuron(a);
			assertEquals("New agent should have 0 hunger.", 0.0, neuron.getValue(), 0); 
		} catch (WeigthNumberNotMatchException e) {
			fail("Error while creating owner agent.");
		};
	}

}
