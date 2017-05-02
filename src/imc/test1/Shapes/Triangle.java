package imc.test1.Shapes;

import java.util.Random;

/**
 * The Triangle program defines the basic definition of a triangle Shape
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public class Triangle extends Shape {

	private int a, b, c;

	public Triangle(int a, int b, int c) {
		if (a < 0 || b < 0 || c < 0)
			throw new IllegalArgumentException("Dimension(s) cannot be negative");
		if (a + b <= c || a + c <= b || b + c <= a)
			throw new IllegalArgumentException("Dimensions cannot describe a Triangle");
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}

	public int getC() {
		return c;
	}

	public static Triangle generate(Random rnd, int maxA, int maxB, int maxC) {
		try {
			int genA = rnd.nextInt(maxA) + 1;
			int genB = rnd.nextInt(maxB - 2) + 3; // B min = 3
			int genC = rnd.nextInt(maxC - 3) + 4; // C min = 4
			return new Triangle(genA, genB, genC);
		} catch (java.lang.IllegalArgumentException e) {
			// try until a valid Triangle is generated (0.25 chances)
			return generate(rnd, maxA, maxB, maxC);
		}

	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "Triangle (A,B,C=" + a + "," + b + "," + c + ")";
	}

}
