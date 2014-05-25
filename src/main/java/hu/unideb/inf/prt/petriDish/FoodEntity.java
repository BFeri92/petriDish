package hu.unideb.inf.prt.petriDish;
/**
 * Class to represent food entities in the game world.
 * 
 * @author Ferenc Barta
 *
 */
public class FoodEntity extends Entity {
	/**
	 * Constructor with initial position setup.
	 * @param x initial x coordinate
	 * @param y initial y coordinate
	 */
	public FoodEntity(float x, float y) {
		super(x, y);
	}

	/**
	 * Returns the amount of hunger decrease, if an agent eats the entity.
	 * @return the amount of hunger decrease, if an agent eats the entity.
	 */
	public double getSustenance() {
		return 0.4;
	}
}
