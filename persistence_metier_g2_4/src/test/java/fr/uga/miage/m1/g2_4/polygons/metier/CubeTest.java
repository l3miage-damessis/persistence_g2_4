package fr.uga.miage.m1.g2_4.polygons.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Graphics2D;
import java.util.Random;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.g2_4.polygons.metier.persistence.JSonVisitor;
import edu.uga.miage.m1.g2_4.polygons.metier.persistence.XMLVisitor;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Cube;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeFactory;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeType;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;
import edu.uga.singleshape.CubePanel;

class CubeTest {

    Random random = new Random();
    ShapeFactory shapeFactory = ShapeFactory.getShapeFactory();

    @Test
    void cube_getX_getY_test() {
        int x = random.nextInt();
        int y = random.nextInt();

        SimpleShape cube1 = shapeFactory.createShape(ShapeType.CUBE, x, y);

        assertEquals(x - 25, cube1.getX());
        assertEquals(y - 25, cube1.getY());
    }

    @Test
    void cubeDrawTest() {
        Graphics2D g2d = mock(Graphics2D.class);
        int x = random.nextInt();
        int y = random.nextInt();

        SimpleShape cube1 = spy(shapeFactory.createShape(ShapeType.CUBE,x,y));

        CubePanel cubePanel = mock(CubePanel.class);

        when(((Cube)cube1).createCubePanel()).thenReturn(cubePanel);

        cube1.draw(g2d);

        verify(cubePanel, times(1)).paintComponent(g2d);
    }

    @Test
    void cube_isSelected_test() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape cube1 = shapeFactory.createShape(ShapeType.CUBE, x, y);

        cube1.setSelected(true);

        assertTrue(cube1.isSelected());
    }

    @Test
    void cube_doesCoordinatesAreInShapeBoundsTest() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape cube1 = shapeFactory.createShape(ShapeType.CUBE, x, y);

        int pointInsideX = x + 10;
        int pointInsideY = y + 10;
        int pointOutsideX = x - 30;
        int pointOutsideY = y - 30;

        assertTrue(cube1.doesCoordinatesAreInShapeBounds(pointInsideX, pointInsideY));
        assertFalse(cube1.doesCoordinatesAreInShapeBounds(pointOutsideX, pointInsideY));
        assertFalse(cube1.doesCoordinatesAreInShapeBounds(pointInsideX, pointOutsideY));
        assertFalse(cube1.doesCoordinatesAreInShapeBounds(pointOutsideX, pointOutsideY));
    }

    @Test
    void cube_moveTo_test() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape cube1 = shapeFactory.createShape(ShapeType.CUBE, x, y);

        int targetX = random.nextInt();
        int targetY = random.nextInt();

        cube1.moveTo(targetX, targetY);

        assertEquals(targetX - 25, cube1.getX());
        assertEquals(targetY - 25, cube1.getY());
    }

    @Test
    void cube_accept_test() {
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape cube1 = shapeFactory.createShape(ShapeType.CUBE, x, y);
        JSonVisitor jsonVisitor = new JSonVisitor();
        XMLVisitor xmlVisitor = new XMLVisitor();

        cube1.accept(jsonVisitor);
        cube1.accept(xmlVisitor);

        assertEquals("{\"type\":\"cube\",\"x\":" + cube1.getX() + ",\"y\":" + cube1.getY() + "}",
                jsonVisitor.getRepresentation());
        assertEquals("<shape><type>cube</type><x>" + cube1.getX() + "</x><y>" + cube1.getY() + "</y></shape>",
                xmlVisitor.getRepresentation());
    }
}
