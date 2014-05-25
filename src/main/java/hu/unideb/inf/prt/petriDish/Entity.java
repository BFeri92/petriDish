package hu.unideb.inf.prt.petriDish;

/**
 * Class to represent an entity in the game world.
 * @author Ferenc Barta
 *
 */
public class Entity {
	/**
	 * The x coordinate of the entities position in the game world.
	 */
	protected float xPos;
	/**
	 * The y coordinate of the entities position in the game world.
	 */	
	protected float yPos;
	/**
	 * The radius of the visual representation of entities.
	 */
	public static final int radius = 10;
	/**
	 * The square of radius.
	 */
	public static final int radiusSquared = radius * radius;

	/**
	 * Constructor.
	 */
	public Entity() {

	}

	/**
	 * Constructor with initial position setup.
	 * @param x initial x coordinate
	 * @param y initial y coordinate
	 */
	public Entity(float x, float y) {
		xPos = x;
		yPos = y;
	}

	/**
	 * Returns the entities positions x coordinate.
	 * @return x coordinate
	 */
	public float getxPos() {
		return xPos;
	}

	/**
	 * Sets the entities positions x coordinate.
	 * @param xPos x position
	 */
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	/**
	 * Returns the entities positions y coordinate.
	 * @return y coordinate
	 */
	public float getyPos() {
		return yPos;
	}
	
	/**
	 * Sets the entities positions y coordinate.
	 * @param yPos y position
	 */
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	/**
	 * Returns the squared distance of this and the entity given in the parameter.
	 * @param other The other entity
	 * @return the squared distance of this and the entity given in the parameter
	 */
	public double distanceSquared(Entity other) {
		return Math.pow((this.xPos - other.xPos), 2)
				+ Math.pow((this.yPos - other.yPos), 2);
	}

	/**
	 * Returns if two entities collides.
	 * @param other The other entity
	 * @return True if the entities collides, false otherwise
	 */
	public boolean collides(Entity other) {
		if (distanceSquared(other) < 2 * radiusSquared)
			return true;
		else
			return false; // we use 50 as radius
	}
}
