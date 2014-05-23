package hu.unideb.inf.prt.petriDish.loaders;

import hu.unideb.inf.prt.petriDish.WorldDescriptor;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAXBWDLoader implements WDLoader {
	
	static private Logger logger = LoggerFactory.getLogger(JAXBConfigLoader.class);

	private File f=null;
	
	/**
	 * Constructor, the world descriptor will be loaded/saved to the
	 * file described by the parameter.
	 * @param f File to use
	 */
	public JAXBWDLoader(File f)
	{
		this.f=f;
	}

	/**
	 * Constructor, the world descriptor will be loaded/saved to the
	 * file described by the parameter.
	 * @param f File path of the file to use
	 */
	public JAXBWDLoader(String path)
	{
		f = new File(path);
	}
	public WorldDescriptor load() {
		try {
			JAXBContext context = JAXBContext.newInstance(WorldDescriptor.class);
			Unmarshaller umarsh = context.createUnmarshaller();
			WorldDescriptor ret = (WorldDescriptor)umarsh.unmarshal(f);
			logger.info("World description loaded: "+f);
			return ret;
		} catch (JAXBException e) {
			logger.error("Could not load world description file "+f);
			logger.error("Error message is: "+e.getMessage());
			return null;
		}
	}

	public boolean save(WorldDescriptor descr) {
		try {
			JAXBContext context = JAXBContext.newInstance(WorldDescriptor.class);
			Marshaller marsh = context.createMarshaller();
			marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marsh.marshal(descr, f);
			logger.info("World descriptor file saved: "+f);
			return true;
		} catch (JAXBException e) {
			logger.error("Could not save world descriptor to "+f);
			logger.error("Error message is: "+e.getMessage());
			System.out.println(e.getStackTrace());
			return false;
		}
	}
}
