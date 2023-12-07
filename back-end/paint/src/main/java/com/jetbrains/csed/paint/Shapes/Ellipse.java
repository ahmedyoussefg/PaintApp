package com.jetbrains.csed.paint.Shapes;

import java.awt.geom.Point2D;

public class Ellipse extends Shape{
    Point2D.Double radius;
    Ellipse(){}

    Ellipse(ShapeDTO data){
        super(data);
        this.radius = new Point2D.Double(data.radiusX, data.radiusY);
    }
    @Override
    public Shape clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public void setRadius(Point2D.Double radius) {
        this.radius = radius;
    }

    public Point2D.Double getRadius() {
        return this.radius;
    }
}
