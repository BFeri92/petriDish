package hu.unideb.inf.prt.petriDish;

import hu.unideb.inf.prt.petriDish.loaders.ConfigLoader;
import hu.unideb.inf.prt.petriDish.loaders.JAXBConfigLoader;
import hu.unideb.inf.prt.petriDish.loaders.JAXBWDLoader;
import hu.unideb.inf.prt.petriDish.loaders.WDLoader;
import hu.unideb.inf.prt.petriDish.view.View;
import hu.unideb.inf.prt.petriDish.view.swing.SwingView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton that holds the game-related objects and give high level
 * services to control the game flow. 
 * @author Ferenc Barta
 *
 */
public class Game {
	/**
	 * The user interface.
	 */
	private View ui;
	/**
	 * The game controller.
	 */
	private Simulation sim = null;
	/**
	 * The only instance of the class created and returned by {@link getInstance()}.
	 */
	static private Game instance = null;
	/**
	 * Logger.
	 */
	static private Logger logger = LoggerFactory.getLogger(Game.class);

	/**
	 * Constructor.
	 * Should only be called by {@link getInstance()}
	 */
	private Game() {
		logger.info("Creating user interface");
		ui = new SwingView();
	}

	/**
	 * Returns the instance of the game.
	 * If it doesn't exists, it will create it.
	 * The user interface will be also constructed on the first call.
	 * @return the instance of Game.
	 */
	static public Game getInstance() {
		if (instance == null) {
			instance = new Game();
			logger.info("Game instance created.");
		}
		return instance;
	}

	/**
	 * Returns the user interface.
	 * @return the user interface.
	 */
	public View getUI() {
		return ui;
	}

	/**
	 * Pauses the simulation by suspending the thread, if exists.
	 * @see Simulation#pause()
	 */
	public void pauseSimulation() {
		if (sim != null)
			sim.pause();
	}

	/**
	 * If a simulation is loaded, this method will start 
	 * - in case it was not paused - or resume the thread
	 * execution.
	 * @see Simulation#unPause()
	 */
	public void startSimulation() {
		if (sim == null)
			return;

		if (sim.getState() == Thread.State.NEW) {
			sim.start();
		} else if (sim.getState() == Thread.State.WAITING) {
			sim.unPause();
		}
	}

	/**
	 * Tries to load the given world descriptor and
	 * create a new simulation.
	 * @param fname The path to the world descriptor
	 */
	public void loadWorldDescriptor(String fname) {
		WDLoader loader = new JAXBWDLoader(fname);
		WorldDescriptor descr = loader.load();
		if (descr != null) {
			long dly = 100;
			if (sim != null) {
				dly = sim.getDelay();
				sim.stopSimulation();
			}
			sim = new Simulation(descr, dly);
		} else
			ui.error("Could not load world descriptor file.");
	}

	/**
	 * Tries to load the given game configuration and
	 * create a new simulation.
	 * @param fname The path to the game configuration
	 */
	public void loadGameConfiguration(String fname) {
		ConfigLoader loader = new JAXBConfigLoader(fname);
		GameConfiguration conf = loader.load();
		long dly = 100;
		if (conf != null) {
			if (sim != null) {
				dly = sim.getDelay();
				sim.stopSimulation();
			}
			sim = new Simulation(new WorldDescriptor(conf), dly);
		} else
			ui.error("Could not load game configuration file.");
	}

