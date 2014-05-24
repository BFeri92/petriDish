package hu.unideb.inf.prt.petriDish;

public class Entity {
	protected float xPos;
	protected float yPos;

	public static final int radius = 10;
	public static final int radiusSquared = radius * radius;

	public Entity() {

	}

	public Entity(float x, float y) {
		xPos = x;
		yPos = y;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public double distanceSquared(Entity other) {
		return Math.pow((this.xPos - other.xPos), 2)
				+ Math.pow((this.yPos - other.yPos), 2);
	}

	public boolean colides(Entity other) {
		if (distanceSquared(other) < 2 * radiusSquared)
			return true;
		else
			return false; // we use 50 as radius
	}
}
