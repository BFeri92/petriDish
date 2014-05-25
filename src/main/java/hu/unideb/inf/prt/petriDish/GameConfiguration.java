package hu.unideb.inf.prt.petriDish;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model to represent a simulation configuration.
 * @author Ferenc Barta
 *
 */
@XmlRootElement
public class GameConfiguration {

	/**
	 * The length of a side of the square-shaped game world.
	 */
	private int worldSize;
	/**
	 * The number of food entities in the time of world creation.
	 */
	private int initialFoodAmount;
	/**
	 * Probability of a food entity to double itself.
	 */
	private double foodAmountIncrese;
	/**
	 * The number of agents in the time of world creation.
	 */
	private int agentCount;

	/**
	 * The number of hidden layers in an agents neural network.
	 */
	private int hiddenLayers;
	/**
	 * The number of neurons in the hidden layer of an agents neural
	 * network. All of these layers have the same number of neurons.
	 */
	private int nodesPerHiddenLayer;
	
	/**
	 * The number of agents which take part in the creation of the next
	 * generation of agents.
	 */
	private int winnerCount;
	/**
	 * Specify if the new agents should be created by combining the genotype of
	 * winner agents. If recombination is disabled, the agents can only evolve
	 * by mutation.
	 */
	private boolean recombine;
	/**
	 * Specify if mutation should happen when creating new population of agents.
	 */
	private boolean mutate;
	/**
	 * The probability of the occurrence of mutation on each gene copy.
	 */
	private double mutationProb;
	/**
	 * The variance of mutation. If mutation occurs, it modify the number
	 * describing the gene as G'=G+norm(0, variance), where norm(m,v) returns a
	 * random number from Gaussian distribution with m mean and v variance.
	 */
	private double mutationAmount;

	/**
	 * Returns the length of a side of the square-shaped game world.
	 * 
	 * @return The world size
	 */
	@XmlElement(required = true, nillable = false)
	public int getWorldSize() {
		return worldSize;
	}

	/**
	 * Sets the length of a side of the square-shaped game world.
	 * 
	 * @param worldSize the size of the world
	 */
	public void setWorldSize(int worldSize) {
		this.worldSize = worldSize;
	}

	/**
	 * Returns the number of food entities in the time of world creation.
	 * 
	 * @return Initial food amount
	 */
	@XmlElement(required = true, nillable = false)
	public int getInitialFoodAmount() {
		return initialFoodAmount;
	}

	/**
	 * Sets the number of food in the time of world creation time.
	 * 
	 * @param initialFoodAmount The initial food amount
	 */
	public void setInitialFoodAmount(int initialFoodAmount) {
		this.initialFoodAmount = initialFoodAmount;
	}

	/**
	 * Returns probability of a food entity to double itself.
	 * 
	 * @return The probability of a food entity to double itself.
	 */
	@XmlElement(required = true, nillable = false)
	public double getFoodAmountIncrese() {
		return foodAmountIncrese;
	}

	/**
	 * Sets probability of a food entity to double itself.
	 * 
	 * @param foodAmountIncrese The probability of a food entity to double itself.
	 */
	public void setFoodAmountIncrese(double foodAmountIncrese) {
		this.foodAmountIncrese = foodAmountIncrese;
	}

	/**
	 * Returns the agent count in the time of world creation.
	 * 
	 * @return the agent count in the time of world creation.
	 */
	@XmlElement(required = true, nillable = false)
	public int getAgentCount() {
		return agentCount;
	}

	/**
	 * Sets the agent count in the time of world creation.
	 * 
	 * @param agentCount the agent count in the time of world creation.
	 */
	public void setAgentCount(int agentCount) {
		this.agentCount = agentCount;
	}

	/**
	 * Returns the number of hidden layers in an agents neural network.
	 * 
	 * @return the number of hidden layers in an agents neural network.
	 */
	@XmlElement(required = true, nillable = false)
	public int getHiddenLayers() {
		return hiddenLayers;
	}

