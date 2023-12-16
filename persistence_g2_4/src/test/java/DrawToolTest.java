import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.DrawTool;
import edu.uga.miage.m1.polygons.gui.command.Command;

class DrawToolTest {

    private DrawTool drawTool;
    private Command commandMock;

    @BeforeEach
    void setUp() {
        drawTool = new DrawTool();
        commandMock = mock(Command.class);
    }

    @Test
    void executeCommand_shouldExecuteAndAddToHistory() {
        drawTool.executeCommand(commandMock);

        verify(commandMock).execute();
        assertEquals(1, drawTool.getCommandsHistory().size());
    }

    @Test
    void executeCommand_shouldNotExecuteNorAddToHistoryBecauseCommandIsNull() {
        drawTool.executeCommand(null);

        verify(commandMock,times(0)).execute();
        assertEquals(0, drawTool.getCommandsHistory().size());
    }

    @Test
    void executeMultipleCommand_shouldExecuteAndAddToHistory() {
        List<Command> multipleCommands = new ArrayList<>();
        multipleCommands.add(commandMock);

        drawTool.executeMultipleCommand(multipleCommands);

        verify(commandMock).execute();
        assertEquals(1, drawTool.getCommandsHistory().size());
    }

    @Test
    void undoCommand_shouldUndoAndMoveToCanceledHistory() {
        drawTool.executeCommand(commandMock);

        drawTool.undoCommand();

        verify(commandMock).undo();
        assertEquals(0, drawTool.getCommandsHistory().size());
        assertEquals(1, drawTool.getCanceledCommandsHistory().size());
    }

    @Test
    void undoCommand_shouldNotUndoNorMoveToCanceledHistoryBecauseNoCommandToUndo() {
        drawTool.undoCommand();

        assertEquals(0, drawTool.getCommandsHistory().size());
        assertEquals(0, drawTool.getCanceledCommandsHistory().size());
    }

    @Test
    void redoCommand_shouldRedoAndMoveToCommandsHistory() {
        drawTool.executeCommand(commandMock);
        drawTool.undoCommand();

        drawTool.redoCommand();

        verify(commandMock).redo();
        assertEquals(1, drawTool.getCommandsHistory().size());
        assertEquals(0, drawTool.getCanceledCommandsHistory().size());
    }

    @Test
    void redoCommand_shouldNotRedoNorMoveToCommandsHistoryBecauseNoCommandToRedo() {
        drawTool.redoCommand();

        assertTrue(drawTool.getCommandsHistory().isEmpty());
        assertTrue(drawTool.getCanceledCommandsHistory().isEmpty());
    }

    @Test
    void undoCommand_shouldNotFailIfHistoryIsEmpty() {
        drawTool.undoCommand();

        assertTrue(drawTool.getCommandsHistory().isEmpty());
        assertTrue(drawTool.getCanceledCommandsHistory().isEmpty());
    }

    @Test
    void redoCommand_shouldNotFailIfCanceledHistoryIsEmpty() {
        drawTool.redoCommand();

        assertTrue(drawTool.getCommandsHistory().isEmpty());
        assertTrue(drawTool.getCanceledCommandsHistory().isEmpty());
    }
}
