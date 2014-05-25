package hu.unideb.inf.prt.petriDish;
/**
 * Main class of the program.
 * @author Ferenc Barta
 *
 */
public class Main {
	/**
	 * Main method of the program.
	 * @param args command line arguments.
	 */
	public static void main(String[] args)
	{
		//First call of Game.getInstance() initialize the UI, from here, 
		//UI will control the program flow.
		Game g = Game.getInstance();
	}
}
