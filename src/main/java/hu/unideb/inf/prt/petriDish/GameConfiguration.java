package hu.unideb.inf.prt.petriDish;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GameConfiguration {

	private int worldSize;
	private int initialFoodAmount;
	private double foodAmountIncrese;
	private int agentCount;

	private int hiddenLayers;
	private int nodesPerHiddenLayer;

	private int winnerCount;
	private boolean recombine;
	private boolean mutate;
	private double mutationProb;
	private double mutationAmount;

	/**
	 * Returns the length of a side of the square-shaped game world.
	 * 
	 * @return world size
	 */
	@XmlElement(required = true, nillable = false)
	public int getWorldSize() {
		return worldSize;
	}

	/**
	 * Sets the length of a side of the square-shaped game world.
	 * 
	 * @param world
	 *            size
	 */
	public void setWorldSize(int worldSize) {
		this.worldSize = worldSize;
	}

	/**
	 * Returns the number of food entities in the time of world creation.
	 * 
	 * @param initial
	 *            food amount
	 */
	@XmlElement(required = true, nillable = false)
	public int getInitialFoodAmount() {
		return initialFoodAmount;
	}

	/**
	 * Sets the number of food in the time of world creation time.
	 * 
	 * @param initial
	 *            food amount
	 */
	public void setInitialFoodAmount(int initialFoodAmount) {
		this.initialFoodAmount = initialFoodAmount;
	}

	/**
	 * Returns probability of a food entity to double itself.
	 * 
	 * @return foodAmountIcrese
	 */
	@XmlElement(required = true, nillable = false)
	public double getFoodAmountIncrese() {
		return foodAmountIncrese;
	}

	/**
	 * Sets probability of a food entity to double itself.
	 * 
	 * @param foodAmountIncrese
	 */
	public void setFoodAmountIncrese(double foodAmountIncrese) {
		this.foodAmountIncrese = foodAmountIncrese;
	}

	/**
	 * Returns the agent count in the time of world creation.
	 * 
	 * @return
	 */
	@XmlElement(required = true, nillable = false)
	public int getAgentCount() {
		return agentCount;
	}

	/**
	 * Sets the agent count in the time of world creation.
	 * 
	 * @param agentCount
	 */
	public void setAgentCount(int agentCount) {
		this.agentCount = agentCount;
	}

	/**
	 * Returns the number of hidden layers in an agents neural network.
	 * 
	 * @return
	 */
	@XmlElement(required = true, nillable = false)
	public int getHiddenLayers() {
		return hiddenLayers;
	}

	/**
	 * Sets the number of hidden layers in an agents neural network.
	 * 
	 * @param hiddenLayers
	 */
	public void setHiddenLayers(int hiddenLayers) {
		this.hiddenLayers = hiddenLayers;
	}

	/**
	 * Returns the number of neurons in the hidden layer of an agents neural
	 * network. All of these layers have the same number of neurons.
	 * 
	 * @return
	 */
	@XmlElement(required = true, nillable = false)
	public int getNodesPerHiddenLayer() {
		return nodesPerHiddenLayer;
	}

	/**
	 * Sets the number of neurons in the hidden layer of an agents neural
	 * network. All of these layers have the same number of neurons.
	 * 
	 * @param nodesPerHiddenLayer
	 */
	public void setNodesPerHiddenLayer(int nodesPerHiddenLayer) {
		this.nodesPerHiddenLayer = nodesPerHiddenLayer;
	}

	/**
	 * Returns the number of agents which take part in the creation of the next
	 * generation of agents.
	 * 
	 * @return
	 */
	@XmlElement(required = true, nillable = false)
	public int getWinnerCount() {
		return winnerCount;
	}

	/**
	 * Sets the number of agents which take part in the creation of the next
	 * generation of agents.
	 * 
	 * @param winnerCount
	 */
	public void setWinnerCount(int winnerCount) {
		this.winnerCount = winnerCount;
	}

	/**
	 * Returns if the new agents should be created by combining the genotype of
	 * winner agents. If recombination is disabled, the agents can only evolve
	 * by mutation.
	 * 
	 * @return
	 */
	@XmlElement(required = true, nillable = false)
	public boolean getRecombine() {
		return recombine;
	}

	/**
	 * Returns if the new agents should be created by combining the genotype of
	 * winner agents. If recombination is disabled, the agents can only evolve
	 * by mutation.
	 * 
	 * @param recombine
	 */
	public void setRecombine(boolean recombine) {
		this.recombine = recombine;
	}

	/**
	 * Returns if mutation should happen when creating new population of agents.
	 * 
	 * @return
	 */
	@XmlElement(required = true, nillable = false)
	public boolean getMutate() {
		return mutate;
	}

	/**
	 * Sets if mutation should happen when creating new population of agents.
	 * 
	 * @param mutate
	 */
	public void setMutate(boolean mutate) {
		this.mutate = mutate;
	}

	/**
	 * Returns the probability of the occurrence of mutation on each gene copy.
	 * 
	 * @return
	 */
	@XmlElement(required = true, nillable = false)
	public double getMutationProb() {
		return mutationProb;
	}

	/**
	 * Sets the probability of the occurrence of mutation on each gene copy.
	 * 
	 * @param mutationProb
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
	 * @return
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
	 * @param mutationAmount
	 */
	public void setMutationAmount(double mutationAmount) {
		this.mutationAmount = mutationAmount;
	}

}
