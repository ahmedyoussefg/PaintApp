package com.jetbrains.csed.paint;

import java.awt.geom.Point2D;

public class Ellipse extends Shape{
    Point2D.Double radius;
    Ellipse(){}

    Ellipse(Point2D.Double radius){
        this.radius=radius;
    }

    public void setRadius(Point2D.Double radius) {
        this.radius = radius;
    }

    public Point2D.Double getRadius() {
        return this.radius;
    }
}
