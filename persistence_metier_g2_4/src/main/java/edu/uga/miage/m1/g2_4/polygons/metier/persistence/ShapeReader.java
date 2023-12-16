package edu.uga.miage.m1.g2_4.polygons.metier.persistence;

import java.io.File;
import java.util.List;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeFactory;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeType;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

public abstract class ShapeReader {
    private ShapeFactory shapeFactory;
    
    protected ShapeReader(){
        this.shapeFactory = ShapeFactory.getShapeFactory();
    }
    

    public abstract List<SimpleShape> uploadShapesFromFile(File sourceFile);


    public SimpleShape convertDataToShape(String shapeType, int x, int y) {
        SimpleShape simpleShape = null;
        switch (shapeType) {
            case "circle":
                simpleShape = shapeFactory.createShape(ShapeType.CIRCLE, x, y);
                break;
            case "square":
                simpleShape = shapeFactory.createShape(ShapeType.SQUARE, x, y);
                break;
            case "triangle":
                simpleShape = shapeFactory.createShape(ShapeType.TRIANGLE, x, y);
                break;
            case "cube":
                simpleShape = shapeFactory.createShape(ShapeType.CUBE, x, y);
                break;
            default:
                break;
        }
        return simpleShape;
    } 

    public abstract boolean isValidFile(File file);
}
