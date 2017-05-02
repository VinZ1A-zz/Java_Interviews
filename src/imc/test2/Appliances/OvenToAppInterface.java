package imc.test2.Appliances;

/**
 * Provides an interface from an Over to an Appliance. Could follow an adapter
 * design if we had to adapt fields or methods behaviors.
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public class OvenToAppInterface implements Appliance {

	private final Oven oven;

	public OvenToAppInterface(Oven oven) {
		if (oven == null)
			throw new NullPointerException("blender is not defined");
		this.oven = oven;
	}

	@Override
	public void powerOnAction() {
		oven.heatUp();
	}

	@Override
	public void powerOffAction() {
		oven.endBaking();
	}

	@Override
	public boolean isTurnedOn() {
		return oven.isBaking();
	}

}
