package hu.unideb.inf.prt.petriDish.loaders;

import hu.unideb.inf.prt.petriDish.GameConfiguration;
import hu.unideb.inf.prt.petriDish.WorldDescriptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for load and save world descriptors with JAXB.
 * @author Ferenc Barta
 *
 */
public class JAXBWDLoader implements WDLoader {
	/**
	 * Logger.
	 */
	static private Logger logger = LoggerFactory.getLogger(JAXBWDLoader.class);
	/**
	 * The input stream for loading.
	 */
	private InputStream istream = null;
	/**
	 * The output stream for saving.
	 */
	private OutputStream ostream = null;
	/**
	 * File used for loading/saving.
	 */
	private File f;

	/**
	 * Constructor, the world descriptor will be loaded/saved to the file
	 * described by the parameter.
	 * 
	 * @param f File to use
	 */
	public JAXBWDLoader(File f) {
		this.f = f;
	}

	/**
	 * Constructor, the world descriptor will be loaded/saved to the file
	 * described by the parameter.
	 * 
	 * @param path
	 *            File path of the file to use
	 */
	public JAXBWDLoader(String path) {
		f = new File(path);
	}
	/**
	 * Creates loader, the world descriptor will be read from the given input stream.
	 * {@link JAXBWDLoader#save(GameConfiguration)} will fail on loaders created with this constructor. 
	 * @param istream the input stream to use.
	 */
	public JAXBWDLoader(InputStream istream) {
		this.istream = istream;
		this.ostream = null;
	}
	/**
	 * Creates loader, the world descriptor will be written to the output stream.
	 * {@link JAXBWDLoader#load()} will fail on loaders created with this constructor.
	 * @param ostream the output stream to use.
	 */
	public JAXBWDLoader(OutputStream ostream) {
		this.ostream = ostream;
		this.istream = null;
	}
	/**
	 * Returns a world descriptor loaded from the input specified when creating the loader.
	 * @return the loaded world descriptor.
	 */
	public WorldDescriptor load() {

		try {
			if (istream == null) {
				istream = new FileInputStream(f);
			}
			JAXBContext context = JAXBContext
					.newInstance(WorldDescriptor.class);
			Unmarshaller umarsh = context.createUnmarshaller();
			WorldDescriptor ret = (WorldDescriptor) umarsh.unmarshal(istream);
			logger.info("World descriptor file loaded: " + f);
			return ret;
		} catch (JAXBException e) {
			logger.error("Could not load world description file " + f);
			logger.error("Error message is: " + e);
			return null;
		} catch (FileNotFoundException e) {
			logger.error("Could not load world description file " + f
					+ ", file not found.");
			return null;
		}
	}
	/**
	 * Saves a world descriptor to the output specified when creating the loader.
	 * @param descr the world descriptor to save
	 * @return false on error, true otherwise
	 */
	public boolean save(WorldDescriptor descr) {
		boolean ostreamCreatedHere = false;
		try {
			if (ostream == null) {
				ostream = new FileOutputStream(f);
				ostreamCreatedHere = true;
			}
			JAXBContext context = JAXBContext
					.newInstance(WorldDescriptor.class);
			Marshaller marsh = context.createMarshaller();
			marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marsh.marshal(descr, ostream);
			return true;
		} catch (JAXBException e) {
			logger.error("Could not save world descriptor to " + f);
			logger.error("Error message is: " + e.getMessage());
			return false;
		} catch (FileNotFoundException e) {
			logger.error("Could create world descriptor file " + f);
			return false;
		} finally {
			if (ostreamCreatedHere)
				ostream = null;
		}
	}
}
