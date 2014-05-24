package hu.unideb.inf.prt.petriDish.ANN;

import java.util.List;
import java.util.Vector;

public class FeedForwardNeuron implements Neuron {

	private double stored;

	public class WeigthNumberNotMatchException extends Exception {
		private static final long serialVersionUID = 1L;

		public WeigthNumberNotMatchException() {
		}

		public WeigthNumberNotMatchException(String message) {
			super(message);
		}
	}

	private Layer previous;
	List<Double> weigths;

	public FeedForwardNeuron(Layer previousLayer, List<Double> weigths)
			throws WeigthNumberNotMatchException {
		if (weigths.size() != previousLayer.getNeuronCount())
			throw new WeigthNumberNotMatchException();
		this.weigths = new Vector<Double>(weigths.size());
		this.weigths.addAll(weigths);
		previous = previousLayer;
	}

	private double logSigmoid(double x) {
		return 1.0 / (1.0 + Math.exp(-x));
	}

	public void preCalc() {
		double weightedInput = 0;
		List<Neuron> connected = previous.getNeurons();
		for (int i = 0; i < previous.getNeuronCount(); i++) {
			weightedInput += connected.get(i).getValue() * weigths.get(i);
		}
		stored = logSigmoid(weightedInput);
	}

	public double getValue() {
		return stored;
	}

}
