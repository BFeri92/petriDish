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

/**
 * JAXB annotation for XML root element.
 */
@XmlRootElement
/**
 * World descriptor can be used to store or create a game world.
 * World descriptor contains the genotypes of the agents 
 * that should be found in the world and the world description. 
 * @author Ferenc Barta
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WorldDescriptor {

	/**
	 * Logger.
	 */
	static private Logger logger = LoggerFactory
			.getLogger(WorldDescriptor.class);

	/**
	 * JAXB element wrapper annotation.
	 */
	@XmlElementWrapper(name = "genotypes")
	/**
	 * The genotype of the agents in the world.
	 */
	@XmlElement(name = "genotype")
	private List<Genotype> genotypes;
	/**
	 * If a game world created from the world descriptor,
	 * fitness is set to the worlds age when all agents
	 * in the world died.
	 */
	@XmlElement
	private long fitness;
	/**
	 * Ruleset of the world to create.
	 */
	@XmlElement
	private GameConfiguration conf;

	/**
	 * Constructor.
	 * Does nothing, only needed for JAXB loaders.
	 */
	public WorldDescriptor() {

	}

	/**
	 * Create world descriptor from game configuration with randomly generated genotypes.
	 * @param conf the game configuration
	 */
	public WorldDescriptor(GameConfiguration conf) {
		genotypes = new Vector<Genotype>(conf.getAgentCount());
		fitness = 0;
		this.conf = conf;
		for (int i = 0; i < conf.getAgentCount(); i++) {
			genotypes.add(new Genotype(conf.getNodesPerHiddenLayer(), conf
					.getHiddenLayers()));
		}
	}

	/**
	 * Create world descriptor from game configuration with the genotypes based on a given list of genotypes.
	 * New genotypes are created by recombining or copying random genotypes from the given list, 
	 * then mutation may happen, according to the game configuration.
	 * @param genBase The genotypes to use 
	 * @param conf The game configuration to use
	 * @throws GenomSizeNotMatchException Thrown when the the parents gene count differs. 
	 */
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

	/**
	 * Return the fitness of the world.
	 * @return the fitness of the world.
	 */
	public long getFitness() {
		return fitness;
	}

	/**
	 * Sets the fitness of the world.
	 * @param f the fitness of the world.
	 */
	public void setFitness(long f) {
		fitness = f;
	}

	/**
	 * Returns an unmodifiable list of genotypes.
	 * @return an unmodifiable list of genotypes.
	 */
	public List<Genotype> getGenotypes() {
		return Collections.unmodifiableList(genotypes);
	}

	/**
	 * Returns the game configuration.
	 * @return the game configuration.
	 */
	public GameConfiguration configuration() {
		return conf;
	}

}
