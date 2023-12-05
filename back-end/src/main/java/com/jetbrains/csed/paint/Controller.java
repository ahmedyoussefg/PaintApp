package com.jetbrains.csed.paint;

import com.jetbrains.csed.paint.Shapes.Shape;
import com.jetbrains.csed.paint.Shapes.ShapeDTO;
import com.jetbrains.csed.paint.Shapes.ShapeFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    ShapeFactory factory = new ShapeFactory();

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value= "/draw/")
    public ShapeDTO drawShape(ShapeDTO shape_data){
        Database db = Database.getInstance();
        Shape new_shape = factory.getShape(shape_data);
        db.draw(new_shape);
        return shape_data;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value="/copy/")
    public ShapeDTO copyShape(ShapeDTO shape_data) throws CloneNotSupportedException {
        Database db = Database.getInstance();
        Shape copied =db.copy(db.getShapeByID(shape_data.id));
        return copied.shapeToDTO();
    }
}
