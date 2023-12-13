package edu.uga.miage.m1.g2_4.polygons.metier.persistence;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Circle;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Cube;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Square;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Triangle;

/**
 * @author <a href=
 *         "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements Visitor {

    private String representation = null;

    @Override
    public void visit(Circle circle) {
        representation = xmlRepresentation("circle", circle.getX(), circle.getY());
    }

    @Override
    public void visit(Square square) {
        representation = xmlRepresentation("square", square.getX(), square.getY());
    }

    @Override
    public void visit(Triangle triangle) {
        representation = xmlRepresentation("triangle", triangle.getX(), triangle.getY());
    }

    @Override
    public void visit(Cube cube) {
        representation = xmlRepresentation("cube", cube.getX(), cube.getY());
    }

    public String xmlRepresentation(String type, int x, int y) {
        return "<shape><type>" + type + "</type><x>" + x + "</x><y>" + y + "</y></shape>";
    }
    

    /**
     * @return the representation in xml example for a Triangle:
     *
     *         <pre>
     * {@code
     *  <shape>
     *    <type>triangle</type>
     *    <x>-25</x>
     *    <y>-25</y>
     *  </shape>
     * }
     * </pre>
     */
    public String getRepresentation() {
        return representation;
    }
}