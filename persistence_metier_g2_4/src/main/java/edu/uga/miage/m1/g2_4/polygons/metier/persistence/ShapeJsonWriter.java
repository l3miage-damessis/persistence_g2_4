package edu.uga.miage.m1.g2_4.polygons.metier.persistence;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

public class ShapeJsonWriter extends ShapeWriter {
    private Logger logger = Logger.getLogger(ShapeJsonWriter.class.getName());
    JSonVisitor jsonVisitor;

    public ShapeJsonWriter() {
        jsonVisitor = new JSonVisitor();
    }

    @Override
    public void saveShapesToFile(File destinationFile, List<SimpleShape> listOfShapes) throws IOException {
        if (!isValidFile(destinationFile)) {
            throw new IllegalArgumentException("Le fichier destination doit etre un fichier json.");
        }
        if (listOfShapes == null) {
            throw new IllegalArgumentException("La liste des shapes a écrire dans le fichier pointent sur null");
        }
        if (listOfShapes.isEmpty()) {
            throw new IllegalArgumentException("La liste des shapes a écrire dans le fichier est vide");
        }

        StringBuilder jsonRepresentationOfShapesBuilder = new StringBuilder("{\"shapes\":[");
        for (SimpleShape simpleShape : listOfShapes) {
            simpleShape.accept(jsonVisitor);
            jsonRepresentationOfShapesBuilder.append(jsonVisitor.getRepresentation());
        }
        try {
            Files.writeString(destinationFile.toPath(),
                    jsonRepresentationOfShapesBuilder.toString().replace("}{", "},{").concat("]}"),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.log(Level.INFO, "Chemin vers fichier destination invalide.", e);
            throw new IllegalArgumentException("Erreur lors de l'écriture dans le fichier destination");
        }

    }

    @Override
    public boolean isValidFile(File file){
        if(file==null){
            return false;
        }
        String fileName = file.toPath().getFileName().toString();
        return fileName.toLowerCase().endsWith(".json");
    }
}