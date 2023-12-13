package edu.uga.miage.m1.g2_4.polygons.metier.shapes;

/**
 * @author <a href=
 *         "mailto:samuel.damessi@etu.univ-grenoble-alpes.fr">damessis</a>
 *
 */

public class ShapeFactory {

	private static ShapeFactory shapeFactoryInstance;

	private ShapeFactory() {

	}

	public SimpleShape createShape(ShapeType shapeType, int x, int y) {
		switch (shapeType) {
			case SQUARE:
				return new Square(x, y);
			case TRIANGLE:
				return new Triangle(x, y);
			case CIRCLE:
				return new Circle(x, y);
			default:
				return new Cube(x, y);
		}
	}

	public static ShapeFactory getShapeFactory() {
		if (shapeFactoryInstance == null) {
			shapeFactoryInstance = new ShapeFactory();
		}
		return shapeFactoryInstance;
	}
}
