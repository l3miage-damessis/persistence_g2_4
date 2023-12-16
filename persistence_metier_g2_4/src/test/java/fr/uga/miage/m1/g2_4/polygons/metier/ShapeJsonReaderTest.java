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
import org.mockito.Mock;

import edu.uga.miage.m1.g2_4.polygons.metier.persistence.ShapeJsonReader;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeFactory;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeType;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

class ShapeJsonReaderTest {
    Random random = new Random();
    ShapeJsonReader shapeJsonReader = new ShapeJsonReader();

    @Test
    void uploadShapesFromFile_shouldReadJsonFileAndCreateShapes_test_sucess(@TempDir Path tempDir) throws IOException {
        int x = random.nextInt();
        int y = random.nextInt();

        // Create a temporary file with JSON content
        File tempFile = new File(tempDir.toFile(), "shapes.json");
        String jsonContent = "{\"shapes\":[{\"type\":\"circle\",\"x\":" + x + ",\"y\":" + y
                + "},{\"type\":\"square\",\"x\":" + x + ",\"y\":" + y + "},{\"type\":\"triangle\",\"x\":" + x
                + ",\"y\":" + y + "},{\"type\":\"cube\",\"x\":" + x + ",\"y\":" + y + "}]}";
        Files.write(tempFile.toPath(), jsonContent.getBytes());

        // Mock ShapeFactory to control the creation of shapes
        ShapeFactory shapeFactory = mock(ShapeFactory.class);
        SimpleShape circle1 = shapeFactory.createShape(ShapeType.CIRCLE, x, y);
        SimpleShape square1 = shapeFactory.createShape(ShapeType.SQUARE, x, y);
        SimpleShape triangle1 = shapeFactory.createShape(ShapeType.TRIANGLE, x, y);
        SimpleShape cube1 = shapeFactory.createShape(ShapeType.CUBE, x, y);
        when(shapeFactory.createShape(ShapeType.CIRCLE, 10, 20)).thenReturn(circle1);
        when(shapeFactory.createShape(ShapeType.SQUARE, 10, 20)).thenReturn(square1);
        when(shapeFactory.createShape(ShapeType.TRIANGLE, 10, 20)).thenReturn(triangle1);
        when(shapeFactory.createShape(ShapeType.CUBE, 10, 20)).thenReturn(cube1);

        ShapeJsonReader shapeJsonReader = new ShapeJsonReader();

        List<SimpleShape> shapes = shapeJsonReader.uploadShapesFromFile(tempFile);

        verify(shapeFactory, times(1)).createShape(ShapeType.CIRCLE, x, y);

        assertEquals(4, shapes.size());
        assertEquals(x, shapes.get(0).getX());
        assertEquals(y, shapes.get(0).getY());
    }

    @Test
    void uploadShapesFromFile_shouldThrowIllegalArgumentExceptionIfSourceFileExtensionIsIncorrect(
            @TempDir Path tempDir) {
        File correctExtensionFile = new File(tempDir.toFile(), "file.txt");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> shapeJsonReader.uploadShapesFromFile(correctExtensionFile));

        assertEquals("Le fichier source doit etre un fichier json.", exception.getMessage());
    }

    @Test
    void uploadShapesFromFile_shouldThrowIllegalArgumentExceptionIfSourceFileIsNull(
            @TempDir Path tempDir) {
        File correctExtensionFile = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> shapeJsonReader.uploadShapesFromFile(correctExtensionFile));

        assertEquals("Le fichier source doit etre un fichier json.", exception.getMessage());
    }

    // @Test
    // void uploadShapesFromFile_shouldHandleException(@TempDir Path tempDir) throws
    // IOException {
    // Logger logger=mock(Logger.class);
    // int x = random.nextInt();
    // int y = random.nextInt();
    // // Create a temporary file with invalid content
    // File tempFile = new File(tempDir.toFile(), "shapes.json");
    // String invalidJsonContent = "invalid json content";
    // Files.write(tempFile.toPath(), invalidJsonContent.getBytes());

    // // Mock ShapeFactory to avoid actual shape creation (since the JSON is
    // invalid)
    // ShapeFactory shapeFactory = mock(ShapeFactory.class);
    // when(shapeFactory.createShape(ShapeType.CIRCLE, x, y)).thenThrow(new
    // RuntimeException("Test exception"));

    // // Create ShapeJsonReader with the mocked ShapeFactory
    // ShapeJsonReader shapeJsonReader = new ShapeJsonReader();

    // shapeJsonReader.uploadShapesFromFile(tempFile);
    // // Assert that an exception is thrown
    // verify(logger).log(eq(Level.INFO), any(String.class), any(Throwable.class));
    // }

}
