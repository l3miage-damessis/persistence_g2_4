package edu.uga.miage.m1.g2_4.polygons.metier.persistence;

import javax.json.Json;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Circle;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Cube;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Square;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Triangle;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JSonVisitor implements Visitor {

    private String representation = null;

    @Override
    public void visit(Circle circle) {
        representation = Json.createObjectBuilder().add("type", "circle").add("x", circle.getX())
                .add("y", circle.getY()).build().toString();
    }

    @Override
    public void visit(Square square) {
        representation = Json.createObjectBuilder().add("type", "square").add("x", square.getX())
                .add("y", square.getY()).build().toString();
    }

    @Override
    public void visit(Triangle triangle) {
        representation = Json.createObjectBuilder().add("type", "triangle").add("x", triangle.getX())
                .add("y", triangle.getY()).build().toString();
    }

    @Override
    public void visit(Cube cube) {
        representation = Json.createObjectBuilder().add("type", "cube").add("x", cube.getX())
                .add("y", cube.getY()).build().toString();
    }

    /**
     * @return the representation in JSon example for a Circle
     *
     *         <pre>
     * {@code
     *  {
     *     "shape": {
     *     	  "type": "circle",
     *        "x": -25,
     *        "y": -25
     *     }
     *  }
     * }
     *         </pre>
     */
    public String getRepresentation() {
        return representation;
    }
}