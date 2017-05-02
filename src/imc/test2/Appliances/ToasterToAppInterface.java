package imc.test2.Appliances;

/**
 * Provides an interface from a Toaster to an Appliance. Could follow an adapter
 * design if we had to adapt fields or methods behaviors.
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public class ToasterToAppInterface implements Appliance {

	private final Toaster toaster;

	public ToasterToAppInterface(Toaster toaster) {
		if (toaster == null)
			throw new NullPointerException("toaster is not defined");
		this.toaster = toaster;
	}

	@Override
	public void powerOnAction() {
		toaster.startToasting();
	}

	@Override
	public void powerOffAction() {
		toaster.stopToasting();
	}

	@Override
	public boolean isTurnedOn() {
		return toaster.isToasting();
	}

}
