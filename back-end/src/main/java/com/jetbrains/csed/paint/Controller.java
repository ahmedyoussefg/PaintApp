package com.jetbrains.csed.paint;

import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

@RestController
public class Controller {
    Stack<Shape> drawn = new Stack<>();
    ShapeFactory factory = new ShapeFactory();

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value= "/draw/")
    public String drawShape(@RequestParam("type") String type, @RequestParam("x") double xCenter, @RequestParam("y") double yCenter, @RequestParam("fill") String fill, @RequestParam("dimensions") double dim){
        ArrayList<Double> dimensions = new ArrayList<>();
        dimensions.add(dim);
        Shape shape=this.factory.getShape(type, new Point2D.Double(xCenter, yCenter), dimensions, fill);
        this.drawn.add(shape);
        return shape.toString();
    }
}
