package hu.unideb.inf.prt.petriDish.ANN;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Layer {
	protected List<Neuron> neurons;

	public Layer() {
		neurons = new Vector<Neuron>();
	}

	public int getNeuronCount() {
		return neurons.size();
	}

	public List<Neuron> getNeurons() {
		return Collections.unmodifiableList(neurons);
	}

	public void insertNeuron(Neuron n) {
		neurons.add(n);
	}

}
