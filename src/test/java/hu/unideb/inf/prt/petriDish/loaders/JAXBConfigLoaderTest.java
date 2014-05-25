package hu.unideb.inf.prt.petriDish.loaders;

import static org.junit.Assert.*;
import hu.unideb.inf.prt.petriDish.GameConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Test for {@link JAXBConfigLoader}.
 * @author Ferenc Barta
 *
 */
public class JAXBConfigLoaderTest {
	
	public static boolean isGetter(Method method)
	{
		  if(!method.getName().startsWith("get"))      return false;
		  if(method.getParameterTypes().length != 0)   return false;  
		  if(void.class.equals(method.getReturnType())) return false;
		  return true;
	}
	
	@Rule
	public TemporaryFolder tempf = new TemporaryFolder();

	/**
	 * Creates a game configuration, saves it, then loads it and search for differences.
	 */
	@Test
	public void testFunctionality() {
		GameConfiguration origConf = new GameConfiguration();
		origConf.setAgentCount(1);
		origConf.setFoodAmountIncrese(2);
		origConf.setHiddenLayers(3);
		origConf.setInitialFoodAmount(4);
		origConf.setMutate(true);
		origConf.setMutationAmount(5);
		origConf.setMutationProb(0.312);
		origConf.setNodesPerHiddenLayer(6);
		origConf.setRecombine(false);
		origConf.setWinnerCount(7);
		origConf.setWorldSize(1024);
		try
		{
			File f;
			f = tempf.newFile("test.tmp");
			JAXBConfigLoader loader = new JAXBConfigLoader(f);
			loader.save(origConf);
			GameConfiguration loadedConf = loader.load();
			for (Method method : GameConfiguration.class.getMethods())
			{
				if (isGetter(method))
				{
					Object orig=method.invoke(origConf);
					Object loaded=method.invoke(loadedConf);
					if (!orig.equals(loaded)) fail("Values returned by "+method.getName()+" not match");
					//assertEquals("Fields not match", method.invoke(origConf), method.invoke(loadedConf));
				}
			}
		} catch (IOException e1) {
			Assert.fail("Error while creating temporary file");
		} catch (IllegalAccessException e) {
			Assert.fail("Error while iterating through getters");
		} catch (IllegalArgumentException e) {
			Assert.fail("Error while iterating through getters");
		} catch (InvocationTargetException e) {
			Assert.fail("Error while iterating through getters");
		}
	}
}
