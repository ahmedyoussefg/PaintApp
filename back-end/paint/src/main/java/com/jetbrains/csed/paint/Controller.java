package com.jetbrains.csed.paint;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.jetbrains.csed.paint.Services.XmlService;
import com.jetbrains.csed.paint.Shapes.Shape;
import com.jetbrains.csed.paint.Shapes.ShapeDTO;
import com.jetbrains.csed.paint.Shapes.ShapeFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.jetbrains.csed.paint.Shapes.Circle;


@RestController
@CrossOrigin(origins = "http://localhost:8080" )
//@CrossOrigin(origins = "http://192.168.0.161:8080" )
public class Controller {
    private XmlService xmlService = new XmlService();
    ShapeFactory factory = new ShapeFactory();

    @PostMapping(value= "/draw")
    public void drawShape(@RequestBody ShapeDTO shape_data){
        Database db = Database.getInstance();
        Shape new_shape = factory.getShape(shape_data);
        db.draw(new_shape);
        DEBUG();
    }

    @PostMapping(value="/copy")
    public ShapeDTO copyShape(@RequestBody String[] shapes_old_copy) throws CloneNotSupportedException {
        Database db = Database.getInstance();
        String shape_id = shapes_old_copy[0];
        String copied_id=shapes_old_copy[1];

        Shape copied = db.copy(db.getShapeByID(Integer.parseInt(shape_id)), Integer.parseInt(copied_id));
        //DEBUG
        DEBUG();
        return copied.shapeToDTO();
    }
    @GetMapping(value="/clear")
    public void clearCanvas() {
        Database db = Database.getInstance();
        db.clear();
    }

    @PostMapping(value="/undo")
    public ArrayList<ShapeDTO> undo(){
        Database db = Database.getInstance();
        db.undo();
        return db.getDrawnShapesDTOs();
    }

    @PostMapping(value="/redo")
    public ArrayList<ShapeDTO> redo(){
        Database db = Database.getInstance();
        db.redo();
        return db.getDrawnShapesDTOs();
    }
    @PostMapping(value="/update")
    public void updateShape(@RequestBody ShapeDTO data){
        Database db = Database.getInstance();
        Shape new_shape = factory.getShape(data);
        System.out.println("CALLED UPDATE");
        db.update(new_shape);
        DEBUG();
    }

    @PostMapping(value="/delete")
    public void deleteShape(@RequestBody String deleted_id) {
        Database db = Database.getInstance();
        System.out.println("TODELETE: "+deleted_id);
        db.delete(Integer.parseInt(deleted_id));
    }
    @PostMapping(value="/saveJSON")
    public ArrayList<ShapeDTO> saveJSON() {
        Database db = Database.getInstance();
        return db.getDrawnShapesDTOs();
    }
    // @PostMapping(value="/loadJSON")
    // public HashMap<Integer, Shape> loadJSON(@RequestBody HashMap<Integer, Shape> ShapeData) {
    //     Database db = Database.getInstance();
    //     db.setDrawnShapes(ShapeData);
    //     return db.getDrawnShapes();
    // }

    @GetMapping("/saveXML")
    public String getShapesXml() throws JsonProcessingException {
        Database db = Database.getInstance();
        ArrayList<ShapeDTO> ShapesData = db.getDrawnShapesDTOs();
        return xmlService.convertToXml(ShapesData);
    }

    @PostMapping("/loadXML")
    public ArrayList<ShapeDTO> getShapesFromXml(@RequestBody String xml) throws IOException {
        System.out.println(xml);
        Database.cleanDatabase();
        ArrayList<ShapeDTO> Shapes = xmlService.convertXmlToShapes(xml);

        for (ShapeDTO Shape: Shapes) {
            drawShape(Shape);
        }
        return Shapes;
    }


    @GetMapping("/clean")
    public void cleanCanvas() {
        Database.cleanDatabase();
    }
    
    // to see saved shapes
    public void DEBUG(){
        Database db = Database.getInstance();
        HashMap<Integer, Shape> test = db.getDrawnShapes();
        System.out.println("TEST");
        for (Map.Entry<Integer, Shape> set : test.entrySet()) {
            int saved_id = set.getKey();
            Shape saved_shape = set.getValue();
            System.out.println("SAVED ID = "+ saved_id);
            System.out.printf("x: %f, y: %f, id: %d", saved_shape.getPosition().getX(), saved_shape.getPosition().getY(), saved_shape.getId() );

            System.out.println("_________________________________________");
        }
        System.out.println("####################");
    }
}
