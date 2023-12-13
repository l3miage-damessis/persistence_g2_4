package edu.uga.miage.m1.g2_4.polygons.metier.persistence;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

public class ShapeJsonWriter extends ShapeWriter {
    private transient Logger logger = Logger.getLogger(ShapeJsonWriter.class.getName());
    JSonVisitor jsonVisitor;

    public ShapeJsonWriter() {
        jsonVisitor = new JSonVisitor();
    }

    @Override
    public boolean saveShapesToFile(File destinationFile, List<SimpleShape> listOfShapes) throws IOException {
        try {
            StringBuilder jsonRepresentationOfShapesBuilder = new StringBuilder("{\"shapes\":[");
            for(SimpleShape simpleShape : listOfShapes){
                simpleShape.accept(jsonVisitor);
                jsonRepresentationOfShapesBuilder.append(jsonVisitor.getRepresentation());
            }
            Files.writeString(destinationFile.toPath(),
                    jsonRepresentationOfShapesBuilder.toString().replace("}{", "},{").concat("]}"),
                    StandardCharsets.UTF_8);
                    return true;
        } catch (IOException e) {
            logger.log(Level.INFO, "Chemin vers fichier destination invalide.",e);
            return false;
        }
    }
}