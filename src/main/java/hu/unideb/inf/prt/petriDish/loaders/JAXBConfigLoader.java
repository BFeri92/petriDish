package hu.unideb.inf.prt.petriDish.loaders;

import hu.unideb.inf.prt.petriDish.GameConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAXBConfigLoader implements ConfigLoader {

	static private Logger logger = LoggerFactory
			.getLogger(JAXBConfigLoader.class);

	// private File f=null;
	private InputStream istream = null;
	private OutputStream ostream = null;
	private File f;

	/**
	 * Constructor, the configuration will be loaded/saved to the file described
	 * by the parameter.
	 * 
	 * @param f
	 *            File to use
	 */
	public JAXBConfigLoader(File f) {
		this.f = f;
	}

	/**
	 * Constructor, the configuration will be loaded/saved to the file described
	 * by the parameter.
	 * 
	 * @param f
	 *            File path of the file to use
	 */
	public JAXBConfigLoader(String path) {
		f = new File(path);
	}

	public JAXBConfigLoader(InputStream istream) {
		this.istream = istream;
		this.ostream = null;
	}

	public JAXBConfigLoader(OutputStream ostream) {
		this.ostream = ostream;
		this.istream = null;
	}

	public GameConfiguration load() {
		try {
			if (istream == null) {
				istream = new FileInputStream(f);
			}
			JAXBContext context = JAXBContext
					.newInstance(GameConfiguration.class);
			Unmarshaller umarsh = context.createUnmarshaller();
			GameConfiguration ret = (GameConfiguration) umarsh
					.unmarshal(istream);
			logger.info("Configuration file loaded: " + f);
			return ret;
		} catch (JAXBException e) {
			logger.error("Could not load configuration file " + f);
			logger.debug("Error message is: " + e.getMessage());
			return null;
		} catch (FileNotFoundException e) {
			logger.error("Could not load world description file "+f+", file not found.");
			return null;
		}
	}

	public boolean save(GameConfiguration conf) {
		boolean ostreamCreatedHere = false;
		try {
			if (ostream == null) {
				ostream = new FileOutputStream(f);
				ostreamCreatedHere = true;
			}
			JAXBContext context = JAXBContext
					.newInstance(GameConfiguration.class);
			Marshaller marsh = context.createMarshaller();
			marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marsh.marshal(conf, ostream);
			return true;
		} catch (JAXBException e) {
			logger.error("Could not save configuration file " + f);
			logger.debug("Error message is: " + e.getMessage());
			return false;
		} catch (FileNotFoundException e) {
			logger.error("Could create world descriptor file "+f);
			return false;
		} finally
		{
			if (ostreamCreatedHere) ostream=null;
		}
	}
}
