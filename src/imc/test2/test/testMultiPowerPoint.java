package imc.test2.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import imc.test2.MultiPowerPoint;
import imc.test2.Appliances.Appliance;
import imc.test2.Appliances.Blender;
import imc.test2.Appliances.BlenderToAppInterface;
import imc.test2.Appliances.Oven;
import imc.test2.Appliances.OvenToAppInterface;
import imc.test2.Appliances.Toaster;
import imc.test2.Appliances.ToasterToAppInterface;

//@formatter:off
/**
 * Illustrates through a running jUnit how MultiPowerPoint is working
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
//@formatter:on
public class testMultiPowerPoint {

	//@formatter:off
	/**
	 * Tests all functionalities of MultiPowerPoint: 
	 * - adding multiple appliances 
	 * - global switch on 
	 * - all appliances should be ON 
	 * - adding single appliance 
	 * - appliance should be ON 
	 * - unplugging single appliance 
	 * - appliance should be OFF 
	 * - global switch OFF 
	 * - all appliances should be OFF
	 */
	//@formatter:on
	@Test
	public void testGlobal() {
		// Instantiate appliances
		Blender blender = new Blender();
		Toaster toaster = new Toaster();
		Oven oven = new Oven();
		Oven anotherOven = new Oven();

		// Instantiate linked Appliances
		Appliance blenderApp = new BlenderToAppInterface(blender);
		Appliance ovenApp = new OvenToAppInterface(oven);
		Appliance toasterApp = new ToasterToAppInterface(toaster);
		Appliance anotherOvenApp = new OvenToAppInterface(anotherOven);

		// ********** plug all appliances in a MultiPowerPoint ***************
		MultiPowerPoint powPt = new MultiPowerPoint(
				new Appliance[] { blenderApp, ovenApp, toasterApp, anotherOvenApp });

		// let's roll !
		powPt.powerOn();
		try { // wait until all appliances are switched on (basic,
				// non-interactive way.)
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		for (Appliance app : powPt.getApps()) {
			assertTrue("app " + app + "should be ON", app.isTurnedOn());
		}

		// ********** add single appliance ************************************
		Toaster anotherToaster = new Toaster();
		Appliance anotherToasterApp = new ToasterToAppInterface(anotherToaster);
		powPt.addAppliance(anotherToasterApp); // adds and switches on
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertTrue("app " + anotherToasterApp + "should be ON", anotherToasterApp.isTurnedOn());

		// ********* remove single appliance **********************************
		powPt.removeAppliance(toasterApp); // remove first toaster
		try { // first toaster is shutting down
			Thread.sleep(300);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertTrue("app " + toasterApp + "should be OFF", !toasterApp.isTurnedOn());

		// ********* global switch off **********************************
		powPt.powerOff(); // all off
		try { // devices are quickly shutting down
			Thread.sleep(300);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		for (Appliance app : powPt.getApps()) {
			assertTrue("app " + app + "should be OFF", !app.isTurnedOn());
		}
	}

}
