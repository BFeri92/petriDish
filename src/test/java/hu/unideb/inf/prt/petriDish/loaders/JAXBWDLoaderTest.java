package hu.unideb.inf.prt.petriDish.loaders;

import static org.junit.Assert.fail;
import hu.unideb.inf.prt.petriDish.GameConfiguration;
import hu.unideb.inf.prt.petriDish.WorldDescriptor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class JAXBWDLoaderTest {
	
	public static boolean isGetter(Method method)
	{
		  if(!method.getName().startsWith("get"))      return false;
		  if(method.getParameterTypes().length != 0)   return false;  
		  if(void.class.equals(method.getReturnType())) return false;
		  return true;
	}
	
	@Rule
	public TemporaryFolder tempf = new TemporaryFolder();

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
		WorldDescriptor origWD = new WorldDescriptor(origConf);
		try {
			File f;
			f = tempf.newFile("wdTest.tmp");
			WDLoader loader = new JAXBWDLoader(f);
			loader.save(origWD);
			WorldDescriptor loadedWD = loader.load();
			for (Method method : WorldDescriptor.class.getMethods())
			{
				if (isGetter(method))
				{
					Object orig=method.invoke(origWD);
					Object loaded=method.invoke(loadedWD);
					if (!orig.equals(loaded)) 
							fail("Values returned by "+method.getName()+" not match");
				}
			}
		} catch (IOException e) {
			Assert.fail("Error while creating temporary file");
		} catch (InvocationTargetException e) {
			Assert.fail("Error while iterating through getters");
		} catch (IllegalAccessException e) {
			Assert.fail("Error while iterating through getters");
		} catch (IllegalArgumentException e) {
			Assert.fail("Error while iterating through getters");
		}
	}
}
