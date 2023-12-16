package edu.uga.miage.m1.polygons.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

public class JDrawingPanel extends JPanel {
    private transient List<SimpleShape> shapesOnPanel;
    private transient List<SimpleShape> removedShapes;

    public JDrawingPanel() {
        shapesOnPanel = new ArrayList<>();
        removedShapes = new ArrayList<>();
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refreshPanel();
            }
        });
    }

    public boolean cursorOnJDrawingPanel(int evtX, int evtY) {
        return this.contains(evtX, evtY);
    }

    public boolean isCursorOnAShape(int evtX, int evtY) {
        boolean isCursorOnAShape = false;
        for (SimpleShape SimpleShape : shapesOnPanel) {
            if (SimpleShape.doesCoordinatesAreInShapeBounds(evtX, evtY)) {
                isCursorOnAShape = true;
                break;
            }
        }
        return isCursorOnAShape;
    }

    public void selectShape(int evtX, int evtY) {
        for (SimpleShape simpleShape : shapesOnPanel) {
            if (simpleShape.doesCoordinatesAreInShapeBounds(evtX, evtY)) {
                simpleShape.setSelected(true);
                break;
            }
        }
    }

    public SimpleShape getSelectedShape() {
        SimpleShape selectedShape = null;
        for (SimpleShape SimpleShape : shapesOnPanel) {
            if (SimpleShape.isSelected()) {
                selectedShape = SimpleShape;
                break;
            }
        }
        return selectedShape;
    }

    public void unSelectShape() {
        for (SimpleShape SimpleShape : shapesOnPanel) {
            SimpleShape.setSelected(false);
        }
    }

    public void addShape(SimpleShape simpleShape) {
        if (simpleShape != null) {
            simpleShape.draw((Graphics2D) this.getGraphics());
            shapesOnPanel.add(simpleShape);
        }
    }

    public void reAddShape() {
        if (!removedShapes.isEmpty()) {
            SimpleShape shapeToReadd = removedShapes.get(removedShapes.size() - 1);
            shapesOnPanel.add(shapeToReadd);
            removedShapes.remove(shapeToReadd);
            shapeToReadd.draw((Graphics2D) this.getGraphics());
        }
    }

    public void removeLastShape() {
        if (!shapesOnPanel.isEmpty()) {
            SimpleShape shapeToRemove = shapesOnPanel.get(shapesOnPanel.size() - 1);
            shapesOnPanel.remove(shapeToRemove);
            removedShapes.add(shapeToRemove);
            paintComponents(this.getGraphics());
        }
    }

    public void moveShape(SimpleShape simpleShape, int finalPositionX, int finalPositionY) {
        simpleShape.moveTo(finalPositionX, finalPositionY);
        simpleShape.setSelected(false);
        paintComponents(this.getGraphics());
    }

    public void revertMoveShape(SimpleShape simpleShape, int initialPositionX, int initialPositionY) {
        simpleShape.moveTo(initialPositionX, initialPositionY);
        paintComponents(this.getGraphics());
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paint(g);
        for (SimpleShape SimpleShape : shapesOnPanel) {
            SimpleShape.draw((Graphics2D) g);
        }
    }

    public List<SimpleShape> getShapesOnPanel() {
        return shapesOnPanel;
    }

    public List<SimpleShape> getRemovedShapes() {
        return removedShapes;
    }

    public void setShapesOnPanel(List<SimpleShape> shapesOnPanel) {
        this.shapesOnPanel = shapesOnPanel;
    }

    public void refreshPanel(){
        paintComponents(getGraphics());
    }
}
