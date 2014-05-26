package hu.unideb.inf.prt.petriDish.ANN;

/**
 * An interface for classes representing a neuron in a neural network.
 * 
 * @author Ferenc Barta
 *
 */
public interface Neuron {
	/**
	 * Can be used to calculate and store the value.
	 * In contrast with the {@link #getValue()} method, this method runs only once 
	 * in each {@link ANN#run()} call, hence it can be used to calculate the neuron
	 * value, store it, then return the stored value when {@link #getValue()}
	 * is called. 
	 */
	public void preCalc();

	/**
	 * Should return the value of the neuron.
	 * @return the value of the neuron.
	 */
	public double getValue();
}
