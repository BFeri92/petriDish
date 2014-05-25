package hu.unideb.inf.prt.petriDish;

import static org.junit.Assert.*;
import hu.unideb.inf.prt.petriDish.ANN.FeedForwardNeuron.WeigthNumberNotMatchException;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for {@link Agent}.
 * @author Ferenc Barta
 *
 */
public class AgentTest {

	/**
	 * Agent to test forward movement.
	 */
	private static Agent moveForwardAgent;
	/**
	 * Agent to test staying in place.
	 */
	private static Agent shouldntMoveAgent;
	/**
	 * Agent to test right turn.
	 */
	private static Agent turnRightAgent;
	/**
	 * Agent to test left turn.
	 */
	private static Agent turnLeftAgent;
	
	/**
	 * Initialize the agents.
	 */
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
			moveForwardAgent = new Agent(g,0,0,0);
		} catch (WeigthNumberNotMatchException e) {
			fail("Previous layers neuron count and the number of the weights does not match");
		}
		
		genes = new ArrayList<Double>();
		genes.add(1.0);
		genes.add(0.0);
		genes.add(0.0);
		genes.add(-1.0);	//ANN output should be about 0.324962
		genes.add(-1.0);	//ANN output should be about 0.324962
		g = new Genotype(0,1,1,genes);
		try {
			shouldntMoveAgent = new Agent(g,0,0,0);
		} catch (WeigthNumberNotMatchException e) {
			fail("Previous layers neuron count and the number of the weights does not match");
		}
		
		genes = new ArrayList<Double>();
		genes.add(1.0);
		genes.add(0.0);
		genes.add(0.0);
		genes.add(1.0);
		genes.add(-1.0);
		g = new Genotype(0,1,1,genes);
		try {
			turnLeftAgent = new Agent(g,0,0,0);
		} catch (WeigthNumberNotMatchException e) {
			fail("Previous layers neuron count and the number of the weights does not match");
		}
		
		genes = new ArrayList<Double>();
		genes.add(1.0);
		genes.add(0.0);
		genes.add(0.0);
		genes.add(-1.0);
		genes.add(1.0);
		g = new Genotype(0,1,1,genes);
		try {
			turnRightAgent = new Agent(g,0,0,0);
		} catch (WeigthNumberNotMatchException e) {
			fail("Previous layers neuron count and the number of the weights does not match");
		}

		assertEquals("Initial hunger was wrong.", 0.0, moveForwardAgent.getHunger(), 0);
		assertEquals("Initial heading was wrong.", 0.0, moveForwardAgent.getHeading(), 0);
		
		//This is here temporarily, because short-lived windows stays on my display
		Game.getInstance(); //create the game here
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests if getGenotype returns the actual genotype.
	 */
	@Test
	public void testGetGenotype() {
		Genotype genotype = moveForwardAgent.getGenotype();
		List<Double> genes= genotype.getGenes();
		List<Double> actualGenes = new ArrayList<Double>();
		actualGenes.add(1.0);
		actualGenes.add(0.0);
		actualGenes.add(0.0);
		actualGenes.add(1.0);
		actualGenes.add(1.0);
		assertEquals(actualGenes, genes);
	}

	/**
	 * Test forward movement.
	 */
	@Test
	public void testMoveFwd()
	{
		moveForwardAgent.exec();
		moveForwardAgent.apply();
		assertEquals("moveForwardAgent's x position was wrong", 3.0, moveForwardAgent.getxPos(), 0.0);
		assertEquals("moveForwardAgent's y position was wrong", 0.0, moveForwardAgent.getyPos(), 0.0);
		assertEquals("moveForwardAgent's heading was wrong", 0.0, moveForwardAgent.getHeading(), 0.0);
	}
	
	/**
	 * Test staying in place.
	 */
	public void testStayInPlace()
	{
		shouldntMoveAgent.exec();
		shouldntMoveAgent.apply();		
		assertEquals("shouldntMoveAgent's x position was wrong", 0.0, shouldntMoveAgent.getxPos(), 0.0);
		assertEquals("shouldntMoveAgent's y position was wrong", 0.0, shouldntMoveAgent.getyPos(), 0.0);
		assertEquals("shouldntMoveAgent's heading was wrong", 0.0, shouldntMoveAgent.getHeading(), 0.0);
	}
	
	/**
	 * Test left turn.
	 */
	public void testTurnLeft()
	{
		turnLeftAgent.exec();
		turnLeftAgent.apply();		
		assertEquals("turnLeftAgent's x position was wrong", 0.0, turnLeftAgent.getxPos(), 0.0);
		assertEquals("turnLeftAgent's y position was wrong", 0.0, turnLeftAgent.getyPos(), 0.0);
		assertEquals("turnLeftAgent's heading was wrong", 5.0, turnLeftAgent.getHeading(), 0.0);
	}
	
	/**
	 * Test right turn.
	 */
	public void testTurnRight()
	{
		turnRightAgent.exec();
		turnRightAgent.apply();
		assertEquals("turnRightAgent's x position was wrong", 0.0, turnRightAgent.getxPos(), 0.0);
		assertEquals("turnRightAgent's y position was wrong", 0.0, turnRightAgent.getyPos(), 0.0);
		assertEquals("turnRightAgent's heading was wrong", 355.0, turnRightAgent.getHeading(), 0.0);
	}

	/**
	 * Test sight of an agent.
	 * The test checks if the agent can see entity before it, 
	 * do not see the entity next to it and do not see the entity behind it.
	 */
	@Test
	public void testSees() {
		Entity seesThis = new Entity(moveForwardAgent.getxPos()+2*Entity.radius, moveForwardAgent.getyPos());
		Entity shouldntSeeThis = new Entity(moveForwardAgent.getxPos(), moveForwardAgent.getyPos()+2*Entity.radius);
		Entity shouldntHaveEyeOnTheBack = new Entity(moveForwardAgent.getxPos()-2*Entity.radius, moveForwardAgent.getyPos());
		assertTrue(moveForwardAgent.sees(seesThis));
		assertTrue(!moveForwardAgent.sees(shouldntSeeThis));
		assertTrue(!moveForwardAgent.sees(shouldntHaveEyeOnTheBack));
	}

}