	/**
	 * If simulation is created, this method saves all the world 
	 * descriptors from the simulation into a tar archive.
	 * @param fname The path of the tar archive in which the world descriptors should be saved.
	 */
	public void saveSimulation(String fname) {
		if (sim != null) {
			try {
				Path td = Files.createTempDirectory("PDTemp_");
				List<WorldDescriptor> worldsToSave = sim.getWDStack();
				for (int i = 0; i < worldsToSave.size(); i++) {
					WDLoader loader = new JAXBWDLoader(td + "/"
							+ Integer.toString(i) + ".xml");
					loader.save(worldsToSave.get(i));
				}

				/* Output Stream - that will hold the physical TAR file */
				OutputStream tar_output = new FileOutputStream(new File(fname));
				/*
				 * Create Archive Output Stream that attaches File Output Stream
				 * / and specifies TAR as type of compression
				 */
				ArchiveOutputStream my_tar_ball = new ArchiveStreamFactory()
						.createArchiveOutputStream(ArchiveStreamFactory.TAR,
								tar_output);
				/* Create Archieve entry - write header information */
				for (int i = 0; i < worldsToSave.size(); i++) {
					File tar_input_file = new File(td + "/"
							+ Integer.toString(i) + ".xml");
					TarArchiveEntry tar_file = new TarArchiveEntry(
							tar_input_file, Integer.toString(i) + ".xml");
					tar_file.setSize(tar_input_file.length());
					my_tar_ball.putArchiveEntry(tar_file);
					IOUtils.copy(new FileInputStream(tar_input_file),
							my_tar_ball);
					my_tar_ball.closeArchiveEntry();
				}
				my_tar_ball.finish();
				tar_output.close();
			} catch (IOException e) {
				logger.error("IO error while saving simulation.");
				ui.error("Exception occured while saving simulation.");
			} catch (ArchiveException e) {
				logger.error("Error while creating TAR archive");
				ui.error("Exception occured while saving simulation.");
			}
		}

	}

	/**
	 * Tries to load the default game configuration 
	 * from the resources and create a new simulation.
	 */
	public void loadDefaultConfiguration() {
		InputStream resource = Game.class
				.getResourceAsStream("/default.cfg.xml");
		ConfigLoader loader = new JAXBConfigLoader(resource);
		GameConfiguration conf = loader.load();
		long dly = 100;
		if (conf != null) {
			if (sim != null) {
				dly = sim.getDelay();
				sim.stopSimulation();
			}
			this.stopSimulation();
			sim = new Simulation(new WorldDescriptor(conf), dly);
		} else
			ui.error("Could not load default game configuration file.");

	}

	/**
	 * Set simulation delay, making the simulation speed change.
	 * @see Simulation#setDelay(long)
	 * @param dly the new delay
	 */
	public void setDelay(long dly) {
		if (sim != null)
			sim.setDelay(dly);
	}

	/**
	 * Stops the simulation thread.
	 */
	public void stopSimulation() {
		if (sim != null) {
			if (sim.getState() == Thread.State.WAITING) {
				sim.unPause();
			}
			sim.stopSimulation();
		}
	}

	/**
	 * If simulation exists, this method returns a synchronized, unmodifiable list of entities in simulations game world.
	 * @return Returns list of entities or if the simulation not constructed, an empty array
	 */
	public List<Entity> getEntities() {
		if (sim != null)
			return Collections.synchronizedList(Collections.unmodifiableList(sim.getWorld().getEntities()));
		else
			return new ArrayList<Entity>();
	}

	/**
	 * Returns the number of worlds created in the simulation.
	 * @return the number of worlds created in the simulation.
	 */
	public int getGeneration() {
		if (sim != null) {
			return sim.getGeneration();
		} else
			return 0;
	}

	/**
	 * Returns the number of steps made with the current world.
	 * @return the number of steps made with the current world.
	 */
	public long getWorldAge() {
		if (sim != null) {
			return sim.getWorldAge();
		} else
			return 0;
	}

	/**
	 * Returns the number of living agents in the current world.
	 * @return the number of living agents in the current world.
	 */
	public int getAgentsAlive() {
		if (sim != null) {
			return sim.getWorld().getConfiguration().getAgentCount()
					- sim.getWorld().getDead().size();
		} else
			return 0;
	}

	/**
	 * Returns if there is a simulation constructed.
	 * @return if there is a simulation constructed.
	 */
	public boolean hasSimulationLoaded() {
		if (sim == null)
			return false;
		return true;
	}

}
