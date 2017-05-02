package imc.test2.Appliances;

import imc.test2.PowerPoint;

//@formatter:off
/**
 * The Appliance interface contains the basic interfaces which every third-party
 * appliance will define. It also supports PowerPoint basic operations.
 * 
 * Notes: 
 * - interface is more suitable than adapter in this case as every method
 * will reflect a corresponding method in the third-party appliance 
 * - Adapter
 * would be suitable to trigger actions or fetch/convert resources from each
 * appliance but this is not applicable in this simple case
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
// @formatter:on
public interface Appliance extends PowerPoint {

	/**
	 * Triggers Power On Action in a separate (basic!) Thread
	 * 
	 */
	@Override
	public default void powerOn() {
		new Thread(() -> {
			powerOnAction();
		}).start();
	};

	/**
	 * Triggers Power Off Action in a separate Thread
	 * 
	 */
	@Override
	public default void powerOff() {
		new Thread(() -> {
			powerOffAction();
		}).start();
	};

	/** generic power on */
	public void powerOnAction();

	/** generic power off */
	public void powerOffAction();

}
