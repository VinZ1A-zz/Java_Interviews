package imc.test1.Shapes;

import java.util.Random;

/**
 * The Circle program defines the basic definition of a circle Shape
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public class Circle extends Shape {

	private int radius;

	public Circle(int radius) {
		if (radius < 0)
			throw new IllegalArgumentException("Radius cannot be negative (" + radius + ")");
		this.radius = radius;
	}

	public static Circle generate(Random rnd, int maxRadius) {
		int genRadius = rnd.nextInt(maxRadius) + 1;
		return new Circle(genRadius);
	}

	public int getRadius() {
		return radius;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "Circle (R=" + radius + ")";
	}

}
