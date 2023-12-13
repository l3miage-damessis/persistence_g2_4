package fr.uga.miage.m1.g2_4.polygons.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Random;

import org.junit.Test;

import edu.uga.miage.m1.g2_4.polygons.metier.persistence.JSonVisitor;
import edu.uga.miage.m1.g2_4.polygons.metier.persistence.XMLVisitor;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeFactory;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeType;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

class CircleTest {
    Random random = new Random();
    ShapeFactory shapeFactory=ShapeFactory.getShapeFactory();

    @Test
    void circle_getX_getY_test() {
        int x = random.nextInt();
        int y = random.nextInt();

        SimpleShape circle1 = shapeFactory.createShape(ShapeType.CIRCLE,x,y);

        assertEquals(x - 25, circle1.getX());
        assertEquals(y - 25, circle1.getY());
    }


    @Test
    void circle_draw_test() {
        Graphics2D g2d = mock(Graphics2D.class);
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape circle1 = shapeFactory.createShape(ShapeType.CIRCLE,x,y);

        circle1.draw(g2d);

        verify(g2d).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        verify(g2d).setPaint(any(GradientPaint.class)); // You can use argument matchers if needed
        verify(g2d).fill(any(Ellipse2D.class));
        verify(g2d).setStroke(any(BasicStroke.class));
        verify(g2d).setColor(Color.BLACK);
        verify(g2d).draw(any(Ellipse2D.class));
    }

    @Test
    void circle_isSelected_test() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape circle1 = shapeFactory.createShape(ShapeType.CIRCLE,x,y);

        circle1.setSelected(true);

        assertTrue(circle1.isSelected());
    }

    @Test
    void circle_doesCoordinatesAreInShapeBoundsTest() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape circle1 = shapeFactory.createShape(ShapeType.CIRCLE,x,y);

        int pointInsideX = x + 10;
        int pointInsideY = y + 10;
        int pointOutsideX = x - 30;
        int pointOutsideY = y - 30;

        assertTrue(circle1.doesCoordinatesAreInShapeBounds(pointInsideX, pointInsideY));
        assertFalse(circle1.doesCoordinatesAreInShapeBounds(pointOutsideX, pointInsideY));
        assertFalse(circle1.doesCoordinatesAreInShapeBounds(pointInsideX, pointOutsideY));
        assertFalse(circle1.doesCoordinatesAreInShapeBounds(pointOutsideX, pointOutsideY));
    }


    @Test
    void circle_moveTo_test() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape circle1 = shapeFactory.createShape(ShapeType.CIRCLE,x,y);

        int targetX = random.nextInt();
        int targetY = random.nextInt();

        circle1.moveTo(targetX, targetY);

        assertEquals(targetX - 25, circle1.getX());
        assertEquals(targetY - 25, circle1.getY());
    }

    @Test
    void circle_acceptTest() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape circle1 = shapeFactory.createShape(ShapeType.CIRCLE,x,y);
        JSonVisitor jsonVisitor = new JSonVisitor();
        XMLVisitor xmlVisitor = new XMLVisitor();

        circle1.accept(jsonVisitor);
        circle1.accept(xmlVisitor);

        assertEquals("{\"type\":\"circle\",\"x\":" + circle1.getX() + ",\"y\":" + circle1.getY() + "}",
                jsonVisitor.getRepresentation());
        assertEquals("<shape><type>circle</type><x>" + circle1.getX() + "</x><y>" + circle1.getY() + "</y></shape>",
                xmlVisitor.getRepresentation());
    }
}
