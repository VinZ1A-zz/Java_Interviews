package imc.test2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import imc.test2.Appliances.Appliance;

//plug many appliances - using adapter?
//turn them on/off all at once (in different threads) - using command pattern

// Please run junit testMultiPowerPoint for a demo.
/**
 * MultiPowerPoint is a PowerPoint (can be turned on/off) on which one or many
 * appliances can be plugged in and turned on at the same time
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public class MultiPowerPoint implements PowerPoint {

	/** List of apps, thread safe */
	private final BlockingQueue<Appliance> apps = new LinkedBlockingQueue<>();
	/** Global state of the MultiPowerPoint */
	private volatile boolean isTurnedOn = false;

	/**
	 * Constructor : plug as many appliances, at least one
	 * 
	 * @param appliances
	 *            to be plugged
	 * 
	 */
	public MultiPowerPoint(Appliance... appliances) {
		if (appliances != null && appliances.length > 0) { // redondant
			for (Appliance app : appliances) {
				if (app != null) {
					apps.add(app);
				}
			}
		}
	}

	/** copy of blocking queue references */
	public ArrayList<Appliance> getApps() {
		return new ArrayList(Arrays.asList(apps.toArray()));
	}

	/**
	 * Adds single appliance. Will turn on if global state is ON
	 * 
	 * @param app
	 *            appliance to be plugged
	 * 
	 */
	public synchronized void addAppliance(Appliance app) {
		if (app == null)
			return;
		apps.add(app);
		if (isTurnedOn && !app.isTurnedOn())
			app.powerOn();
	}

	/**
	 * Remove single appliance. Will turn the appliance off.
	 * 
	 * @param app
	 *            appliance to be unplugged
	 * 
	 */
	public synchronized void removeAppliance(Appliance app) {
		if (app == null)
			return;
		app.powerOff();
		apps.remove(app);
	}

	/**
	 * Remove all appliances, which will be all turned off.
	 * 
	 */
	public synchronized void unpluggAll() {
		for (Appliance app : apps) {
			app.powerOff();
		}
		apps.clear();
	}

	/**
	 * Global Power On. Does not re-power on Appliances again
	 */
	@Override
	public synchronized void powerOn() {
		if (isTurnedOn)
			return;
		isTurnedOn = true;
		for (Appliance app : apps) {
			if (!app.isTurnedOn()) { // should be redundant
				app.powerOn();
			}
		}
	}

	/**
	 * Global Power Off. Does not re-power off
	 */
	@Override
	public synchronized void powerOff() {
		if (!isTurnedOn)
			return;
		isTurnedOn = false;
		for (Appliance app : apps) {
			app.powerOff();
		}
	}

	/**
	 * Checks general status of the MultiPowerPoint
	 */
	@Override
	public boolean isTurnedOn() {
		return isTurnedOn;
	}

}
