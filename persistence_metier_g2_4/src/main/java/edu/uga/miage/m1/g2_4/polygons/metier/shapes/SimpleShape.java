package edu.uga.miage.m1.g2_4.polygons.metier.shapes;

import java.awt.Graphics2D;

import edu.uga.miage.m1.g2_4.polygons.metier.persistence.Visitable;



/**
 * This interface defines the <tt>SimpleShape</tt> extension. This extension
 * is used to draw shapes.
 * 
 * @author <a href=
 *         "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
public abstract class SimpleShape implements Visitable {

    public abstract int getX();

    public abstract int getY();

    public abstract void draw(Graphics2D g2d);

    public abstract boolean isSelected();

    public abstract void setSelected(boolean selected);

    boolean doesXIsInShapeBounds(int x){
        return isCoordinateInShapeBounds(getX(),x);
    }

    boolean doesYIsInShapeBounds(int y){
        return isCoordinateInShapeBounds(getY(),y);
    }

    public boolean isCoordinateInShapeBounds(int shapeCoordinate, int targetCoordinate){
        int shapeBoundary = shapeCoordinate + 50;
        return (shapeBoundary > targetCoordinate && shapeCoordinate < targetCoordinate);
    }

    public boolean doesCoordinatesAreInShapeBounds(int x, int y){
        return doesXIsInShapeBounds(x) && doesYIsInShapeBounds(y);
    }

    public abstract void moveTo(int targetPositionX, int targetPositionY);


}