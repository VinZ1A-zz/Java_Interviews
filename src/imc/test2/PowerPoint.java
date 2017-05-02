package imc.test2;

//@formatter:off
/**
 * Generic, basic powerPoint behaviors
 * Applies to everything that can be plugged in a PowerPoint:
 *   - MultiPowerPoint
 *   - Appliances
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
//@formatter:on
public interface PowerPoint {
	boolean isTurnedOn();

	void powerOn();

	void powerOff();
}
