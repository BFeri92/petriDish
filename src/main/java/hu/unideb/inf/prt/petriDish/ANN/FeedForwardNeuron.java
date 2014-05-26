package hu.unideb.inf.prt.petriDish.ANN;

import java.util.List;
import java.util.Vector;
/**Neuron to use in hidden and output layers.
 * The value of the neuron depends on the values of the previous layer.
 * 
 * @author Ferenc Barta
 *
 */
public class FeedForwardNeuron implements Neuron {
	/**
	 * Stored value calculated by {@link preCalc()} method.
	 */
	private double stored;
	/**Exception raised when the number of the neurons in the previous layer
	 * and the number of the given weights not match.
	 * 
	 * @author Ferenc Barta
	 *
	 */
	public class WeigthNumberNotMatchException extends Exception {

		/**
		 * Constructor for the exception.
		 */
		public WeigthNumberNotMatchException() {
		}
		/**
		 * Constructor for the exception.
		 * @param message Message to be sent with the exception.
		 */
		public WeigthNumberNotMatchException(String message) {
			super(message);
		}
	}
	/**
	 * The previous layer of neuorons.
	 */
	private Layer previous;
	/**
	 * The weights that should be applied when calculating the value.
	 */
	List<Double> weigths;

	/**
	 * Constructor.
	 * 
	 * @param previousLayer The previous layer of neurons, which will be used as the input of this neuron
	 * @param weigths A list of the weights that will be applied for the inputs.
	 * @throws WeigthNumberNotMatchException Thrown when the number of the weights and the number of the inputs - the number of the neurons in the previous layer - differs.
	 */
	public FeedForwardNeuron(Layer previousLayer, List<Double> weigths)
			throws WeigthNumberNotMatchException {
		if (weigths.size() != previousLayer.getNeuronCount())
			throw new WeigthNumberNotMatchException();
		this.weigths = new Vector<Double>(weigths.size());
		this.weigths.addAll(weigths);
		previous = previousLayer;
	}

	/**
	 * The function to be used as the output function.
	 * In our implementation, we use the following function:
	 * {@code
	 * 1.0 / (1.0 + Math.exp(-x))
	 * }
	 * which gives a sigmoid curve.
	 * @param x function parameter.
	 * @return function value.
	 */
	private double logSigmoid(double x) {
		return 1.0 / (1.0 + Math.exp(-x));
	}

	/**
	 * Calculates and stores the value of the neuron.
	 * Required to call before calling {@link #getValue()}.<br/>
	 * As it uses the getValue() method on the neurons
	 * in the previous layer, those neurons should be
	 * evaluated before this method is called.
	 */
	public void preCalc() {
		double weightedInput = 0;
		List<Neuron> connected = previous.getNeurons();
		for (int i = 0; i < previous.getNeuronCount(); i++) {
			weightedInput += connected.get(i).getValue() * weigths.get(i);
		}
		stored = logSigmoid(weightedInput);
	}

	/**
	 * Returns the value of the neuron.
	 * Evaluating with {@link #preCalc()} method before calling this 
	 * method is needed to return the correct value.
	 * @return Returns the value given by {@code logSigmoid(i)} where i is the weighted sum of the input values.
	 */
	public double getValue() {
		return stored;
	}

}
