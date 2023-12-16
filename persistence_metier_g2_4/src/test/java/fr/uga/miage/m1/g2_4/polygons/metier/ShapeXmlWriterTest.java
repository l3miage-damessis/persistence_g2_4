package fr.uga.miage.m1.g2_4.polygons.metier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.uga.miage.m1.g2_4.polygons.metier.persistence.ShapeXmlWriter;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeFactory;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeType;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

class ShapeXmlWriterTest {

    Random random = new Random();
    ShapeFactory shapeFactory = ShapeFactory.getShapeFactory();
    ShapeXmlWriter shapeXmlWriter = new ShapeXmlWriter();

    @Test
    void saveShapesToFile_shouldWriteXmlToFileSuccesfully(@TempDir Path tempDir) throws IOException {
        int x = random.nextInt();
        int y = random.nextInt();
        File tempFile = new File(tempDir.toFile(), "output.xml");

        List<SimpleShape> listOfShapes = new ArrayList<>();
        SimpleShape circle1 = shapeFactory.createShape(ShapeType.CIRCLE, x, y);
        SimpleShape square1 = shapeFactory.createShape(ShapeType.SQUARE, x, y);
        SimpleShape triangle1 = shapeFactory.createShape(ShapeType.TRIANGLE, x, y);
        SimpleShape cube1 = shapeFactory.createShape(ShapeType.CUBE, x, y);
        listOfShapes.addAll(Arrays.asList(circle1, square1, triangle1, cube1));

        ShapeXmlWriter shapeXmlWriter = new ShapeXmlWriter();

        shapeXmlWriter.saveShapesToFile(tempFile, listOfShapes);

        assertTrue(tempFile.exists());

        int expectedX = x - 25;
        int expectedY = y - 25;
        String expectedXmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><shapes><shape><type>circle</type><x>" + expectedX+ "</x><y>"+ expectedY +"</y></shape><shape><type>square</type><x>"+ expectedX +"</x><y>" + expectedY +"</y></shape><shape><type>triangle</type><x>" + expectedX +"</x><y>" + expectedY + "</y></shape><shape><type>cube</type><x>" + expectedX +"</x><y>"+ expectedY + "</y></shape></shapes></root>";
        String actualXmlContent = new String(Files.readAllBytes(tempFile.toPath()));

        assertEquals(expectedXmlContent, actualXmlContent);
    }

    @Test
    void saveShapesToFile_shouldThrowIllegalArgumentExceptionIfDestinationFileExtensionIsIncorrect(@TempDir Path tempDir) {
        File correctExtensionFile = new File(tempDir.toFile(), "file.txt");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> shapeXmlWriter.saveShapesToFile(correctExtensionFile, null));

        assertEquals("Le fichier destination doit etre un fichier xml.", exception.getMessage());
    }

    @Test
    void saveShapesToFile_shouldThrowIllegalArgumentExceptionIfListOfShapesIsEmpty(@TempDir Path tempDir) {
        File correctExtensionFile = new File(tempDir.toFile(), "file.xml");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> shapeXmlWriter.saveShapesToFile(correctExtensionFile, null));

        assertEquals("La liste des shapes a écrire dans le fichier pointent sur null", exception.getMessage());
    }

    @Test
    void saveShapesToFile_shouldThrowIllegalArgumentExceptionIfListOfShapesIsNull(@TempDir Path tempDir) {
        File correctExtensionFile = new File(tempDir.toFile(), "file.xml");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> shapeXmlWriter.saveShapesToFile(correctExtensionFile, new ArrayList<>()));

        assertEquals("La liste des shapes a écrire dans le fichier est vide", exception.getMessage());
    }

    // @Test
    // void saveShapesToFile_shouldThrowIllegalArgumentExceptionIfIOExceptionOccursWhenWriting(@TempDir Path tempDir)
    //         throws IOException {
    //     Arrange
    //     File correctExtensionFile = new File(tempDir.toFile(), "file.xml");
    //     List<SimpleShape> listOfShapes = Collections.singletonList(mock(SimpleShape.class));
    //     Logger logger = mock(Logger.class);
    //     XMLVisitor xmlVisitor = mock(XMLVisitor.class);
    //     ShapeXmlWriter shapeXmlWriter = new ShapeXmlWriter();

    //     Mock IOException during file writing using PowerMockito
    //     mockStatic(Files.class);
    //     when(Files.writeString(any(Path.class), anyString(), any())).thenThrow(IOException.class);

    //     Act and Assert
    //     IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
    //             () -> shapeXmlWriter.saveShapesToFile(correctExtensionFile, listOfShapes));

    //     assertEquals("Erreur lors de l'écriture dans le fichier destination", exception.getMessage());

    //     Verify that logger was called
    //     verify(logger).log(eq(Level.INFO), anyString(), any(IOException.class));
    // }


    @Test
    void isValidFile_shouldReturnTrueForXmlFile() {
        // Arrange
        ShapeXmlWriter yourClass = new ShapeXmlWriter();
        Path jsonFilePath = Paths.get("example.xml");

        // Act
        boolean result = yourClass.isValidFile(jsonFilePath.toFile());

        // Assert
        assertTrue(result);
    }

    @Test
    void isValidFile_shouldReturnFalseForNonXmlFile() {
        // Arrange
        ShapeXmlWriter yourClass = new ShapeXmlWriter();
        Path nonJsonFilePath = Paths.get("example.txt");

        // Act
        boolean result = yourClass.isValidFile(nonJsonFilePath.toFile());

        // Assert
        assertFalse(result);
    }

    @Test
    void isValidFile_shouldReturnFalseForNullPath() {
        // Arrange
        ShapeXmlWriter yourClass = new ShapeXmlWriter();

        // Act
        boolean result = yourClass.isValidFile(null);

        // Assert
        assertFalse(result);
    }

}
