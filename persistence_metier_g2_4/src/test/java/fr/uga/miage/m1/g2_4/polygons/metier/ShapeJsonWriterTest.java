package fr.uga.miage.m1.g2_4.polygons.metier;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import edu.uga.miage.m1.g2_4.polygons.metier.persistence.ShapeJsonWriter;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeFactory;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeType;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

class ExportJsonTest {

    Random random = new Random();
    ShapeFactory shapeFactory=ShapeFactory.getShapeFactory();
    ShapeJsonWriter shapeJsonWriter=new ShapeJsonWriter();

    @Test
    void exportJson_saveShapesFromJsonFile_test_success(){
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape circle1=shapeFactory.createShape(ShapeType.CIRCLE, x, y);
        SimpleShape square1=shapeFactory.createShape(ShapeType.SQUARE, x, y);
        SimpleShape triangle1=shapeFactory.createShape(ShapeType.TRIANGLE, x, y);
        SimpleShape cube1=shapeFactory.createShape(ShapeType.CUBE, x, y);
        List<SimpleShape> listOfShapes=Arrays.asList(circle1,square1,triangle1,cube1);
        String exportTestFilePath = "./src/test/resources/edu/uga/miage/m1/g2_4/persistence_test_data";
        File exportTestFile = new File(exportTestFilePath);

        try {
            assertTrue(shapeJsonWriter.saveShapesToFile(exportTestFile, listOfShapes));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

     @Test
    void exportJson_saveShapesFromJsonFile_test_failure(){
        int x = random.nextInt();
        int y = random.nextInt();
        SimpleShape circle1=shapeFactory.createShape(ShapeType.CIRCLE, x, y);
        SimpleShape square1=shapeFactory.createShape(ShapeType.SQUARE, x, y);
        SimpleShape triangle1=shapeFactory.createShape(ShapeType.TRIANGLE, x, y);
        SimpleShape cube1=shapeFactory.createShape(ShapeType.CUBE, x, y);
        List<SimpleShape> listOfShapes=Arrays.asList(circle1,square1,triangle1,cube1);
        File exportTestFile = null;

        try {
            assertFalse(shapeJsonWriter.saveShapesToFile(exportTestFile, listOfShapes));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
