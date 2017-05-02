package imc.test2.Appliances;

/**
 * Provides an interface from a Blender to an Appliance. Could follow an adapter
 * design if we had to adapt fields or methods behaviors.
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public class BlenderToAppInterface implements Appliance {

	private final Blender blender;

	public BlenderToAppInterface(Blender blender) {
		if (blender == null)
			throw new NullPointerException("blender is not defined");
		this.blender = blender;
	}

	@Override
	public void powerOnAction() {
		blender.swizzle();
	}

	@Override
	public void powerOffAction() {
		blender.allChopped();
	}

	@Override
	public boolean isTurnedOn() {
		return blender.isSwizzling();
	}

}
