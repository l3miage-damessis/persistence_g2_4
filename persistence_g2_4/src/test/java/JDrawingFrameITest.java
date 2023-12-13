import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeType;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;

//when running this test make sure to not touch your mousse to keep focus on the Test frame otherwise some test can failed
class JDrawingFrameITest {

    private JDrawingFrame drawingFrame;

    @BeforeEach
    public void setUp() {
        // Create the drawing frame in the event dispatch thread
        try {
            SwingUtilities.invokeAndWait(() -> {
                drawingFrame = new edu.uga.miage.m1.polygons.gui.JDrawingFrame("Integration Test Frame");
                drawingFrame.pack();
                drawingFrame.setLocationRelativeTo(null); // Center the frame on the screen
                drawingFrame.setVisible(true);
            });
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }

        waitActionToBeCompleted();
    }

    @Test
    void testAddingSquareShapes() {
        assertNotNull(drawingFrame);

        simulateSelectionOfSpecificShapeButtonInToolBar(ShapeType.SQUARE);

        simulateMousePressAndRelease(drawingFrame.getJDrawingPanel());

        waitActionToBeCompleted();

        verifyShapeIsAtItsInitalPosition_x_25_y_25();
    }

    @Test
    void testAddingCirleShapes() {
        assertNotNull(drawingFrame);

        simulateSelectionOfSpecificShapeButtonInToolBar(ShapeType.CIRCLE);

        simulateMousePressAndRelease(drawingFrame.getJDrawingPanel());

        waitActionToBeCompleted();

        verifyShapeIsAtItsInitalPosition_x_25_y_25();
    }

    @Test
    void testAddingTriangleShapes() {
        assertNotNull(drawingFrame);

        simulateSelectionOfSpecificShapeButtonInToolBar(ShapeType.TRIANGLE);

        simulateMousePressAndRelease(drawingFrame.getJDrawingPanel());

        waitActionToBeCompleted();

        verifyShapeIsAtItsInitalPosition_x_25_y_25();
    }

    @Test
    void testAddingCubeShapes() {
        assertNotNull(drawingFrame);

        simulateSelectionOfSpecificShapeButtonInToolBar(ShapeType.CUBE);

        simulateMousePressAndRelease(drawingFrame.getJDrawingPanel());

        waitActionToBeCompleted();

        verifyShapeIsAtItsInitalPosition_x_25_y_25();
    }

    @Test
    void testMovingShapes() {
        assertNotNull(drawingFrame);

        simulateSelectionOfSpecificShapeButtonInToolBar(ShapeType.SQUARE);

        simulateMousePressAndRelease(drawingFrame.getJDrawingPanel());

        waitActionToBeCompleted();

        verifyShapeIsAtItsInitalPosition_x_25_y_25();

        simulateMousePressAndReleaseWithDraging(drawingFrame.getJDrawingPanel());

        waitActionToBeCompleted();

        verifyShapeMoveTo_x_125_y_125();
    }

    @Test
    void testUndoingAddShape() {
        assertNotNull(drawingFrame);

        simulateSelectionOfSpecificShapeButtonInToolBar(ShapeType.CIRCLE);

        simulateMousePressAndRelease(drawingFrame.getJDrawingPanel());

        waitActionToBeCompleted();

        verifyShapeIsAtItsInitalPosition_x_25_y_25();

        simulateCtrlPlusZ();

        waitActionToBeCompleted();

        verifyNoShapeOnTheDrawingPanel();
    }

    @Test
    void testUndoingMovingShapes() {
        assertNotNull(drawingFrame);

        simulateSelectionOfSpecificShapeButtonInToolBar(ShapeType.SQUARE);

        simulateMousePressAndRelease(drawingFrame.getJDrawingPanel());

        waitActionToBeCompleted();

        verifyShapeIsAtItsInitalPosition_x_25_y_25();

        simulateMousePressAndReleaseWithDraging(drawingFrame.getJDrawingPanel());

        waitActionToBeCompleted();
        verifyShapeMoveTo_x_125_y_125();

        simulateCtrlPlusZ();

        waitActionToBeCompleted();

        verifyShapeIsAtItsInitalPosition_x_25_y_25();
    }

