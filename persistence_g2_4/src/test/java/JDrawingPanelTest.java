import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.JDrawingPanel;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

class JDrawingPanelTest {

    // JDrawingPanel drawingPanel = new JDrawingPanel();

    // @Test
    // void cursorOnJDrawingPanelTest() {
    //     drawingPanel = new JDrawingPanel();
    //     assertTrue(drawingPanel.cursorOnJDrawingPanel(50, 50));
    //     assertFalse(drawingPanel.cursorOnJDrawingPanel(200, 200));
    // }

    // @Test
    // void isCursorOnAShapeTest() {
    //     drawingPanel = new JDrawingPanel();
    //     drawingPanel.addShape(ShapeType.CIRCLE, 50, 50);
    //     assertTrue(drawingPanel.isCursorOnAShape(70, 70));
    //     assertFalse(drawingPanel.isCursorOnAShape(200, 200));
    // }

    // @Test
    // void selectShapeTest() {
    //     drawingPanel = new JDrawingPanel();
    //     drawingPanel.addShape(ShapeType.SQUARE, 50, 50);
    //     drawingPanel.addShape(ShapeType.CIRCLE, 100, 100);
    //     drawingPanel.selectShape(70, 70);

    //     SimpleShape selectedShape = drawingPanel.getSelectedShape();
    //     assertNotNull(selectedShape);
    //     assertTrue(selectedShape.isSelected());
    // }

    // @Test
    // void unSelectShapeTest() {
    //     drawingPanel = new JDrawingPanel();
    //     drawingPanel.addShape(ShapeType.TRIANGLE, 50, 50);
    //     drawingPanel.addShape(ShapeType.CIRCLE, 100, 100);

    //     drawingPanel.unSelectShape();

    //     for (SimpleShape shape : drawingPanel.getShapesOnPanel()) {
    //         assertFalse(shape.isSelected());
    //     }
    // }

    // @Test
    // void addShapeTest() {
    //     drawingPanel = new JDrawingPanel();
    //     drawingPanel.addShape(ShapeType.TRIANGLE, 50, 50);
    //     drawingPanel.addShape(ShapeType.CIRCLE, 100, 100);

    //     List<SimpleShape> shapesOnPanel = drawingPanel.getShapesOnPanel();
    //     assertEquals(2, shapesOnPanel.size());
    // }

    // @Test
    // void removeShapeTest() {
    //     drawingPanel = new JDrawingPanel();
    //     drawingPanel.addShape(ShapeType.SQUARE, 50, 50);
    //     SimpleShape square = drawingPanel.getShapesOnPanel().get(drawingPanel.getShapesOnPanel().size() - 1);

    //     boolean removed = drawingPanel.removeShape(square);

    //     assertTrue(removed);
    //     assertEquals(0, drawingPanel.getShapesOnPanel().size());
    // }

    // @Test
    // void moveShapeTest() {
    //     drawingPanel = new JDrawingPanel();
    //     drawingPanel.addShape(ShapeType.CIRCLE, 50, 50);
    //     SimpleShape circle = drawingPanel.getShapesOnPanel().get(drawingPanel.getShapesOnPanel().size() - 1);

    //     drawingPanel.moveShape(circle, 100, 100);

    //     assertEquals(100, circle.getX());
    //     assertEquals(100, circle.getY());
    //     assertFalse(circle.isSelected());
    // }

    // @Test
    // void revertMoveShapeTest() {

    //     drawingPanel.addShape(ShapeType.SQUARE, 50, 50);
    //     SimpleShape square = drawingPanel.getShapesOnPanel().get(drawingPanel.getShapesOnPanel().size() - 1);
    //     drawingPanel.moveShape(square, 100, 100);
    //     drawingPanel.revertMoveShape(square, 50, 50);

    //     assertEquals(50, square.getX());
    //     assertEquals(50, square.getY());
    // }

    // @Test
    // void paintComponentsTest() {

    //     drawingPanel.addShape(ShapeType.TRIANGLE, 50, 50);
    //    SimpleShape triangle = drawingPanel.getShapesOnPanel().get(drawingPanel.getShapesOnPanel().size() - 1);

    //     Graphics g = new JPanel().getGraphics();
    //     drawingPanel.paintComponents(g);

    //     verify(triangle,times(1)).draw((Graphics2D)g);
    // }
}
