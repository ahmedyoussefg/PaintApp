package com.jetbrains.csed.paint;

import com.jetbrains.csed.paint.Shapes.Shape;
import com.jetbrains.csed.paint.Shapes.ShapeDTO;
import com.jetbrains.csed.paint.Shapes.ShapeFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class Controller {
    ShapeFactory factory = new ShapeFactory();

    @PostMapping(value= "/draw")
    public ShapeDTO drawShape(ShapeDTO shape_data){
        Database db = Database.getInstance();
        Shape new_shape = factory.getShape(shape_data);
        db.draw(new_shape);
        return shape_data;
    }

    @PostMapping(value="/copy")
    public ShapeDTO copyShape(ShapeDTO shape_data) throws CloneNotSupportedException {
        Database db = Database.getInstance();
        Shape copied =db.copy(db.getShapeByID(shape_data.id));
        return copied.shapeToDTO();
    }
}
