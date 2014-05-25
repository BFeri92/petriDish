package hu.unideb.inf.prt.petriDish.loaders;

import hu.unideb.inf.prt.petriDish.WorldDescriptor;

/**
 * Interface for loading/saving world descriptors.
 * @author Ferenc Barta
 *
 */

public interface WDLoader {
	/**
	 * Loads a world descriptor.
	 * 
	 * @return The loaded world descriptor
	 */
	WorldDescriptor load();

	/**
	 * Saves a world descriptor.
	 * 
	 * @param conf
	 *            The descriptor to be be saved
	 * @return True if save was successful, false otherwise
	 */
	boolean save(WorldDescriptor conf);
}
