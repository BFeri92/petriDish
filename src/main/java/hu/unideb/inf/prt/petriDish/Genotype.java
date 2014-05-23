package hu.unideb.inf.prt.petriDish;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class Genotype {
	public class GenomSizeNotMatchException extends Exception 
	{
		private static final long serialVersionUID = 1L;
		public GenomSizeNotMatchException() {}
	    public GenomSizeNotMatchException(String message)
	    {
	       super(message);
	    }
	}
	
	private static double G(double x, double var)
	{
		return Math.exp(-Math.pow(x,2) / 2*Math.pow(var,2));
	}
	private static double randGaussian(double var, Random rng)
	{
		double res;
		double y;
		do 
		{
			res=rng.nextDouble()*(2*6*var) - (2*3*var);
			y=rng.nextDouble();
		}
		while (G(res, var)>y);
		return res;	
	}
	
	@XmlElement(required=true, nillable=false)
	private int generation;
	@XmlElementWrapper(name="genom")
	@XmlElement(required=true, nillable=false, name="gene")
	private List<Double> genes;
	@XmlElement(required=true, nillable=false)
	private int hiddenLayersCount;
	@XmlElement(required=true, nillable=false)
	private int genesPerHiddenLayer;
	
	public int getGeneration()
	{
		return generation;
	}
	
	public List<Double> getGenes()
	{
		return Collections.unmodifiableList(genes);
	}
	
	public int getHiddenLayerCount()
	{
		return hiddenLayersCount;
	}
	
	public int getGenesPerHiddenLayer()
	{
		return genesPerHiddenLayer;
	}
	
	public Genotype()
	{
		
	}
	
	public Genotype(int hiddenGeneCount, int hiddenLayerCount)
	{
		int geneCount =	Constants.inputNeuronCount*hiddenGeneCount + 
						(hiddenLayerCount-1)*hiddenGeneCount*hiddenGeneCount +
						Constants.outputNeuronCount*hiddenGeneCount;
		genesPerHiddenLayer = hiddenGeneCount;
		genes=new Vector<Double>(geneCount);
		Random rnd = new Random();
		for (int i=0; i<geneCount; i++)
			genes.add(rnd.nextDouble()*8-4);
		this.hiddenLayersCount = hiddenLayerCount;
		this.generation=0;
	}
	
	public Genotype(Genotype mother)
	{
		genesPerHiddenLayer = mother.getGenesPerHiddenLayer();
		genes = new Vector<Double>();
		for (int i=0; i<mother.genes.size(); i++)
			genes.add(mother.genes.get(i));
		hiddenLayersCount = mother.hiddenLayersCount;
		this.generation=mother.generation+1;
	}
	
	public Genotype(Genotype mother, Genotype father) throws GenomSizeNotMatchException
	{
		if (mother.genes.size()!=father.genes.size() || 
				mother.hiddenLayersCount != father.hiddenLayersCount)
				throw new GenomSizeNotMatchException();
		Random rnd = new Random();
		genesPerHiddenLayer = mother.getGenesPerHiddenLayer();
		genes = new Vector<Double> (mother.genes.size());
		generation = Math.max(mother.generation, father.generation)+1;
		hiddenLayersCount = mother.hiddenLayersCount;
		for (int i=0; i<mother.genes.size(); i++)
		{
			if (rnd.nextBoolean()) genes.add(mother.genes.get(i));
				else genes.add(father.genes.get(i));
		}
	}
	
	public Genotype(int generation, int hiddenLayersCount, int genesPerHiddenLayer, List<Double> genes)
	{
		this.genesPerHiddenLayer = genesPerHiddenLayer;
		this.generation = generation;
		this.hiddenLayersCount = hiddenLayersCount;
		this.genes = new Vector<Double> (genes);
	}
	
	public void mutate(double prob, double variance)
	{
		Random rnd = new Random();
		for (int i=0; i<genes.size(); i++)
		{
			if (rnd.nextDouble()<prob)
			{
				double orig = genes.get(i);
				orig+=randGaussian(variance, rnd);
				genes.set(i, orig);
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!obj.getClass().equals(Genotype.class)) return false;
		Genotype other = (Genotype) obj;
		if (this.generation != other.generation) return false;
		if (this.hiddenLayersCount != other.hiddenLayersCount) return false;
		//if (this.genes.equals(other.genes)) return false;
		if (this.genes.size()!=other.genes.size()) return false;
		for (int i=0; i<this.genes.size(); i++)
		{
			if (Math.abs(this.genes.get(i)-other.genes.get(i))>0.000000000000001) return false;
		}
		return true;
	}
}
