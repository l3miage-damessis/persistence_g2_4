package edu.uga.miage.m1.polygons.gui.command;

import edu.uga.miage.m1.polygons.gui.JDrawingPanel;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

public class AddShapeCommand implements Command {

    private JDrawingPanel jDrawingPanel;
    private SimpleShape simpleShape;

    public AddShapeCommand(JDrawingPanel jDrawingPanel, SimpleShape simpleShape) {
        this.jDrawingPanel = jDrawingPanel;
        this.simpleShape = simpleShape;
    }

    @Override
    public void execute() {
        if(this.simpleShape!=null){
            jDrawingPanel.addShape(simpleShape);
        }
 
    }

    @Override
    public void undo() {
        jDrawingPanel.removeLastShape();
    }

    @Override
    public void redo() {
        jDrawingPanel.reAddShape();
    }
}
