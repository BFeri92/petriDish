package hu.unideb.inf.prt.petriDish;

import hu.unideb.inf.prt.petriDish.ANN.FeedForwardNeuron.WeigthNumberNotMatchException;
import hu.unideb.inf.prt.petriDish.loaders.JAXBWDLoader;
import hu.unideb.inf.prt.petriDish.loaders.WDLoader;

import javax.xml.bind.JAXBException;


public class Main {
	
	public static void main(String[] args) throws JAXBException, WeigthNumberNotMatchException {
		/*GameConfiguration origConf = new GameConfiguration();
		origConf.setAgentCount(1);
		origConf.setFoodAmountIncrese(2);
		origConf.setHiddenLayers(4);
		origConf.setInitialFoodAmount(4);
		origConf.setMutate(true);
		origConf.setMutationAmount(1);
		origConf.setMutationProb(1);
		origConf.setNodesPerHiddenLayer(1);
		origConf.setRecombine(false);
		origConf.setWinnerCount(7);
		origConf.setWorldSize(100);
		WorldDescriptor desc1 = new WorldDescriptor(origConf);
		Entity e1 = new Entity(0,0);
		Agent e2 = new Agent(desc1.getGenotypes().get(0), -100, 0, 0);
		while (true)
		{
			System.out.println(e2.getHeading()+": "+e2.sees(e1));
			e2.setHeading(e2.getHeading()+1);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.exit(0);
			}
		}*/
		/*
		WDLoader loader = new JAXBWDLoader( "testWD.xml");
		WorldDescriptor desc1 = loader.load();*/
		/*
		GameWorld world = new GameWorld(desc1);
		while (true)
		{
			System.out.println(world.getEntities().get(0).getxPos()+":"+world.getEntities().get(0).getyPos()+":"+((Agent) (world.getEntities().get(0))).getHeading());
			System.out.println(((Agent) (world.getEntities().get(0))).sees(e1));
			world.step();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.exit(0);
			}
		}*/
		Game g = Game.getInstance();
	}
}
