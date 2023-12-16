import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Circle;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeFactory;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeType;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Square;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.Triangle;
import edu.uga.miage.m1.polygons.gui.JDrawingPanel;

class JDrawingPanelTest {
    Random random = new Random();
    private JDrawingPanel jDrawingPanel;

    @BeforeEach
    void setUp() {
        jDrawingPanel = new JDrawingPanel();
    }

    @Test
    void cursorOnJDrawingPanel_shouldReturnTrueWhenCursorIsInsidePanel() {
        jDrawingPanel.setSize(100, 100);
        jDrawingPanel.setLocation(0, 0);
        MouseEvent realEvent = new MouseEvent(jDrawingPanel, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, 50,
                50, 0, false);

        boolean result = jDrawingPanel.cursorOnJDrawingPanel(realEvent.getX(),
                realEvent.getY());

        assertTrue(result);
    }

    @Test
    void cursorOnJDrawingPanel_shouldReturnFalseWhenCursorIsNotInsidePanel() {
        jDrawingPanel.setSize(100, 100);
        jDrawingPanel.setLocation(0, 0);
        MouseEvent realEvent = new MouseEvent(jDrawingPanel, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, 150,
                150, 0, false);

        boolean result = jDrawingPanel.cursorOnJDrawingPanel(realEvent.getX(),
                realEvent.getY());

        assertFalse(result);
    }

    @Test
    void isCursorOnAShape_shouldReturnFalseWhenCursorIsNotOnAShape() {
        int x = random.nextInt();
        int y = random.nextInt();
        Circle circle1 = mock(Circle.class);
        when(circle1.doesCoordinatesAreInShapeBounds(anyInt(), anyInt())).thenReturn(false);
        jDrawingPanel.addShape(circle1);

        MouseEvent mockEvent = mock(MouseEvent.class);
        when(mockEvent.getX()).thenReturn(x);
        when(mockEvent.getY()).thenReturn(y);

        boolean result = jDrawingPanel.isCursorOnAShape(mockEvent.getX(), mockEvent.getY());

        assertFalse(result);
    }

    @Test
    void isCursorOnAShape_shouldReturnTrueWhenCursorIsOnAShape() {
        int x = random.nextInt();
        int y = random.nextInt();
        Circle circle1 = mock(Circle.class);
        when(circle1.doesCoordinatesAreInShapeBounds(anyInt(), anyInt())).thenReturn(true);
        jDrawingPanel.addShape(circle1);

        MouseEvent mockEvent = mock(MouseEvent.class);
        when(mockEvent.getX()).thenReturn(x);
        when(mockEvent.getY()).thenReturn(y);

        boolean result = jDrawingPanel.isCursorOnAShape(mockEvent.getX(), mockEvent.getY());

        assertTrue(result);
    }

    @Test
    void selectShape_shouldSelectShapeWhenCoordinatesAreInShapeBounds() {
        int x = random.nextInt();
        int y = random.nextInt();
        Square square1 = mock(Square.class);
        jDrawingPanel.getShapesOnPanel().add(square1);

        when(square1.doesCoordinatesAreInShapeBounds(anyInt(), anyInt())).thenReturn(true);

        jDrawingPanel.selectShape(x, y);
        verify(square1).setSelected(true);
    }

    @Test
    void selectShape_shouldNotSelectShapeWhenCoordinatesAreNotInShapeBounds() {
        int x = random.nextInt();
        int y = random.nextInt();
        Triangle triangle1 = mock(Triangle.class);
        jDrawingPanel.getShapesOnPanel().add(triangle1);

        when(triangle1.doesCoordinatesAreInShapeBounds(anyInt(), anyInt())).thenReturn(false);

        jDrawingPanel.selectShape(x, y);
        verify(triangle1, times(0)).setSelected(true);
    }

    @Test
    void getSelectedShape_shouldReturnSelectedShapeWhenOneIsSelected() {
        JDrawingPanel jDrawingPanel = new JDrawingPanel();

        SimpleShape mockShape = mock(SimpleShape.class);

        jDrawingPanel.getShapesOnPanel().add(mockShape);
        when(mockShape.isSelected()).thenReturn(true);

        SimpleShape selectedShape = jDrawingPanel.getSelectedShape();

        verify(mockShape).isSelected();

        assertEquals(mockShape, selectedShape, "The selected shape should be the mockShape");
    }

    @Test
    void getSelectedShape_shouldReturnNullWhenNoShapeIsSelected() {
        SimpleShape selectedShape = jDrawingPanel.getSelectedShape();

        assertNull(selectedShape, "No shape should be selected");
    }

    @Test
    void getSelectedShape_shouldReturnNullWhenNoShapesOnPanel() {
        SimpleShape selectedShape = jDrawingPanel.getSelectedShape();

        assertNull(selectedShape);
    }

    @Test
    void unSelectShape_shouldSetSelectedToFalseForAllShapes() {
        SimpleShape mockShape1 = mock(SimpleShape.class);
        SimpleShape mockShape2 = mock(SimpleShape.class);
        jDrawingPanel.getShapesOnPanel().add(mockShape1);
        jDrawingPanel.getShapesOnPanel().add(mockShape2);
        when(mockShape1.isSelected()).thenReturn(true);
        when(mockShape2.isSelected()).thenReturn(true);

        jDrawingPanel.unSelectShape();

        verify(mockShape1).setSelected(false);
        verify(mockShape2).setSelected(false);
    }

    @Test
    void addShape_shouldAddShapeToPanel() {
        SimpleShape mockShape = mock(SimpleShape.class);

        jDrawingPanel.addShape(mockShape);

        List<SimpleShape> shapesOnPanel = jDrawingPanel.getShapesOnPanel();
        assertEquals(1, shapesOnPanel.size());
        assertTrue(shapesOnPanel.contains(mockShape));

        verify(mockShape).draw(any());
    }

    @Test
    void addShape_shouldNotAddNullShapeToPanel() {

        jDrawingPanel.addShape(null);

        List<SimpleShape> shapesOnPanel = jDrawingPanel.getShapesOnPanel();
        assertEquals(0, shapesOnPanel.size());
    }

}
