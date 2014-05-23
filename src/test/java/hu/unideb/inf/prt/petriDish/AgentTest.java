package hu.unideb.inf.prt.petriDish;

import static org.junit.Assert.*;
import hu.unideb.inf.prt.petriDish.ANN.FeedForwardNeuron.WeigthNumberNotMatchException;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class AgentTest {

	private static Agent a;
	
	@BeforeClass
	public static void beforeClass()
	{
		List<Double> genes = new ArrayList<Double>();
		genes.add(1.0);
		genes.add(0.0);
		genes.add(0.0);
		genes.add(1.0);
		genes.add(1.0);
		Genotype g = new Genotype(0,1,1,genes);
		try {
			a = new Agent(g,0,0,0);
		} catch (WeigthNumberNotMatchException e) {
			fail("Previous layers neuron count and the number of the weights does not match");
		}
	}
	
	@Test
	public void testGetGenotype() {
		Genotype genotype = a.getGenotype();
		List<Double> genes= genotype.getGenes();
		List<Double> actualGenes = new ArrayList<Double>();
		actualGenes.add(1.0);
		actualGenes.add(0.0);
		actualGenes.add(0.0);
		actualGenes.add(1.0);
		actualGenes.add(1.0);
		assertEquals(actualGenes, genes);
	}

	@Test
	public void test() {
		assertEquals(0.0, a.getHunger(), 0);
		assertEquals(0.0, a.getHeading(), 0);
		a.exec();
		a.apply();
		//This is here temporarily, because short-lived windows stays on my display
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(3.0, a.getxPos(), 0.0);
	}

	@Test
	public void testSees() {
		Entity seesThis = new Entity(a.getxPos()+2*Entity.radius, a.getyPos());
		Entity shouldntSeeThis = new Entity(a.getxPos(), a.getyPos()+2*Entity.radius);
		assertTrue(a.sees(seesThis));
		assertTrue(!a.sees(shouldntSeeThis));
	}

}
