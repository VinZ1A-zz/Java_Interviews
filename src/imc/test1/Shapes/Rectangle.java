package imc.test1.Shapes;

import java.util.Random;

/**
 * The Rectangle program defines the basic definition of a rectangle Shape
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public class Rectangle extends Shape {

	private int a, b;

	public Rectangle(int a, int b) {
		if (a < 0 || b < 0)
			throw new IllegalArgumentException("Dimension(s) cannot be negative");
		this.a = a;
		this.b = b;
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}

	public static Rectangle generate(Random rnd, int maxA, int maxB) {
		int genA = rnd.nextInt(maxA) + 1;
		int genB = rnd.nextInt(maxB) + 1;
		return new Rectangle(genA, genB);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "Rectangle (A,B=" + a + "," + b + ")";
	}

}
