package hu.unideb.inf.prt.petriDish;

import hu.unideb.inf.prt.petriDish.ANN.ANN;
import hu.unideb.inf.prt.petriDish.ANN.FeedForwardNeuron.WeigthNumberNotMatchException;

import java.util.List;

/**
 * Represents a neural network driven entity.
 * 
 * @author Ferenc Barta
 *
 */
public class Agent extends Entity {
	/**
	 * The neural network of the agent.
	 */
	protected ANN network;
	/**
	 * The hunger of the agent. It's range is between 0 and 1.
	 */
	protected double hunger;
	/**
	 * The heading of the agent in degree.
	 */
	protected double heading;
	/**
	 * The amount of forward movement to be applied with the {@link apply()} method.
	 */
	private double movement;
	/**
	 * The amount of heading change to be applied with the {@link apply()} method.
	 */
	private double headingDelta;
	/**
	 * The genotype which was given as the base of the neural network.
	 */
	private Genotype genotype;
	/**
	 * The maximum range of the agent in which it can see.
	 */
	public static final double visualRange = 500;
	/**
	 * The square of the visualRange.
	 */
	public static final double visualRangeSquared = visualRange
			* visualRange;
	/**
	 * Create an agent with given genotype, position and heading.
	 * 
	 * @param g Genotype describing the neural network
	 * @param xPos Initial X coordinate
	 * @param yPos Initial Y coordinate
	 * @param heading Initial heading
	 * @throws WeigthNumberNotMatchException Thrown when malformed genotype is given. See also {@link ANN#ANN(Agent, Genotype)}.
	 */
	public Agent(Genotype g, int xPos, int yPos, int heading)
			throws WeigthNumberNotMatchException {
		this.genotype = g;
		this.hunger = 0;
		network = new ANN(this, g);
		this.xPos = xPos;
		this.yPos = yPos;
		this.heading = heading;
	}
	
	/**
	 * Returns the genotype of the agent.
	 * @return the genotype of the agent.
	 */

	public Genotype getGenotype() {
		return genotype;
	}

	/**
	 * Evaluates the neural network and prepare the movement based
	 * on its value.
	 */
	public void exec() {
		List<Double> annOut = network.run();
		if (annOut.get(0) > .5 && annOut.get(1) <= .5) {
			headingDelta = 5;
			movement = 0;
			hunger += .0009;
		} else if (annOut.get(0) > .5 && annOut.get(1) > .5) {
			headingDelta = 0;
			movement = 3;
			hunger += .0009;
		} else if (annOut.get(0) <= .5 && annOut.get(1) > .5) {
			headingDelta = -5;
			movement = 0;
			hunger += .0009;
		} else {
			headingDelta = 0;
			movement = 0;
			hunger += .0009;
		}
	}

	/**
	 * Returns the agents heading.
	 * @return heading
	 */
	public double getHeading() {
		return heading;
	}
	
	/**
	 * Sets the agents heading.
	 * @param heading heading.
	 */
	public void setHeading(double heading) {
		if (heading >= 360)
			heading -= 360;
		if (heading < 0)
			heading += 360;
		this.heading = heading;
	}

	/**
	 * Returns the agents hunger.
	 * @return the agents hunger
	 */
	public double getHunger() {
		return hunger;
	}

	/**
	 * Sets the agents hunger.
	 * @param hung hunger
	 */
	public void setHunger(double hung) {
		if (hung < 0)
			hunger = 0;
		else if (hung > 1)
			hunger = 1;
		else
			hunger = hung;
	}

	/**
	 * Applies the position and heading change.
	 * The output of the neural network determine the movement, thus 
	 * {@link exec()} method must be called before. 
	 */
	public void apply() {
		heading += headingDelta;
		if (heading >= 360)
			heading -= 360;
		if (heading < 0)
			heading += 360;
		xPos += Math.cos(Math.toRadians(heading)) * movement;
		yPos += Math.sin(Math.toRadians(heading)) * movement;
	}

	/**
	 * Check if the agent sees the given entity.
	 * An entity is visible to the agent if: <br/>
	 * <ol>
	 * <li>the circle
	 * representing the entity and the line that falls upon
	 * the (x,y), (x+cos(heading),y+sin(heading)) points have
	 * at least common point, </li>
	 * <li>The distance of the agent and the entity
	 * is at most visualRange and</li>
	 * <li>The angle of the vector that crosses both the
	 * agent and the entity and the vector described by
	 * (x,y), ((x+cos(heading),y+sin(heading)) points
	 * is at most 90 degree or at most 270 degree.</li>
	 * </ol>
	 * @param other The entity to examine
	 * @return Returns true if the entity given is visible for the agent, false otherwise.
	 */
	public boolean sees(Entity other) {
		if (distanceSquared(other) > visualRangeSquared)
			return false; // only check in circle with r=200

		double p1X = this.xPos - other.xPos;
		double p1Y = this.yPos - other.yPos;
		double p2X = this.xPos - other.xPos + Math.cos(Math.toRadians(heading))
				* 10;
		double p2Y = this.yPos - other.yPos + Math.sin(Math.toRadians(heading))
				* 10;
		double a = p1Y - p2Y;
		double b = (p2X - p1X);
		double c = (p2X * p1Y - p1X * p2Y);
		double discriminant = radiusSquared * (Math.pow(a, 2) + Math.pow(b, 2))
				- Math.pow(c, 2);
		if (discriminant < 0)
			return false;

		double v1X = other.xPos - this.xPos;
		double v1Y = other.yPos - this.yPos;
		// normalize
		double v1Length = Math.sqrt(v1X * v1X + v1Y * v1Y);
		v1X /= v1Length;
		v1Y /= v1Length;
		double v2X = Math.cos(Math.toRadians(heading));
		double v2Y = Math.sin(Math.toRadians(heading));
		double dotProduct = v1X * v2X + v1Y * v2Y;
		if (dotProduct < 0)
			return false;
		return true;
	}
}
