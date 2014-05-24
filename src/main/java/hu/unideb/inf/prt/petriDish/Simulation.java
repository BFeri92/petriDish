package hu.unideb.inf.prt.petriDish;

import hu.unideb.inf.prt.petriDish.Genotype.GenomSizeNotMatchException;
import hu.unideb.inf.prt.petriDish.ANN.FeedForwardNeuron.WeigthNumberNotMatchException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Simulation extends Thread {
	static private Logger logger = LoggerFactory.getLogger(Simulation.class);
	private List<WorldDescriptor> WDStack;
	private GameWorld world;
	private int generation;
	private long delay;
	private boolean pause = false;

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	private long lastRendered;
	private boolean running;

	Simulation(WorldDescriptor descr, long stepDelay) {
		generation = 0;
		WDStack = new Vector<WorldDescriptor>();
		logger.info("Creating new simulation");
		WDStack.add(descr);
		delay = stepDelay;
		lastRendered = 0;
		try {
			logger.info("Creating game world");
			world = new GameWorld(descr);
		} catch (WeigthNumberNotMatchException e) {
			logger.error("Error while loading world descriptor: world descriptor file is possibliy corrupted");
			logger.info("Number of genes in the container not match with the one described in the world configuration");
			Game.getInstance().getUI().error("Invalid world descriptor.");
		}
	}

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
						lastRendered = System.currentTimeMillis();
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

	public int getGeneration() {
		return generation;
	}

	public long getWorldAge() {
		if (world != null)
			return world.getStepCount();
		return 0;
	}

	public void stopSimulation() {
		running = false;
	}

	public GameWorld getWorld() {
		return world;
	}

	public List<WorldDescriptor> getWDStack() {
		return Collections.unmodifiableList(WDStack);
	}

	public void pause() {
		logger.info("Game paused.");
		this.pause = true;
	}

	public void unPause() {
		synchronized (this) {
			this.pause = false;
			this.notify();
		}
	}
}
