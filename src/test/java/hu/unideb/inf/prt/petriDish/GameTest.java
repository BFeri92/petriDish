package hu.unideb.inf.prt.petriDish;

import hu.unideb.inf.prt.petriDish.loaders.ConfigLoader;
import hu.unideb.inf.prt.petriDish.loaders.JAXBConfigLoader;
import hu.unideb.inf.prt.petriDish.loaders.JAXBWDLoader;
import hu.unideb.inf.prt.petriDish.loaders.WDLoader;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

public class GameTest {
	
	@Rule
	public TemporaryFolder tempf = new TemporaryFolder();

	@Test
	public void test() {
		GameConfiguration conf = new GameConfiguration();
		conf.setAgentCount(1);
		conf.setInitialFoodAmount(1);
		conf.setFoodAmountIncrese(1);
		conf.setHiddenLayers(1);
		conf.setNodesPerHiddenLayer(1);
		conf.setWorldSize(1000);
		WorldDescriptor wd = new WorldDescriptor(conf);
		try {
			File cf = tempf.newFile("conf.tmp");
			ConfigLoader cLoader = new JAXBConfigLoader(cf);
			cLoader.save(conf);
			File wf = tempf.newFile("wd.tmp");
			WDLoader wLoader = new JAXBWDLoader(wf);
			wLoader.save(wd);
			Game.getInstance().loadGameConfiguration(cf.getAbsolutePath());
			assertEquals(1, Game.getInstance().getAgentsAlive());
			assertEquals(0, Game.getInstance().getWorldAge());
			assertEquals(true, Game.getInstance().hasSimulationLoaded());
			assertEquals(0, Game.getInstance().getGeneration());
			assertEquals(2, Game.getInstance().getEntities().size());
			
			assertEquals(1, Game.getInstance().getAgentsAlive());
			assertEquals(0, Game.getInstance().getWorldAge());
			assertEquals(true, Game.getInstance().hasSimulationLoaded());
			assertEquals(0, Game.getInstance().getGeneration());
			assertEquals(2, Game.getInstance().getEntities().size());
			
			Game.getInstance().loadWorldDescriptor(wf.getAbsolutePath());
			assertEquals(1, Game.getInstance().getAgentsAlive());
			assertEquals(0, Game.getInstance().getWorldAge());
			assertEquals(true, Game.getInstance().hasSimulationLoaded());
			assertEquals(0, Game.getInstance().getGeneration());
			assertEquals(2, Game.getInstance().getEntities().size());
			
			assertEquals(1, Game.getInstance().getAgentsAlive());
			assertEquals(0, Game.getInstance().getWorldAge());
			assertEquals(true, Game.getInstance().hasSimulationLoaded());
			assertEquals(0, Game.getInstance().getGeneration());
			assertEquals(2, Game.getInstance().getEntities().size());

		} catch (IOException e) {
			fail("Could not create temp files for test.");
		}
	}

}
