package hu.unideb.inf.prt.petriDish;

import hu.unideb.inf.prt.petriDish.ANN.ANN;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Genotypes are used to describe neural networks.
 * Each gene represents a weight in the neural network.
 * @author Ferenc Barta
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Genotype {
	/**
	 * Exception thrown when number of parents genes not match.
	 * @author Ferenc Barta
	 *
	 */
	public class GenomSizeNotMatchException extends Exception {

		/**
		 * Constructor.
		 */
		public GenomSizeNotMatchException() {
		}

		/**
		 * Constructor.
		 * @param message Message to be sent with the exception.
		 */
		public GenomSizeNotMatchException(String message) {
			super(message);
		}
	}

	/**
	 * Gaussian function with mean = 0.
	 * @param x function parameter
	 * @param var variance
	 * @return the function value with the given parameters
	 */
	private static double G(double x, double var) {
		return Math.exp(-Math.pow(x, 2) / 2 * Math.pow(var, 2));
	}

	/**
	 * Returns a random number from the Gaussian distribution.
	 * @param var variance
	 * @param rng uniform random number generator
	 * @return a random number from the Gaussian distribution
	 */
	private static double randGaussian(double var, Random rng) {
		double res;
		double y;
		do {
			res = rng.nextDouble() * (2 * 6 * var) - (2 * 3 * var);
			y = rng.nextDouble();
		} while (G(res, var) > y);
		return res;
	}

	/**
	 * The generation of the genotype.
	 */
	@XmlElement(required = true, nillable = false)
	private int generation;
	/**
	 * List of genes.
	 */
	@XmlElementWrapper(name = "genom")
	@XmlElement(required = true, nillable = false, name = "gene")
	private List<Double> genes;
	/**
	 * The number of the hidden layers.
	 */
	@XmlElement(required = true, nillable = false)
	private int hiddenLayersCount;
	/**
	 * The number of genes per hidden layer.
	 */
	@XmlElement(required = true, nillable = false)
	private int genesPerHiddenLayer;

	/**
	 * Returns the generation.
	 * @return the generation
	 */
	public int getGeneration() {
		return generation;
	}

	/**
	 * Returns an unmodifiable list of genes.
	 * @return an unmodifiable list of genes.
	 */
	public List<Double> getGenes() {
		return Collections.unmodifiableList(genes);
	}

	/**
	 * Returns the number of hidden layers.
	 * @return the number of hidden layers.
	 */
	public int getHiddenLayerCount() {
		return hiddenLayersCount;
	}

	/**
	 * Return the number of genes per hidden layer.
	 * @return the number of genes per hidden layer
	 */
	public int getGenesPerHiddenLayer() {
		return genesPerHiddenLayer;
	}

	/**
	 * Constructor.
	 * Won't do anything, only used by JAXB loader.
	 */
	public Genotype() {

	}

	/**
	 * Constructor, generates a random genom with the given parameters.
	 * @param hiddenGeneCount The number of genes in each hidden layer.
	 * @param hiddenLayerCount The number of hidden layers.
	 */
	public Genotype(int hiddenGeneCount, int hiddenLayerCount) {
		int geneCount = ANN.inputNeuronCount * hiddenGeneCount
				+ (hiddenLayerCount - 1) * hiddenGeneCount * hiddenGeneCount
				+ ANN.outputNeuronCount * hiddenGeneCount;
		genesPerHiddenLayer = hiddenGeneCount;
		genes = new Vector<Double>(geneCount);
		Random rnd = new Random();
		for (int i = 0; i < geneCount; i++)
			genes.add(rnd.nextDouble() * 8 - 4);
		this.hiddenLayersCount = hiddenLayerCount;
		this.generation = 0;
	}

	/**
	 * Constructor, copies the parents genom.
	 * The generation will increase with one.
	 * @param mother the base of the genom.
	 */
	public Genotype(Genotype mother) {
		genesPerHiddenLayer = mother.getGenesPerHiddenLayer();
		genes = new Vector<Double>();
		for (int i = 0; i < mother.genes.size(); i++)
			genes.add(mother.genes.get(i));
		hiddenLayersCount = mother.hiddenLayersCount;
		this.generation = mother.generation + 1;
	}

	/**
	 * Constructor, each gene will be copied from one of the parent randomly.
	 * The generation will increase with one.
	 * @param mother The first base of the genom
	 * @param father The second base of the genom
	 * @throws GenomSizeNotMatchException Thrown when parents gene count does not match.
	 */
	public Genotype(Genotype mother, Genotype father)
			throws GenomSizeNotMatchException {
		if (mother.genes.size() != father.genes.size()
				|| mother.hiddenLayersCount != father.hiddenLayersCount)
			throw new GenomSizeNotMatchException();
		Random rnd = new Random();
		genesPerHiddenLayer = mother.getGenesPerHiddenLayer();
		genes = new Vector<Double>(mother.genes.size());
		generation = Math.max(mother.generation, father.generation) + 1;
		hiddenLayersCount = mother.hiddenLayersCount;
		for (int i = 0; i < mother.genes.size(); i++) {
			if (rnd.nextBoolean())
				genes.add(mother.genes.get(i));
			else
				genes.add(father.genes.get(i));
		}
	}

	/**
	 * Constructor to manually set parameters.
	 * @param generation generation
	 * @param hiddenLayersCount the number of hidden layers
	 * @param genesPerHiddenLayer the number of genes in each hidden layer
	 * @param genes the genom.
	 */
	public Genotype(int generation, int hiddenLayersCount,
			int genesPerHiddenLayer, List<Double> genes) {
		this.genesPerHiddenLayer = genesPerHiddenLayer;
		this.generation = generation;
		this.hiddenLayersCount = hiddenLayersCount;
		this.genes = new Vector<Double>(genes);
	}

	/**
	 * Mutate each gene. 
	 * Changes happen with the given probability and the amount of 
	 * change will be a Gaussian random number with the given variance 
	 * @param prob the probability of change to happen
	 * @param variance the variance of the Gaussian distribution from which the delta will be sampled.
	 */
	public void mutate(double prob, double variance) {
		Random rnd = new Random();
		for (int i = 0; i < genes.size(); i++) {
			if (rnd.nextDouble() < prob) {
				double orig = genes.get(i);
				orig += randGaussian(variance, rnd);
				genes.set(i, orig);
			}
		}
	}

	/**
	 * Compares if obj equals the genotype.
	 * @param obj the object to compare to
	 * @return true if obj is a Genotype, both obj and the
	 * instance have the same generation, number of hidden layers,
	 * number of genes in hidden layers, and genes. Otherwise it
	 * returns false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!obj.getClass().equals(Genotype.class))
			return false;
		Genotype other = (Genotype) obj;
		if (this.generation != other.generation)
			return false;
		if (this.hiddenLayersCount != other.hiddenLayersCount)
			return false;
		// if (this.genes.equals(other.genes)) return false;
		if (this.genes.size() != other.genes.size())
			return false;
		for (int i = 0; i < this.genes.size(); i++) {
			if (Math.abs(this.genes.get(i) - other.genes.get(i)) > 0.000000000000001)
				return false;
		}
		return true;
	}
}
