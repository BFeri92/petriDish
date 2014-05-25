package hu.unideb.inf.prt.petriDish;

import hu.unideb.inf.prt.petriDish.Genotype.GenomSizeNotMatchException;
import hu.unideb.inf.prt.petriDish.ANN.FeedForwardNeuron.WeigthNumberNotMatchException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The controller of the game.
 * @author Ferenc Barta
 *
 */
public class Simulation extends Thread {
	/**
	 * Logger.
	 */
	static private Logger logger = LoggerFactory.getLogger(Simulation.class);
	/**
	 * A list containing all world descriptors of the simulations lifetime.
	 */
	private List<WorldDescriptor> WDStack;
	/**
	 * The current game world.
	 */
	private GameWorld world;
	/**
	 * The number of created worlds.
	 */
	private int generation;
	/**Simulation delay.
	 * Time to wait between every world step in milliseconds.
	 */
	private long delay;
	/**
	 * Describes if the game should be paused.
	 */
	private boolean pause = false;

	/**
	 * Returns the simulation delay.
	 * @return the current simulation delay.
	 */
	public long getDelay() {
		return delay;
	}

	/**
	 * Sets the simulation delay.
	 * @param delay the delay to be used in milliseconds.
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}
	/**
	 * Describes whether the simulation should be running.
	 */
	private boolean running;

	/**
	 * Creates simulation with the given world description and simulation delay.
	 * @param descr the world descriptor to be used to create the game world.
	 * @param stepDelay the delay to be used as simulation delay in milliseconds.
	 */
	Simulation(WorldDescriptor descr, long stepDelay) {
		generation = 0;
		WDStack = new Vector<WorldDescriptor>();
		logger.info("Creating new simulation");
		WDStack.add(descr);
		delay = stepDelay;
		try {
			logger.info("Creating game world");
			world = new GameWorld(descr);
		} catch (WeigthNumberNotMatchException e) {
			logger.error("Error while loading world descriptor: world descriptor file is possibliy corrupted");
			logger.info("Number of genes in the container not match with the one described in the world configuration");
			Game.getInstance().getUI().error("Invalid world descriptor.");
		}
	}

	/**
	 * The simulation loop.
	 * Step the game world, create new if all the agents are dead, asks
	 * the user interface to render.
	 */
	@Override
	public void run() {
		GameConfiguration conf = WDStack.get(WDStack.size() - 1)
				.configuration();
		if (!running) {
			running = true;
			while (running) {
				try {
					if (pause) {
						synchronized (this) {
							wait();
						}
					}
					world.step();
					if (world.getDead().size() == conf.getAgentCount()) {
						List<Genotype> parents = new ArrayList<Genotype>(
								conf.getWinnerCount());
						generation++;
						for (int i = 0; i < conf.getWinnerCount(); i++) {
							Agent a = (Agent) world.getDead().get(
									world.getDead().size() - (1 + i));
							parents.add(a.getGenotype());
						}
						WorldDescriptor braveNewWorld = new WorldDescriptor(
								parents, conf);
						WDStack.get(WDStack.size()-1).setFitness(this.getWorldAge());
						WDStack.add(braveNewWorld);
						world = new GameWorld(braveNewWorld);
					}
					// if ((System.currentTimeMillis()-lastRendered)>100.0/6.0)
					{
						Game.getInstance().getUI().render();
					}
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (GenomSizeNotMatchException e) {
					logger.error("Genom size not match at simulation.");
					logger.warn("There exception shouldn't be thrown, because we use the current world to generate a new one.");
				} catch (WeigthNumberNotMatchException e) {
					logger.error("Genom size not match at simulation.");
					logger.warn("There exception shouldn't be thrown, because we use the current world to generate a new one.");					
				}
			}
		}
	}

	/**
	 * Returns the number of the worlds created.
	 * @return the number of the worlds created.
	 */
	public int getGeneration() {
		return generation;
	}

	/**
	 * Returns the number of steps made with the current world.
	 * @return the number of steps made with the current world.
	 */
	public long getWorldAge() {
		if (world != null)
			return world.getStepCount();
		return 0;
	}

	/**
	 * Make the thread exit the simulation loop.
	 */
	public void stopSimulation() {
		running = false;
	}

	/**
	 * Returns the actual game world.
	 * @return the actual game world.
	 */
	public GameWorld getWorld() {
		return world;
	}

	/**
	 * Returns the world descriptors used by the simulation.
	 * @return the world descriptors used by the simulation.
	 */
	public List<WorldDescriptor> getWDStack() {
		return Collections.unmodifiableList(WDStack);
	}

	/**
	 * Suspend the thread.
	 */
	public void pause() {
		logger.info("Game paused.");
		this.pause = true;
	}

	/**
	 * Resume the thread.
	 */
	public void unPause() {
		synchronized (this) {
			this.pause = false;
			this.notify();
		}
	}
}
