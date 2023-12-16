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
import java.awt.geom.Rectangle2D;
import java.util.Random;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.g2_4.polygons.metier.persistence.JSonVisitor;
import edu.uga.miage.m1.g2_4.polygons.metier.persistence.XMLVisitor;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeFactory;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeType;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

class SquareTest {
    Random random = new Random();
    ShapeFactory shapeFactory=ShapeFactory.getShapeFactory();

    @Test
    void square_getX_getY_test() {
        int x = random.nextInt();
        int y = random.nextInt();

        SimpleShape square1 = shapeFactory.createShape(ShapeType.SQUARE,x,y);

        assertEquals(x - 25, square1.getX());
        assertEquals(y - 25, square1.getY());
    }

    @Test
    void square_draw_test() {
        Graphics2D g2d = mock(Graphics2D.class);
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape square1 = shapeFactory.createShape(ShapeType.SQUARE,x,y);

        square1.draw(g2d);

        verify(g2d).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        verify(g2d).setPaint(any(GradientPaint.class));
        verify(g2d).setColor(Color.BLACK);
        verify(g2d).setStroke(any(BasicStroke.class));
        verify(g2d).draw(any(Rectangle2D.Double.class));
        verify(g2d).fill(any(Rectangle2D.Double.class));
    }

    @Test
    void square_isSelected_test() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape square1 = shapeFactory.createShape(ShapeType.SQUARE,x,y);

        square1.setSelected(true);

        assertTrue(square1.isSelected());
    }

    @Test
    void square_doesCoordinatesAreInShapeBounds_test() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape square1 = shapeFactory.createShape(ShapeType.SQUARE,x,y);

        int pointInsideX = x + 10;
        int pointInsideY = y + 10;
        int pointOutsideX = x - 30;
        int pointOutsideY = y - 30;

        assertTrue(square1.doesCoordinatesAreInShapeBounds(pointInsideX, pointInsideY));
        assertFalse(square1.doesCoordinatesAreInShapeBounds(pointOutsideX, pointInsideY));
        assertFalse(square1.doesCoordinatesAreInShapeBounds(pointInsideX, pointOutsideY));
        assertFalse(square1.doesCoordinatesAreInShapeBounds(pointOutsideX, pointOutsideY));
    }

    @Test
    void square_moveTo_test() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape square1 = shapeFactory.createShape(ShapeType.SQUARE,x,y);

        int targetX = random.nextInt();
        int targetY = random.nextInt();

        square1.moveTo(targetX, targetY);

        assertEquals(targetX - 25, square1.getX());
        assertEquals(targetY - 25, square1.getY());
    }

    @Test
    void square_accept_test() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape square1 = shapeFactory.createShape(ShapeType.SQUARE,x,y);
        JSonVisitor jsonVisitor = new JSonVisitor();
        XMLVisitor xmlVisitor = new XMLVisitor();

        square1.accept(jsonVisitor);
        square1.accept(xmlVisitor);

        assertEquals("{\"type\":\"square\",\"x\":" + square1.getX() + ",\"y\":" + square1.getY() + "}",
                jsonVisitor.getRepresentation());
        assertEquals(
                "<shape><type>square</type><x>" + square1.getX() + "</x><y>" + square1.getY() + "</y></shape>",
                xmlVisitor.getRepresentation());
    }

}
