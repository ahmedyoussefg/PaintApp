package com.jetbrains.csed.paint;

import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

@RestController
public class Controller {
    ShapeFactory factory = new ShapeFactory();

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value= "/draw/")
    public String drawShape(ShapeDTO shape_data){
        Database db = Database.getInstance();
        Shape new_shape = factory.getShape(shape_data);
    }
}
