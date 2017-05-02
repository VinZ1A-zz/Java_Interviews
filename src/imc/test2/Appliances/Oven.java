package imc.test2.Appliances;

/**
 * Third-party Oven Note: cannot implement any (new) interface or extend from a
 * class
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public class Oven {

	/** current state */
	private boolean isBaking = false;

	/** I am powering on! */
	public void heatUp() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.err.println("Oven is ON in ThreadID " + Thread.currentThread().getId());
		isBaking = true;
	}

	/** I am powering off! */
	public void endBaking() {
		System.err.println("Oven is OFF");
		isBaking = false;
	}

	/** Which state I am? */
	public boolean isBaking() {
		return isBaking;
	}
}
