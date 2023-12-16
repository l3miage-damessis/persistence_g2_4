package fr.uga.miage.m1.g2_4.polygons.metier;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.uga.miage.m1.g2_4.polygons.metier.persistence.ShapeJsonWriter;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeFactory;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeType;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

class ShapeJsonWriterTest {

    Random random = new Random();
    ShapeFactory shapeFactory = ShapeFactory.getShapeFactory();
    ShapeJsonWriter shapeJsonWriter = new ShapeJsonWriter();

    @Test
    void saveShapesToFile_shouldWriteJsonToFileSuccesfully(@TempDir Path tempDir) throws IOException {
        int x = random.nextInt();
        int y = random.nextInt();
        // Create a temporary file for testing
        File tempFile = new File(tempDir.toFile(), "output.json");

        // Create a list of SimpleShapes for testing
        List<SimpleShape> listOfShapes = new ArrayList<>();
        SimpleShape circle1 = shapeFactory.createShape(ShapeType.CIRCLE, x, y);
        SimpleShape square1 = shapeFactory.createShape(ShapeType.SQUARE, x, y);
        SimpleShape triangle1 = shapeFactory.createShape(ShapeType.TRIANGLE, x, y);
        SimpleShape cube1 = shapeFactory.createShape(ShapeType.CUBE, x, y);
        listOfShapes.addAll(Arrays.asList(circle1,square1,triangle1,cube1));


        // Create an instance of ShapeJsonWriter
        ShapeJsonWriter shapeJsonWriter = new ShapeJsonWriter();

        // Call the method to save shapes to the temporary file
        shapeJsonWriter.saveShapesToFile(tempFile, listOfShapes);


        // Verify that the file has been created
        assertTrue(tempFile.exists());

        // Verify the content of the file (you may need to customize this based on your
        // actual shapes)
        int expectedX=x-25;
        int expectedY=y-25;
        String expectedJsonContent = "{\"shapes\":[{\"type\":\"circle\",\"x\":" + expectedX + ",\"y\":" + expectedY
        + "},{\"type\":\"square\",\"x\":" + expectedX + ",\"y\":" + expectedY + "},{\"type\":\"triangle\",\"x\":" + expectedX
        + ",\"y\":" + expectedY + "},{\"type\":\"cube\",\"x\":" + expectedX + ",\"y\":" + expectedY + "}]}";
        String actualJsonContent = new String(Files.readAllBytes(tempFile.toPath()));

        assertEquals(expectedJsonContent, actualJsonContent);
    }

    @Test
    void saveShapesToFile_shouldThrowIllegalArgumentExceptionIfDestinationFileExtensionIsIncorrect(@TempDir Path tempDir) {
        File correctExtensionFile = new File(tempDir.toFile(), "file.txt");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> shapeJsonWriter.saveShapesToFile(correctExtensionFile, null));

        assertEquals("Le fichier destination doit etre un fichier json.", exception.getMessage());
    }

   @Test
    void saveShapesToFile_shouldThrowIllegalArgumentExceptionIfListOfShapesIsEmpty(@TempDir Path tempDir) {
        File correctExtensionFile = new File(tempDir.toFile(), "file.json");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> shapeJsonWriter.saveShapesToFile(correctExtensionFile, null));

        assertEquals("La liste des shapes a écrire dans le fichier pointent sur null", exception.getMessage());
    }

    @Test
    void saveShapesToFile_shouldThrowIllegalArgumentExceptionIfListOfShapesIsNull(@TempDir Path tempDir) {
        File correctExtensionFile = new File(tempDir.toFile(), "file.json");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> shapeJsonWriter.saveShapesToFile(correctExtensionFile, new ArrayList<>()));

        assertEquals("La liste des shapes a écrire dans le fichier est vide", exception.getMessage());
    }

    @Test
    void isValidFile_shouldReturnTrueForJsonFile() {
        // Arrange
        ShapeJsonWriter shapeJsonWriter = new ShapeJsonWriter();
        Path jsonFilePath = Paths.get("example.json");

        // Act
        boolean result = shapeJsonWriter.isValidFile(jsonFilePath.toFile());

        // Assert
        assertTrue(result);
    }

    @Test
    void isValidFile_shouldReturnFalseForNonJsonFile() {
        // Arrange
        ShapeJsonWriter shapeJsonWriter = new ShapeJsonWriter();
        Path nonJsonFilePath = Paths.get("example.txt");

        // Act
        boolean result = shapeJsonWriter.isValidFile(nonJsonFilePath.toFile());

        // Assert
        assertFalse(result);
    }

    @Test
    void isValidFile_shouldReturnFalseForNullPath() {
        // Arrange
        ShapeJsonWriter shapeJsonWriter = new ShapeJsonWriter();

        // Act
        boolean result = shapeJsonWriter.isValidFile(null);

        // Assert
        assertFalse(result);
    }
}
