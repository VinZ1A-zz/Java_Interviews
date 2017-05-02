package imc.test1;

import imc.test1.Visitable.Visitor;
import imc.test1.Shapes.Circle;
import imc.test1.Shapes.Rectangle;
import imc.test1.Shapes.Triangle;

/**
 * The AreaVisitor class is a kind of Visitor tailored to add the property
 * "area" to each shape, containing its area value.
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public class AreaVisitor implements Visitor {

	@Override
	public String getPropertyName() {
		return "area";
	}

	@Override
	public void visit(Circle elem) {
		addToElem(elem, Math.pow(elem.getRadius(), 2) * Math.PI);
	}

	@Override
	public void visit(Triangle elem) {
		double p = (elem.getA() + elem.getB() + elem.getC()) / 2;
		double area = Math.sqrt(p * (p - elem.getA()) * (p - elem.getB()) * (p - elem.getC()));
		addToElem(elem, area);
	}

	@Override
	public void visit(Rectangle elem) {
		addToElem(elem, ((double) elem.getA()) * elem.getB());
	}

}
