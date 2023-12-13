package edu.uga.miage.m1.g2_4.polygons.metier.persistence;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Circle;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Cube;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Square;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Triangle;

/**
 * You must define a method for each type of Visitable.
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
public interface Visitor {
	public void visit(Circle circle);
	public void visit(Square square);
	public void visit(Triangle triangle);
	public void visit(Cube cube);
}