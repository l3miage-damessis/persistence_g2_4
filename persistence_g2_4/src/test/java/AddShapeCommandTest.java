import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.JDrawingPanel;
import edu.uga.miage.m1.polygons.gui.command.AddShapeCommand;

class AddShapeCommandTest {
    private JDrawingPanel jDrawingPanelMock;
    private SimpleShape simpleShapeMock;
    private AddShapeCommand addShapeCommand;

    @BeforeEach
    void setUp() {

        jDrawingPanelMock = mock(JDrawingPanel.class);
        simpleShapeMock = mock(SimpleShape.class);
        addShapeCommand = new AddShapeCommand(jDrawingPanelMock, simpleShapeMock);

    }

    @Test
    void execute_shouldAddShapeToJDrawingPanel() {
        addShapeCommand.execute();

        verify(jDrawingPanelMock).addShape(simpleShapeMock);
    }

    @Test
    void undo_shouldRemoveLastShapeFromJDrawingPanel() {
        addShapeCommand.undo();
    
        verify(jDrawingPanelMock).removeLastShape();
    }

    @Test
    void redo_shouldReAddShapeToJDrawingPanel() {
        addShapeCommand.redo();
        
        verify(jDrawingPanelMock).reAddShape();
    }

}
