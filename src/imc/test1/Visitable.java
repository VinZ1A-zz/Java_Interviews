package imc.test1;

import imc.test1.Shapes.Circle;
import imc.test1.Shapes.Rectangle;
import imc.test1.Shapes.Shape;
import imc.test1.Shapes.Triangle;

/**
 * The Visitable interface will be implemented for Objects which can be visited.
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public interface Visitable {
	/** to be implemented by each Visitable object */
	void accept(Visitor visitor);

	/**
	 * The Visitor interface describes - actions that can be performed into the
	 * visited object - which objects can be visited (visit() methods)
	 *
	 */
	static interface Visitor {

		/** to be statically defined for each Visitor */
		String getPropertyName();

		/**
		 * Adds to a given element a property. The key of this property is given
		 * by the PropertyName attribute of the Visitor
		 * 
		 * @param elem
		 *            element for which property need to be added
		 * @param val
		 *            value of the property
		 */
		default void addToElem(Shape elem, Object val) {
			if (getPropertyName() == null)
				throw new UnsupportedOperationException("Property name for this visitor has not been defined");
			elem.addProperty(getPropertyName(), val);
		}

		void visit(Circle element);

		void visit(Triangle element);

		void visit(Rectangle element);

	}
}
