package imc.test2.Appliances;

/**
 * Third-party Blender Note: cannot implement any (new) interface or extend from
 * a class
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public class Blender {

	/** current state */
	private boolean isSwizzling = false;

	/** I am powering on! */
	public void swizzle() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.err.println("Blender is ON in ThreadID " + Thread.currentThread().getId());
		isSwizzling = true;
	}

	/** I am powering off! */
	public void allChopped() {
		System.err.println("Blender is OFF");
		isSwizzling = false;
	}

	/** Which state I am? */
	public boolean isSwizzling() {
		return isSwizzling;
	}

}
