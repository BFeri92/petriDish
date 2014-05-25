package hu.unideb.inf.prt.petriDish.ANN;

import hu.unideb.inf.prt.petriDish.Agent;
import hu.unideb.inf.prt.petriDish.Genotype;
import hu.unideb.inf.prt.petriDish.ANN.FeedForwardNeuron.WeigthNumberNotMatchException;

import java.util.List;
import java.util.Vector;
/**A representation of an artificial neural network.
 * 
 * The implementation contains three input nodes 
 * (ConstantOneInputNeuron, HungerInputNeuron, EyeInputNeuron,
 * and two output nodes. For output and hidden nodes, 
 * FeedForwardNeurons were used. 
 * @author Ferenc Barta
 *
 */
public class ANN {
	/**
	 * The list containing the layers of the network.
	 */
	private List<Layer> layers;
	/**
	 * The agent that will use the neural network.
	 * 
	 * Required to correctly evaluate EyeInputNeuron and
	 * HungerInputNeuron.
	 */
	private Agent owner;
	/**
	 * The number of the neurons in the input layer.
	 */
	public static final int inputNeuronCount = 3;
	/**
	 * The number of the neurons in the output layer.
	 */
	public static final int outputNeuronCount = 2;
	/**Constructor to create a neural network.
	 * 
	 * @param owner the agent that will use the neural network.
	 * @param g the neural network will be based on this genotype.
	 * @throws WeigthNumberNotMatchException Thrown when the number of genes in the genotype not match the required. 
	 */
	public ANN(Agent owner, Genotype g) throws WeigthNumberNotMatchException {
		layers = new Vector<Layer>(g.getHiddenLayerCount());
		this.owner = owner;
		// Input layer
		Layer inp = new Layer();
		// Input neurons
		inp.insertNeuron(new ConstantOneInputNeuron());
		inp.insertNeuron(new HungerInputNeuron(this.owner));
		inp.insertNeuron(new EyeInputNeuron(this.owner));
		layers.add(inp);
		Layer prevLayer = inp;
		List<Double> genom = g.getGenes();
		// hidden layers
		int currGene = 0;
		for (int i = 0; i < g.getHiddenLayerCount(); i++) {
			Layer hidden = new Layer();
			for (int n = 0; n < g.getGenesPerHiddenLayer(); n++) {
				List<Double> weigths = new Vector<Double>(
						prevLayer.getNeuronCount());
				for (int j = 0; j < prevLayer.getNeuronCount(); j++) {
					weigths.add(genom.get(currGene));
					currGene++;
				}
				hidden.insertNeuron(new FeedForwardNeuron(prevLayer, weigths));
				layers.add(hidden);
			}
			prevLayer = hidden;
		}
		// Finally, the output layer
		Layer outp = new Layer();
		for (int n = 0; n < ANN.outputNeuronCount; n++) {
			List<Double> weigths = new Vector<Double>(
					prevLayer.getNeuronCount());
			for (int j = 0; j < prevLayer.getNeuronCount(); j++) {
				weigths.add(genom.get(currGene));
				currGene++;
			}
			outp.insertNeuron(new FeedForwardNeuron(prevLayer, weigths));
		}
		layers.add(outp);
	}

	/**Evaluates the neural network and return with the result.
	 * 
	 * @return A list containing the values of the output neurons.
	 */
	public List<Double> run() {
		List<Double> res = new Vector<Double>(ANN.outputNeuronCount);
		for (Layer l : layers) {
			for (Neuron n : l.getNeurons())
				n.preCalc();
		}
		for (Neuron o : layers.get(layers.size() - 1).getNeurons()) {
			res.add(o.getValue());
		}
		return res;
	}
}
