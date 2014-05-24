package hu.unideb.inf.prt.petriDish.loaders;

import hu.unideb.inf.prt.petriDish.GameConfiguration;

/**
 * Interface for loading/saving game configuration.
 * 
 * @author Ferenc Barta
 * 
 */
public interface ConfigLoader {
	/**
	 * Loads a game configuration
	 * 
	 * @return The loaded configuration
	 */
	GameConfiguration load();

	/**
	 * Saves a game configuration.
	 * 
	 * @param conf
	 *            The configuration to be saved
	 * @return True if save was successful, false otherwise
	 */
	boolean save(GameConfiguration conf);
}
