package fr.uga.miage.m1.g2_4.polygons.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.uga.miage.m1.g2_4.polygons.metier.persistence.ShapeXmlReader;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeFactory;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeType;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

class ShapeXmlReaderTest {

    Random random = new Random();
    ShapeXmlReader shapeXmlReader=new ShapeXmlReader();

    @Test
    void uploadShapesFromFile_shouldReadXmlFileAndCreateShapes_test_sucess(@TempDir Path tempDir) throws IOException {
        int x = random.nextInt();
        int y = random.nextInt();

        // Create a temporary file with JSON content
        File tempFile = new File(tempDir.toFile(), "shapes.xml");
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><shapes><shape><type>circle</type><x>" + x+ "</x><y>"+ y +"</y></shape><shape><type>square</type><x>"+ x +"</x><y>" + y +"</y></shape><shape><type>triangle</type><x>" + x +"</x><y>" + y + "</y></shape><shape><type>cube</type><x>" + x +"</x><y>"+ y + "</y></shape></shapes></root>";
        Files.write(tempFile.toPath(), xmlContent.getBytes());

        // Mock ShapeFactory to control the creation of shapes
        ShapeFactory shapeFactory = mock(ShapeFactory.class);
        SimpleShape circle1 = shapeFactory.createShape(ShapeType.CIRCLE, x, y);
        SimpleShape square1 = shapeFactory.createShape(ShapeType.SQUARE, x, y);
        SimpleShape triangle1 = shapeFactory.createShape(ShapeType.TRIANGLE, x, y);
        SimpleShape cube1 = shapeFactory.createShape(ShapeType.CUBE, x, y);
        when(shapeFactory.createShape(ShapeType.CIRCLE, x, y)).thenReturn(circle1);
        when(shapeFactory.createShape(ShapeType.SQUARE, x, y)).thenReturn(square1);
        when(shapeFactory.createShape(ShapeType.TRIANGLE, x, y)).thenReturn(triangle1);
        when(shapeFactory.createShape(ShapeType.CUBE, x, y)).thenReturn(cube1);

        ShapeXmlReader shapeXmlReader = new ShapeXmlReader();

        List<SimpleShape> shapes = shapeXmlReader.uploadShapesFromFile(tempFile);

        verify(shapeFactory, times(1)).createShape(ShapeType.CIRCLE, x, y);

        assertEquals(4, shapes.size());
        assertEquals(x, shapes.get(0).getX());
        assertEquals(y, shapes.get(0).getY());
    }

    @Test
    void uploadShapesFromFile_shouldThrowIllegalArgumentExceptionIfSourceFileExtensionIsIncorrect(
            @TempDir Path tempDir) {
        File incorrectExtensionFile = new File(tempDir.toFile(), "file.txt");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> shapeXmlReader.uploadShapesFromFile(incorrectExtensionFile));

        assertEquals("Le fichier source doit etre un fichier xml.", exception.getMessage());
    }
}
