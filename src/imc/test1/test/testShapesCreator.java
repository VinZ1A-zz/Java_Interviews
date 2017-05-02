package imc.test1.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import imc.test1.AreaVisitor;
import imc.test1.ShapesCreator;
import imc.test1.Shapes.Shape;

/**
 * The testShapesCreator program tests the functionalities provided within the
 * ShapesCreator class.
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public class testShapesCreator {

	/** using a pre-defined random sequence */
	static RandomMock rnd;

	/**
	 * This method is executed once before all junits are executed
	 */
	@BeforeClass
	public static void toDoBeforeAllTest() {
		System.err.println("preparing ALL tests");
		// instantiate Random Mock
		rnd = new RandomMock();
		// Note: we could eventually re-use the same randoms for every test.
	}

	/**
	 * This method is executed after each junit test execution. It ensures the
	 * list of randoms can be reused for other tests
	 */
	@After
	public void toDoAfterEachTest() {
		System.err.println("cleaning-up test");
		rnd.initIndex();
	}

	/**
	 * This method is testing ShapesCreator.generatedShapes
	 */
	@Test
	public void testgeneratedShapes() {
		System.out.println("@Test testgeneratedShapes(): ");

		// fill pre-defined Random suite
		rnd.setInts(Arrays.asList(//
				2, 15, 11, 1, // Triangle (A,B,C=16,14,5)
				2, 16, 10, 18, // Triangle (A,B,C=17,13,22)
				0, 5, // Circle (R=6)
				1, 15, 23, // Rectangle (A,B=16,24)
				0, 9, // Circle (R=10)
				1, 24, 19, // Rectangle (A,B=25,20)
				1, 3, 19, // Rectangle (A,B=4,20)
				1, 25, 5, // Rectangle (A,B=26,6)
				1, 9, 10, // Rectangle (A,B=10,11)
				1, 24, 13)); // Rectangle (A,B=25,14)
		List<Double> expectedAreas = Arrays.asList(24.73863375370596, 110.30865786510141, 113.09733552923255, 384.0,
				314.1592653589793, 500.0, 80.0, 156.0, 110.0, 350.0);
		List<Double> areas = ShapesCreator.generatedShapes(rnd);
		assertEquals("Invalid number of areas returned", expectedAreas.size(), areas.size());
		for (int idx = 0; idx < areas.size(); idx++) {
			assertEquals("Invalid area for Shape #idx=" + idx, expectedAreas.get(idx), areas.get(idx));
		}
	}

	/**
	 * This method is testing ShapesCreator.generateShape
	 */
	@Test
	public void testgenerateShape() {
		System.out.println("@Test testgenerateShape(): ");
		AreaVisitor areaVisitor = new AreaVisitor();
		rnd.setInts(Arrays.asList(15, 11, 1)); // Triangle (A,B,C=16,14,5)
		Double expectedArea = 24.73863375370596;
		Shape shape = ShapesCreator.generateShape(rnd, 3, areaVisitor);
		System.out.println("Area of " + shape + " : " + shape.getProperty("area"));
		assertEquals("Invalid area for Shape " + shape, expectedArea, shape.getProperty("area"));
	}

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	/**
	 * This method is testing ShapesCreator.generateShape with invalid data
	 */
	@Test
	public void testgenerateInvalidShape() {
		System.out.println("@Test testgenerateInvalidShape(): ");
		AreaVisitor areaVisitor = new AreaVisitor();
		rnd.setInts(Arrays.asList(110, 3, 1, // Triangle (A,B,C=111,6,5) invalid
				12, 16, 18, // // Triangle (A,B,C=13,19,22) valid
				-3));
		Double expectedArea = 122.96340919151518;
		Shape shape = ShapesCreator.generateShape(rnd, 3, areaVisitor);
		System.out.println("Area of " + shape + " : " + shape.getProperty("area"));
		assertEquals("Invalid area for Shape " + shape, expectedArea, shape.getProperty("area"));
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("Radius cannot be negative (-2)");
		Shape shape2 = ShapesCreator.generateShape(rnd, 1, areaVisitor);
	}

}
