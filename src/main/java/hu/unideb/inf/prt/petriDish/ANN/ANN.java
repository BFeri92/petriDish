package hu.unideb.inf.prt.petriDish.ANN;

import hu.unideb.inf.prt.petriDish.Agent;
import hu.unideb.inf.prt.petriDish.Constants;
import hu.unideb.inf.prt.petriDish.Genotype;
import hu.unideb.inf.prt.petriDish.ANN.FeedForwardNeuron.WeigthNumberNotMatchException;

import java.util.List;
import java.util.Vector;

public class ANN {
	List<Layer> layers;
	Agent owner;

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
		for (int n = 0; n<Constants.outputNeuronCount ;n++)
		{
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
	
	public List<Double> run()
	{
		List<Double> res = new Vector<Double>(Constants.outputNeuronCount);
		for (Neuron o : layers.get(layers.size()-1).getNeurons())
		{
			res.add(o.getValue());
		}
		return res;
	}
}
