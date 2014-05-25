package hu.unideb.inf.prt.petriDish.ANN;
/**Input neuron whose value is always 1.
 * 
 * @author Ferenc Barta
 *
 */
public class ConstantOneInputNeuron implements Neuron {
	/**
	 * Returns the value of the neuron, which is always 1.
	 * @return The value of the neuron
	 */
	public double getValue() {
		return 1;
	}

	/**
	 * Calling preCalc() before getValue() is not needed here.
	 */
	public void preCalc() {
	}

}
