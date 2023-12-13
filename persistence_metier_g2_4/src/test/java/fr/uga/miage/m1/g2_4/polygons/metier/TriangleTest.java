package fr.uga.miage.m1.g2_4.polygons.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.util.Random;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.g2_4.polygons.metier.persistence.JSonVisitor;
import edu.uga.miage.m1.g2_4.polygons.metier.persistence.XMLVisitor;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeFactory;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeType;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

class TriangleTest {
    Random random = new Random();
    ShapeFactory shapeFactory=ShapeFactory.getShapeFactory();

    @Test
    void square_getX_getY_test() {
        int x = random.nextInt();
        int y = random.nextInt();

        SimpleShape triangle1 = shapeFactory.createShape(ShapeType.TRIANGLE,x,y);

        assertEquals(x - 25, triangle1.getX());
        assertEquals(y - 25, triangle1.getY());
    }

    @Test
    void triangle_drawTest() {
        Graphics2D g2d = mock(Graphics2D.class);
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape triangle1 = shapeFactory.createShape(ShapeType.TRIANGLE,x,y);

        triangle1.draw(g2d);

        verify(g2d).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        verify(g2d).setPaint(any(GradientPaint.class));
        verify(g2d).setColor(Color.BLACK);
        verify(g2d).setStroke(any(BasicStroke.class));
        verify(g2d).draw(any(GeneralPath.class));
        verify(g2d).fill(any(GeneralPath.class));
    }

    @Test
    void triangle_isSelected_test() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape triangle1 = shapeFactory.createShape(ShapeType.TRIANGLE,x,y);

        triangle1.setSelected(true);

        assertTrue(triangle1.isSelected());
    }

    @Test
    void triangle_doesCoordinatesAreInShapeBoundsTest() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape triangle1 = shapeFactory.createShape(ShapeType.TRIANGLE,x,y);

        int pointInsideX = x + 10;
        int pointInsideY = y + 10;
        int pointOutsideX = x - 30;
        int pointOutsideY = y - 30;

        assertTrue(triangle1.doesCoordinatesAreInShapeBounds(pointInsideX, pointInsideY));
        assertFalse(triangle1.doesCoordinatesAreInShapeBounds(pointOutsideX, pointInsideY));
        assertFalse(triangle1.doesCoordinatesAreInShapeBounds(pointInsideX, pointOutsideY));
        assertFalse(triangle1.doesCoordinatesAreInShapeBounds(pointOutsideX, pointOutsideY));
    }

    @Test
    void triangle_moveTo_test() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape triangle1 = shapeFactory.createShape(ShapeType.TRIANGLE,x,y);

        int targetX = random.nextInt();
        int targetY = random.nextInt();

        triangle1.moveTo(targetX, targetY);

        assertEquals(targetX - 25, triangle1.getX());
        assertEquals(targetY - 25, triangle1.getY());
    }

    @Test
    void triangle_accept_test() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape triangle1 = shapeFactory.createShape(ShapeType.TRIANGLE,x,y);
        JSonVisitor jsonVisitor = new JSonVisitor();
        XMLVisitor xmlVisitor = new XMLVisitor();

        triangle1.accept(jsonVisitor);
        triangle1.accept(xmlVisitor);

        assertEquals("{\"type\":\"triangle\",\"x\":" + triangle1.getX() + ",\"y\":" + triangle1.getY() + "}",
                jsonVisitor.getRepresentation());
        assertEquals(
                "<shape><type>triangle</type><x>" + triangle1.getX() + "</x><y>" + triangle1.getY() + "</y></shape>",
                xmlVisitor.getRepresentation());
    }

}
