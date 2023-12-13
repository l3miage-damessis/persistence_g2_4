package edu.uga.miage.m1.g2_4.polygons.metier.persistence;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

public class ShapeXmlWriter extends ShapeWriter{
    private transient Logger logger = Logger.getLogger(ShapeXmlWriter.class.getName());
    XMLVisitor xmlVisitor;

    public ShapeXmlWriter() {
        xmlVisitor = new XMLVisitor();
    }

    public boolean saveShapesToFile(File destinationFile, List<SimpleShape> listOfShapes) throws IOException {
        try {
            StringBuilder xmlRepresentationOfShapesBuilder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><shapes>");
            for(SimpleShape simpleShape : listOfShapes){
                simpleShape.accept(xmlVisitor);
                xmlRepresentationOfShapesBuilder.append(xmlVisitor.getRepresentation());
            }
            xmlRepresentationOfShapesBuilder.append("</shapes></root>");
            Files.writeString(destinationFile.toPath(),
                    xmlRepresentationOfShapesBuilder.toString(),
                    StandardCharsets.UTF_8);
                    return true;
        } catch (IOException e) {
            logger.log(Level.INFO, "Chemin vers fichier destination invalide.",e);
            return false;
        }
    }
}
