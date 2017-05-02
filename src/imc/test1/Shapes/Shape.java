package imc.test1.Shapes;

import java.util.HashMap;
import java.util.Map;

import imc.test1.Visitable;

/**
 * The Shape program defines a common interface (through an abstract class) to
 * define shapes which can have intrinsic properties defined
 * 
 * Note: A shape does NOT know what attributes it has as it should rely on
 * Visitors to provide them
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public abstract class Shape implements Visitable {

	/** can store many properties filled via Visitors. */
	private Map<String, Object> properties = new HashMap<>();

	public void addProperty(String key, Object val) {
		properties.put(key, val);
	}

	public Object getProperty(String key) {
		return properties.get(key);
	}

}
