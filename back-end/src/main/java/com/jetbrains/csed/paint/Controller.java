package com.jetbrains.csed.paint;

import com.jetbrains.csed.paint.Shapes.Circle;
import com.jetbrains.csed.paint.Shapes.Shape;
import com.jetbrains.csed.paint.Shapes.ShapeDTO;
import com.jetbrains.csed.paint.Shapes.ShapeFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:8080" )
@CrossOrigin(origins = "http://192.168.1.110:8080" )
public class Controller {
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
