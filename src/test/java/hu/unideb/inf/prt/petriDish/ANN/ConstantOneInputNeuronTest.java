package hu.unideb.inf.prt.petriDish.ANN;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Test for {@link ConstantOneInputNeuron}.
 * @author Ferenc Barta
 *
 */
public class ConstantOneInputNeuronTest {

	/**
	 * Tests if the neuron actually returns one.
	 */
	@Test
	public void test() {
		ConstantOneInputNeuron neuron = new ConstantOneInputNeuron();
		assertEquals(1.0, neuron.getValue(), 0);
	}

}
