package com.jetbrains.csed.paint;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.jetbrains.csed.paint.Services.XmlService;
import com.jetbrains.csed.paint.Shapes.Shape;
import com.jetbrains.csed.paint.Shapes.ShapeDTO;
import com.jetbrains.csed.paint.Shapes.ShapeFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.jetbrains.csed.paint.Shapes.Circle;


@RestController
//@CrossOrigin(origins = "http://localhost:8080" )
@CrossOrigin(origins = "http://192.168.1.110:8080" )
public class Controller {
    private XmlService xmlService = new XmlService();
    ShapeFactory factory = new ShapeFactory();

    @PostMapping(value= "/draw")
    public ShapeDTO drawShape(@RequestBody ShapeDTO shape_data){
        Database db = Database.getInstance();
        Shape new_shape = factory.getShape(shape_data);
        db.draw(new_shape);
        new_shape.setId(db.getId_counter()-1);
        DEBUG();
        return new_shape.shapeToDTO();
    }

    @PostMapping(value="/copy")
    public ShapeDTO copyShape(@RequestBody ShapeDTO shape_data) throws CloneNotSupportedException {
        Database db = Database.getInstance();
        Shape copied = db.copy(db.getShapeByID(shape_data.id));

        //DEBUG
        DEBUG();
        return copied.shapeToDTO();
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
        db.update(new_shape);
    }

    @PostMapping(value="/saveJSON")
    public ShapeDTO[] saveJSON() {
        Database db = Database.getInstance();
        ShapeDTO[] ShapesData = getShapesDTOArray(db.getDrawnShapes());
        return ShapesData;
    }
    // @PostMapping(value="/loadJSON")
    // public HashMap<Integer, Shape> loadJSON(@RequestBody HashMap<Integer, Shape> ShapeData) {
    //     Database db = Database.getInstance();
    //     db.setDrawnShapes(ShapeData);
    //     return db.getDrawnShapes();
    // }
    ShapeDTO[] getShapesDTOArray(HashMap<Integer, Shape> ShapesData){
        int NoShapes = ShapesData.size();
        ShapeDTO[] ShapesDTO = new ShapeDTO[NoShapes];
        for (int i = 0; i < NoShapes; i++) {
            ShapesDTO[i] = ShapesData.get(i+1).shapeToDTO();
        }
        return ShapesDTO;
    }

    @GetMapping("/saveXML")
    public String getShapesXml() throws JsonProcessingException {
        Database db = Database.getInstance();
        ShapeDTO[] ShapesData = getShapesDTOArray(db.getDrawnShapes());
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
