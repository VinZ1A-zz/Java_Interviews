package imc.test1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import imc.test1.Visitable.Visitor;
import imc.test1.Shapes.Circle;
import imc.test1.Shapes.Rectangle;
import imc.test1.Shapes.Shape;
import imc.test1.Shapes.Triangle;

/**
 * The ShapesCreator program implements an application that generates
 * dynamically or statically Shapes and determines their area, using a Visitor
 * pattern.
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public class ShapesCreator {

	/** number of Shapes to be dynmically generated */
	static int _nbOfShapes = 10;
	// bounds for pure Random execution
	static int _circle_maxRad = 30;
	static int _rect_maxA = 30;
	static int _rect_maxB = 30;
	static int _triangle_maxA = 30;
	static int _triangle_maxB = 30;
	static int _triangle_maxC = 30;

	/**
	 * Main Method - generates shapes using different mechanisms
	 * 
	 * @param args
	 *            Usual main() arguments (not used)
	 */
	static public void main(String... args) {
		Random rnd = new Random();

		// generates arbitrary instances of shapes
		generatedShapes(rnd);

		// (optional) statically generated shapes
		System.out.println("**** directed Shapes ****");
		directedShapes();
	}

	/**
	 * This method generates a <_nbOfShapes> number of shapes and computes their
	 * respective area values.
	 * 
	 * @param rnd
	 *            Random instance (externalized so it can be mocked)
	 * @return List<Double> Returns list of all computed areas
	 */
	public static List<Double> generatedShapes(Random rnd) {
		List<Double> ret = new ArrayList<>();
		AreaVisitor areaVisitor = new AreaVisitor();
		List<Shape> shapes = generateArbitraryShapes(rnd, areaVisitor);
		for (Shape shape : shapes) {
			System.out.println("Area of " + shape + " : " + shape.getProperty("area"));
			ret.add((Double) shape.getProperty("area"));
		}
		return ret;
	}

	/**
	 * This method generates a <_nbOfShapes> number of shapes and applies the
	 * given visitor to each of them
	 * 
	 * @param rnd
	 *            Random instance
	 * @param visitor
	 *            Visitor to be applied to each Shape
	 * @return List<Double> Returns list of all computed areas
	 */
	private static List<Shape> generateArbitraryShapes(Random rnd, Visitor visitor) {

		List<Shape> shapes = new ArrayList<>();
		for (int i = 0; i < _nbOfShapes; i++) {
			int shapeType = rnd.nextInt(3) + 1;
			shapes.add(generateShape(rnd, shapeType, visitor));
		}
		return shapes;
	}

	/**
	 * This method generates
	 * 
	 * @param rnd
	 *            Random instance
	 * @param shapeType
	 *            Shape to be built (1-Circle ; 2-Rectangle ; 3-Triangle)
	 * @param visitor
	 *            Visitor to be applied to each Shape
	 * @return List<Double> Returns list of all computed areas
	 */
	public static Shape generateShape(Random rnd, int shapeType, Visitor visitor) {
		switch (shapeType) {
		case 1: {
			Circle circle = Circle.generate(rnd, _circle_maxRad);
			circle.accept(visitor);
			return circle;
		}
		case 2: {
			Rectangle rect = Rectangle.generate(rnd, _rect_maxA, _rect_maxB);
			rect.accept(visitor);
			return rect;
		}
		case 3: {
			Triangle triangle = Triangle.generate(rnd, _triangle_maxA, _triangle_maxB, _triangle_maxC);
			triangle.accept(visitor);
			return triangle;
		}
		}
		return null;
	}

	/**
	 * This method generates shapes statically and apply an AreaVisitor instance
	 * 
	 * @return List<Double> Returns list of all computed areas
	 */
	private static List<Double> directedShapes() {
		List<Double> ret = new ArrayList<>();
		AreaVisitor areaVisitor = new AreaVisitor();

		Circle circleA = new Circle(3);
		circleA.accept(areaVisitor);

		Circle circleB = new Circle(5);
		circleB.accept(areaVisitor);

		Circle unvisitedCircle = new Circle(10);

		Triangle triangleA = new Triangle(24, 30, 18);
		triangleA.accept(areaVisitor);

		ret.add((Double) circleA.getProperty("area"));
		ret.add((Double) triangleA.getProperty("area"));
		ret.add((Double) unvisitedCircle.getProperty("area")); // null
		ret.add((Double) triangleA.getProperty("area")); // null

		System.out.println("Area of circleA : " + circleA.getProperty("area"));
		System.out.println("Area of triangleA : " + triangleA.getProperty("area"));
		System.out.println("Area of unvisitedCircle : " + unvisitedCircle.getProperty("area")); // null
		System.out.println("invalidProp of triangleA : " + triangleA.getProperty("invalidProp")); // null

		return ret;
	}

}
