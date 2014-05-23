package hu.unideb.inf.prt.petriDish.ANN;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConstantOneInputNeuronTest {

	@Test
	public void test() {
		ConstantOneInputNeuron neuron = new ConstantOneInputNeuron();
		assertEquals(1.0, neuron.getValue(), 0);
	}

}
