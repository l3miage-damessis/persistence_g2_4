package edu.uga.miage.m1.g2_4.polygons.metier.persistence;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

public abstract class ShapeWriter {

    public ShapeWriter(){

    }

    public abstract boolean saveShapesToFile(File destinationFile, List<SimpleShape> listOfShapes) throws IOException;

}
