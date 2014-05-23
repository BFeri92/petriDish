package hu.unideb.inf.prt.petriDish.ANN;

import hu.unideb.inf.prt.petriDish.ANN.FeedForwardNeuron.WeigthNumberNotMatchException;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class FeedForwardNeuronTest {

	@Test
	public void testGetValue() {
		Layer l = new Layer();
		List<Double> weights = new ArrayList<Double>();
		l.insertNeuron(new ConstantOneInputNeuron());
		l.insertNeuron(new ConstantOneInputNeuron());
		weights.add(1.0);
		weights.add(1.0);
		try {
			FeedForwardNeuron neuron = new FeedForwardNeuron(l, weights);
			assertEquals(neuron.getValue(), 1.0/(1.0+Math.exp(-2.0)), 1e-15 );
			
		} catch (WeigthNumberNotMatchException e) {
			fail("Previous layers neuron count and the number of the weights does not match");
		}
	}

}
