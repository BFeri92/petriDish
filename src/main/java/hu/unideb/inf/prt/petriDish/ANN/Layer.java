package hu.unideb.inf.prt.petriDish.ANN;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
/**
 * Class to represent a layer in a neural network.
 * 
 * @author Ferenc Barta
 *
 */
public class Layer {
	/**
	 * A list of the contained neurons.
	 */
	protected List<Neuron> neurons;

	/**
	 * Constructor, creates an empty layer.
	 */
	public Layer() {
		neurons = new Vector<Neuron>();
	}

	/**
	 * Returns the number of neurons in the layer.
	 * @return the number of neurons in the layer.
	 */
	public int getNeuronCount() {
		return neurons.size();
	}

	/**
	 * Returns a list of the contained neurons.
	 * @return The list of the neurons wrapped into an unmodifiableList.
	 */
	public List<Neuron> getNeurons() {
		return Collections.unmodifiableList(neurons);
	}

	/**
	 * Inserts a neuron into the layer.
	 * @param n the neuron to be inserted.
	 */
	public void insertNeuron(Neuron n) {
		neurons.add(n);
	}

}
