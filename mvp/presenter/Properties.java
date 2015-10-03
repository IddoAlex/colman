package presenter;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;

import exceptions.ColmanException;

public class Properties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 555913593004844041L;

	HashMap<String, String> map;

	private XMLEncoder encoder;
	private XMLDecoder decoder;

	public Properties() {
		map = new HashMap<>();
	}

	public String addProperty(String key, String value) {
		return map.put(key.toLowerCase(), value.toLowerCase());
	}
	
	public String getProperty(String key) {
		return map.get(key.toLowerCase());
	}

	public String removePropety(String key) {
		return map.remove(key.toLowerCase());
	}

	public void save(OutputStream os) {
		encoder = new XMLEncoder(os);
		encoder.writeObject(map);
		encoder.close();
	}
	
	@SuppressWarnings("unchecked")
	public void load(InputStream is) throws ColmanException {
		Object obj;
		decoder = new XMLDecoder(is);
		obj = decoder.readObject();
		decoder.close();
		if(obj instanceof HashMap<?,?>) {
			map = (HashMap<String,String>) obj;
		} else {
			throw new ColmanException("Invalid input stream provided");
		}
	}

}
