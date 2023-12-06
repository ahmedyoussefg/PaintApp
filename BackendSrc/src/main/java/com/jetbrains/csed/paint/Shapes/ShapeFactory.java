package com.jetbrains.csed.paint.Shapes;

import com.jetbrains.csed.paint.Shapes.*;
import com.jetbrains.csed.paint.Shapes.Rectangle;

public class ShapeFactory {
    public com.jetbrains.csed.paint.Shapes.Shape getShape(ShapeDTO data) {
        if (data == null) {
            return null;
        }
        else if (data.shapeType.equalsIgnoreCase("circle")) {
            return new Circle(data);
        }
        else if (data.shapeType.equalsIgnoreCase("rectangle")) {
            return new Rectangle(data);
        }
        else if (data.shapeType.equalsIgnoreCase("triangle")) {
            return new Triangle(data);
        }
        else if (data.shapeType.equalsIgnoreCase("line_segment")) {
            return new LineSegment(data);
        }
        else if (data.shapeType.equalsIgnoreCase("ellipse")) {
            return new Ellipse(data);
        }
        else if (data.shapeType.equalsIgnoreCase("square")) {
            return new Square(data);
        }
        return null;
    }
}
