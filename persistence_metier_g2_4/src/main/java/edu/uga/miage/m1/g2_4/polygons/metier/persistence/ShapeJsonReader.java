package edu.uga.miage.m1.g2_4.polygons.metier.persistence;

import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;

public class ShapeJsonReader extends ShapeReader{
    private Logger logger = Logger.getLogger(ShapeJsonReader.class.getName());

    public ShapeJsonReader() {
        super();
    }

    @Override
    public List<SimpleShape> uploadShapesFromFile(File sourceFile) {
        if (!isValidFile(sourceFile)) {
            throw new IllegalArgumentException("Le fichier source doit etre un fichier json.");
        }
        List<SimpleShape> shapesOnFile = new ArrayList<>();
        try {
            String jsonString = Files.readString(sourceFile.toPath());
            try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
                JsonObject jsonObject = reader.readObject();
                JsonArray arrayOfJsonRepresentationOfShapes = jsonObject.getJsonArray("shapes");
                String shapeType;
                int x;
                int y;
                SimpleShape simpleShape = null;
                for (JsonValue jsonRepresentationOfShape : arrayOfJsonRepresentationOfShapes) {
                    shapeType = ((JsonObject) jsonRepresentationOfShape).getString("type");
                    x = ((JsonObject) jsonRepresentationOfShape).getInt("x") + 25;
                    y = ((JsonObject) jsonRepresentationOfShape).getInt("y") + 25;
                    simpleShape = convertDataToShape(shapeType, x, y);
                    if (simpleShape != null) {
                        shapesOnFile.add(simpleShape);
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "Echec tentative de recuperation des shapes", e);
        }
        return shapesOnFile;
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