	/**
	 * Sets the number of hidden layers in an agents neural network.
	 * 
	 * @param hiddenLayers the number of hidden layers in an agents neural network.
	 */
	public void setHiddenLayers(int hiddenLayers) {
		this.hiddenLayers = hiddenLayers;
	}

	/**
	 * Returns the number of neurons in the hidden layer of an agents neural
	 * network. All of these layers have the same number of neurons.
	 * 
	 * @return the number of neurons in the hidden layer of an agents neural
	 * network.
	 */
	@XmlElement(required = true, nillable = false)
	public int getNodesPerHiddenLayer() {
		return nodesPerHiddenLayer;
	}

	/**
	 * Sets the number of neurons in the hidden layer of an agents neural
	 * network. All of these layers have the same number of neurons.
	 * 
	 * @param nodesPerHiddenLayer the number of neurons in the hidden layer of an agents neural
	 * network.
	 */
	public void setNodesPerHiddenLayer(int nodesPerHiddenLayer) {
		this.nodesPerHiddenLayer = nodesPerHiddenLayer;
	}

	/**
	 * Returns the number of agents which take part in the creation of the next
	 * generation of agents.
	 * 
	 * @return the number of agents which take part in the creation of the next
	 * generation of agents.
	 */
	@XmlElement(required = true, nillable = false)
	public int getWinnerCount() {
		return winnerCount;
	}

	/**
	 * Sets the number of agents which take part in the creation of the next
	 * generation of agents.
	 * 
	 * @param winnerCount the number of agents which take part in the creation of the next
	 * generation of agents.
	 */
	public void setWinnerCount(int winnerCount) {
		this.winnerCount = winnerCount;
	}

	/**
	 * Returns if the new agents should be created by combining the genotype of
	 * winner agents. If recombination is disabled, the agents can only evolve
	 * by mutation.
	 * 
	 * @return True, if the new agents should be created by combining the genotype of
	 * winner agents, false otherwise.
	 */
	@XmlElement(required = true, nillable = false)
	public boolean getRecombine() {
		return recombine;
	}

	/**
	 * Sets if the new agents should be created by combining the genotype of
	 * winner agents. If recombination is disabled, the agents can only evolve
	 * by mutation.
	 * 
	 * @param recombine Sets if the new agents should be created by combining the genotype of
	 * winner agents
	 */
	public void setRecombine(boolean recombine) {
		this.recombine = recombine;
	}

	/**
	 * Returns if mutation should happen when creating new population of agents.
	 * 
	 * @return true, if mutation should happen when creating new population of agents.
	 */
	@XmlElement(required = true, nillable = false)
	public boolean getMutate() {
		return mutate;
	}

	/**
	 * Sets if mutation should happen when creating new population of agents.
	 * 
	 * @param mutate Specify if mutation should happen
	 */
	public void setMutate(boolean mutate) {
		this.mutate = mutate;
	}

	/**
	 * Returns the probability of the occurrence of mutation on each gene copy.
	 * 
	 * @return the probability of the occurrence of mutation on each gene copy.
	 */
	@XmlElement(required = true, nillable = false)
	public double getMutationProb() {
		return mutationProb;
	}

	/**
	 * Sets the probability of the occurrence of mutation on each gene copy.
	 * 
	 * @param mutationProb the probability of the occurrence of mutation
	 */
	public void setMutationProb(double mutationProb) {
		this.mutationProb = mutationProb;
	}

	/**
	 * Returns the variance of mutation. If mutation occurs, it modify the
	 * number describing the gene as G'=G+norm(0, variance), where norm(m,v)
	 * returns a random number from Gaussian distribution with m mean and v
	 * variance.
	 * 
	 * @return the variance of mutation.
	 */
	@XmlElement(required = true, nillable = false)
	public double getMutationAmount() {
		return mutationAmount;
	}

	/**
	 * Sets the variance of mutation. If mutation occurs, it modify the number
	 * describing the gene as G'=G+norm(0, variance), where norm(m,v) returns a
	 * random number from Gaussian distribution with m mean and v variance.
	 * 
	 * @param mutationAmount the variance of mutation.
	 */
	public void setMutationAmount(double mutationAmount) {
		this.mutationAmount = mutationAmount;
	}

}
