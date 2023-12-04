package com.jetbrains.csed.paint;

import java.awt.geom.Point2D;

public class Circle extends Shape {
    private double radius;
    Circle() {}
    Circle(Point2D.Double center, String fill_color, String stroke_color, double radius) {
        this.setPosition(center);
        this.setFill(fill_color);
        this.setStrokeColor(stroke_color);
        this.radius = radius;
    }
    public double getDimensionsX()
    {
        return this.radius;
    }
    public void setDimensionsX(double radius) {
        this.radius = radius;
    }
}
