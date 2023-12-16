import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.JDrawingPanel;
import edu.uga.miage.m1.polygons.gui.command.MoveShapeCommand;

class MoveShapeCommandTest {
    private SimpleShape simpleShapeMock;
    private JDrawingPanel jDrawingPanelMock;
    private MoveShapeCommand moveShapeCommand;

    @BeforeEach
    void setUp() {
        simpleShapeMock = mock(SimpleShape.class);
        jDrawingPanelMock = mock(JDrawingPanel.class);

        when(simpleShapeMock.getX()).thenReturn(10);
        when(simpleShapeMock.getY()).thenReturn(20); 

        moveShapeCommand = new MoveShapeCommand(simpleShapeMock, jDrawingPanelMock);
    }

    @Test
    void execute_shouldMoveShapeInJDrawingPanel() {
        moveShapeCommand.setFinalPosition(50, 60); // Modify according to your requirements

        moveShapeCommand.execute();

        verify(jDrawingPanelMock).moveShape(simpleShapeMock, 50, 60);
    }

    @Test
    void undo_shouldRevertMoveShapeInJDrawingPanel() {
        moveShapeCommand.undo();

        verify(jDrawingPanelMock).revertMoveShape(simpleShapeMock, 35, 45); // Adjust based on your initial position
    }

    @Test
    void redo_shouldExecuteMoveShapeInJDrawingPanel() {
        moveShapeCommand.setFinalPosition(50, 60); // Modify according to your requirements

        moveShapeCommand.redo();

        verify(jDrawingPanelMock).moveShape(simpleShapeMock, 50, 60);
    }
}
