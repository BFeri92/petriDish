package hu.unideb.inf.prt.petriDish;

import hu.unideb.inf.prt.petriDish.Genotype.GenomSizeNotMatchException;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WorldDescriptor {

	static private Logger logger = LoggerFactory
			.getLogger(WorldDescriptor.class);

	@XmlElementWrapper(name = "genotypes")
	@XmlElement(name = "genotype")
	private List<Genotype> genotypes;
	@XmlElement
	private int fitness;
	@XmlElement
	private GameConfiguration conf;

	public WorldDescriptor() {

	}

	public WorldDescriptor(GameConfiguration conf) {
		genotypes = new Vector<Genotype>(conf.getAgentCount());
		fitness = 0;
		this.conf = conf;
		for (int i = 0; i < conf.getAgentCount(); i++) {
			genotypes.add(new Genotype(conf.getNodesPerHiddenLayer(), conf
					.getHiddenLayers()));
		}
	}

	public WorldDescriptor(List<Genotype> genBase, GameConfiguration conf)
			throws GenomSizeNotMatchException {
		genotypes = new Vector<Genotype>(conf.getAgentCount());
		Random rnd = new Random();
		this.conf = conf;
		fitness = 0;

		for (int i = 0; i < conf.getAgentCount(); i++) {
			if (conf.getRecombine()) {
				Genotype mother = genBase.get(rnd.nextInt(genBase.size()));
				Genotype father = genBase.get(rnd.nextInt(genBase.size()));
				try {
					genotypes.add(new Genotype(mother, father));
				} catch (GenomSizeNotMatchException e) {
					logger.error("Genotypes with different number of genes or hidden layers were used as the generation base");
					throw e;
				}
			} else {
				Genotype mother = genBase.get(rnd.nextInt(genBase.size()));
				genotypes.add(new Genotype(mother));
			}
			if (conf.getMutate())
				genotypes.get(genotypes.size() - 1).mutate(
						conf.getMutationProb(), conf.getMutationAmount());
		}
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int f) {
		fitness = f;
	}

	public List<Genotype> getGenotypes() {
		return Collections.unmodifiableList(genotypes);
	}

	public GameConfiguration configuration() {
		return conf;
	}

}
