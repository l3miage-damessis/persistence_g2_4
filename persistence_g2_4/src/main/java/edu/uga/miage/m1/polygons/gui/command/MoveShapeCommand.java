package edu.uga.miage.m1.polygons.gui.command;

import edu.uga.miage.m1.polygons.gui.JDrawingPanel;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

public class MoveShapeCommand implements Command {

    private int initialPositionX;
    private int initialPositionY;

    private SimpleShape simpleShape;

    private int finalPositionX;
    private int finalPositionY;

    private JDrawingPanel jDrawingPanel;

    public MoveShapeCommand(SimpleShape simpleShape, JDrawingPanel jDrawingPanel) {
        this.initialPositionX = simpleShape.getX() + 25;
        this.initialPositionY = simpleShape.getY() + 25;
        this.simpleShape = simpleShape;
        this.jDrawingPanel = jDrawingPanel;
    }

    public void setFinalPosition(int x, int y) {
        this.finalPositionX = x;
        this.finalPositionY = y;
    }

    @Override
    public void execute() {
        jDrawingPanel.moveShape(simpleShape, finalPositionX, finalPositionY);
    }

    @Override
    public void undo() {
        jDrawingPanel.revertMoveShape(simpleShape, initialPositionX, initialPositionY);
    }

    @Override
    public void redo() {
        execute();
    }

}
