package hu.unideb.inf.prt.petriDish.loaders;

import hu.unideb.inf.prt.petriDish.GameConfiguration;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAXBConfigLoader implements ConfigLoader{
	
	static private Logger logger = LoggerFactory.getLogger(JAXBConfigLoader.class);

	private File f=null;
	
	/**
	 * Constructor, the configuration will be loaded/saved to the
	 * file described by the parameter.
	 * @param f File to use
	 */
	public JAXBConfigLoader(File f)
	{
		this.f=f;
	}

	/**
	 * Constructor, the configuration will be loaded/saved to the
	 * file described by the parameter.
	 * @param f File path of the file to use
	 */
	public JAXBConfigLoader(String path)
	{
		f = new File(path);
	}
	public GameConfiguration load() {
		try {
			JAXBContext context = JAXBContext.newInstance(GameConfiguration.class);
			Unmarshaller umarsh = context.createUnmarshaller();
			GameConfiguration ret = (GameConfiguration)umarsh.unmarshal(f);
			logger.info("Configuration file loaded: "+f);
			return ret;
		} catch (JAXBException e) {
			logger.error("Could not load configuration file "+f);
			logger.debug("Error message is: "+e.getMessage());
			return null;
		}
	}

	public boolean save(GameConfiguration conf) {
		try {
			JAXBContext context = JAXBContext.newInstance(GameConfiguration.class);
			Marshaller marsh = context.createMarshaller();
			marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marsh.marshal(conf, f);
			logger.info("Configuration file saved: "+f);
			return true;
		} catch (JAXBException e) {
			logger.error("Could not save configuration file "+f);
			logger.debug("Error message is: "+e.getMessage());
			return false;
		}
	}
}
