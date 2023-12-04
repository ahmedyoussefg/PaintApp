package com.jetbrains.csed.paint;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ShapeFactory {
    public Shape getShape(String shapeType, Point2D.Double center, ArrayList<Double> dimensions, String fillColor) {
        if (shapeType == null) {
            return null;
        }
        else if (shapeType.equalsIgnoreCase("circle")) {
            if (dimensions.size()!=1)
                return null;
            return new Circle(center, fillColor, dimensions.get(0));
        }
        return null;
    }
}
