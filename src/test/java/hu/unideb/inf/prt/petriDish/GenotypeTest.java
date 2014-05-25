package hu.unideb.inf.prt.petriDish;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import hu.unideb.inf.prt.petriDish.Genotype;
import hu.unideb.inf.prt.petriDish.Genotype.GenomSizeNotMatchException;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for {@link Genotype}.
 * @author Ferenc Barta
 *
 */
public class GenotypeTest {

	private static Genotype g1;
	private static Genotype g2;
	private static Genotype g3;
	
	@BeforeClass
	public static void before()
	{
		g1 = new Genotype(1, 2);
		g2 = new Genotype(g1);
		try {
			g3 = new Genotype(g1,g2);
		} catch (GenomSizeNotMatchException e) {
			fail("Could not create genotype based on two other genotypes");
		}
	}
	/**
	 * Tests generations increase.
	 */
	@Test
	public void testGetGeneration() {
		assertEquals("New genotypes generation should be 0", 0, g1.getGeneration());
		assertEquals("One-parented genotypes generation should be one more than it's parents generation.", 1, g2.getGeneration());
		assertEquals("Two-parented genotypes generation should be one more than the maximum of it's parents generation.", 2, g3.getGeneration());
	}

	/**
	 * Tests if the genes are inherited from parents.
	 */
	@Test
	public void testGetGenes() {
		List<Double> genom1=g1.getGenes();
		List<Double> genom2=g2.getGenes();
		List<Double> genom3=g3.getGenes();
		for (int i=0; i<genom1.size(); i++)
		{
			assertTrue("One-parented genotype have different gene than it's parent", genom1.get(i)==genom2.get(i));
			assertTrue("Two-parented genotype have different gene than any of it's parents", (genom3.get(i)==genom2.get(i) || genom3.get(i)==genom1.get(i)));
		}
	}

	/**
	 * Tests {@link Genotype#getHiddenLayerCount()}.
	 */
	@Test
	public void testGetHiddenLayerCount() {
		assertEquals(2, g1.getHiddenLayerCount());
		assertEquals(2, g2.getHiddenLayerCount());
		assertEquals(2, g3.getHiddenLayerCount());
	}

}
