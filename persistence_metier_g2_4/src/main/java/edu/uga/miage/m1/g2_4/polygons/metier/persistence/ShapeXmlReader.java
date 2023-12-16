package edu.uga.miage.m1.g2_4.polygons.metier.persistence;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

public class ShapeXmlReader extends ShapeReader{
    private  Logger logger = Logger.getLogger(ShapeXmlReader.class.getName());

    public ShapeXmlReader() {
        super();
    }

    @Override
    public List<SimpleShape> uploadShapesFromFile(File sourceFile) {
        if (!isValidFile(sourceFile)) {
            throw new IllegalArgumentException("Le fichier source doit etre un fichier xml.");
        }
        List<SimpleShape> shapesOnFile = new ArrayList<>();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            try {
                dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                dbFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

                dBuilder = dbFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                logger.log(Level.INFO, "Echec tentative de recuperation des shapes", e);
            }
            Document doc = null;
            try {
                if (dBuilder != null) {
                    doc = dBuilder.parse(sourceFile);
                }
            } catch (SAXException | IOException e) {
                logger.log(Level.INFO, "Echec tentative de recuperation des shapes", e);
            }
            if (doc != null) {
                doc.getDocumentElement().normalize();
                NodeList shapeList = doc.getElementsByTagName("shape");
                Element shapeElement;
                SimpleShape simpleShape = null;
                String shapeType;
                int x;
                int y;
                for (int i = 0; i < shapeList.getLength(); i++) {
                    shapeElement = (Element) shapeList.item(i);
                    shapeType = shapeElement.getElementsByTagName("type").item(0).getTextContent();
                    x = Integer
                            .parseInt(shapeElement.getElementsByTagName("x").item(0).getTextContent()) +
                            25;
                    y = Integer
                            .parseInt(shapeElement.getElementsByTagName("y").item(0).getTextContent()) +
                            25;
                    simpleShape = convertDataToShape(shapeType, x, y);
                    if (simpleShape != null) {
                        shapesOnFile.add(simpleShape);
                    }
                }
            }
        return shapesOnFile;
    }
   
    @Override
    public boolean isValidFile(File file){
        if(file==null){
            return false;
        }
        String fileName = file.toPath().getFileName().toString();
        return fileName.toLowerCase().endsWith(".xml");
    }

}
