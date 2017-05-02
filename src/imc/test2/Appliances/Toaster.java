package imc.test2.Appliances;

/**
 * Third-party Toaster Note: cannot implement any (new) interface or extend from
 * a class
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public class Toaster {

	/** current state */
	private boolean isToasting = false;

	/** I am powering on! */
	public void startToasting() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.err.println("Toaster is ON in ThreadID " + Thread.currentThread().getId());
		isToasting = true;
	}

	/** I am powering off! */
	public void stopToasting() {
		System.err.println("Toaster is OFF");
		isToasting = false;
	}

	/** Which state I am? */
	public boolean isToasting() {
		return isToasting;
	}
}
