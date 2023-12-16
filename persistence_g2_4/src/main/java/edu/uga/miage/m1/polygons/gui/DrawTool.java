package edu.uga.miage.m1.polygons.gui;

import java.util.ArrayList;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.command.Command;

public class DrawTool {
    private List<Command> commandsHistory;
    private List<Command> canceledCommandsHistory;

    public List<Command> getCommandsHistory() {
        return commandsHistory;
    }

    public List<Command> getCanceledCommandsHistory() {
        return canceledCommandsHistory;
    }

    public DrawTool() {
        commandsHistory = new ArrayList<>();
        canceledCommandsHistory = new ArrayList<>();
    }

    public void executeCommand(Command command) {
        if (command != null) {
            command.execute();
            commandsHistory.add(command);
        }
    }

    public void executeMultipleCommand(List<Command> multipleCommands) {
        for (Command command : multipleCommands) {
            this.executeCommand(command);
        }
    }

    public void undoCommand() {
        if (!commandsHistory.isEmpty()) {
            Command commandToUndo = commandsHistory.get(commandsHistory.size() - 1);
            commandToUndo.undo();
            commandsHistory.remove(commandToUndo);
            canceledCommandsHistory.add(commandToUndo);
        }
    }

    public void redoCommand() {
        if (!canceledCommandsHistory.isEmpty()) {
            Command commandToRedo = canceledCommandsHistory.get(canceledCommandsHistory.size() - 1);
            commandToRedo.redo();
            canceledCommandsHistory.remove(commandToRedo);
            commandsHistory.add(commandToRedo);
        }

    }

}