    private void verifyShapeMoveTo_x_125_y_125() {
        assertEquals(1, drawingFrame.getJDrawingPanel().getShapesOnPanel().size());
        assertEquals(125, drawingFrame.getJDrawingPanel().getShapesOnPanel().get(0).getX());
        assertEquals(125, drawingFrame.getJDrawingPanel().getShapesOnPanel().get(0).getY());
    }

    @Test
    void testRedoingAddShape() {
        assertNotNull(drawingFrame);

        simulateSelectionOfSpecificShapeButtonInToolBar(ShapeType.TRIANGLE);
        simulateMousePressAndRelease(drawingFrame.getJDrawingPanel());

        waitActionToBeCompleted();

        verifyShapeIsAtItsInitalPosition_x_25_y_25();

        simulateCtrlPlusZ();

        waitActionToBeCompleted();

        verifyNoShapeOnTheDrawingPanel();

        simulateCtrlPlusY();

        waitActionToBeCompleted();

        verifyShapeIsAtItsInitalPosition_x_25_y_25();
    }

    @Test
    void testRedoingMoveShape() {
        assertNotNull(drawingFrame);

        simulateSelectionOfSpecificShapeButtonInToolBar(ShapeType.TRIANGLE);
        simulateMousePressAndRelease(drawingFrame.getJDrawingPanel());

        waitActionToBeCompleted();

        // Verify that the shape was added
        verifyShapeIsAtItsInitalPosition_x_25_y_25();

        simulateMousePressAndReleaseWithDraging(drawingFrame.getJDrawingPanel());

        waitActionToBeCompleted();

        verifyShapeMoveTo_x_125_y_125();

        simulateCtrlPlusZ();

        waitActionToBeCompleted();

        verifyShapeIsAtItsInitalPosition_x_25_y_25();

        simulateCtrlPlusY();

        waitActionToBeCompleted();

        verifyShapeMoveTo_x_125_y_125();
    }

    private void verifyShapeIsAtItsInitalPosition_x_25_y_25() {
        assertEquals(1, drawingFrame.getJDrawingPanel().getShapesOnPanel().size());
        assertEquals(25, drawingFrame.getJDrawingPanel().getShapesOnPanel().get(0).getX());
        assertEquals(25, drawingFrame.getJDrawingPanel().getShapesOnPanel().get(0).getY());
    }

    private void verifyNoShapeOnTheDrawingPanel() {
        assertEquals(0, drawingFrame.getJDrawingPanel().getShapesOnPanel().size());
    }

    private void simulateCtrlPlusZ() {
        simulateKeyPress(KeyEvent.VK_Z, KeyEvent.VK_CONTROL);
    }

    private void simulateSelectionOfSpecificShapeButtonInToolBar(ShapeType shapeType) {
        JButton shapeTypeButton = drawingFrame.getButtons().get(shapeType);
        assertNotNull(shapeTypeButton);
        SwingUtilities.invokeLater(() -> shapeTypeButton.doClick());
        assertNotNull(shapeTypeButton);
    }

    private void simulateCtrlPlusY() {
        simulateKeyPress(KeyEvent.VK_Y, KeyEvent.VK_CONTROL);
    }

    private void waitActionToBeCompleted() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Utility method to simulate mouse press and release on a component
    private void simulateMousePressAndRelease(Component component) {
        Point point = component.getLocationOnScreen();
        Robot robot;
        try {
            robot = new Robot();
            robot.mouseMove(point.x + 50, point.y + 50);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    // Utility method to simulate key press
    private void simulateKeyPress(int keyCode, int modifiers) {
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(modifiers);
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
            robot.keyRelease(modifiers);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    // Utility method to simulate mouse press and release on a component
    private void simulateMousePressAndReleaseWithDraging(Component component) {
        Point point = component.getLocationOnScreen();
        Robot robot;
        try {
            robot = new Robot();
            robot.mouseMove(point.x + 50, point.y + 50);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseMove(point.x + 150, point.y + 150);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
