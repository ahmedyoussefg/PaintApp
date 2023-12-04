package com.jetbrains.csed.paint;

import java.awt.*;
import java.awt.geom.Point2D;

public interface Shape {

    Point2D.Double getCenter();
    void setCenter(Point2D.Double center);
    public String getFillColor();
    public void setFillColor(String fill_color);

//    public void setStrokeColor(String stroke_color);
//    public String getStrokeColor();
}

class Circle implements Shape {

    // default values
    private Point2D.Double center = new Point2D.Double(0,0);
    private String fill_color = "red";
//    private String stroke_color = "black";
    private double radius;

    Circle(){}
    Circle(Point2D.Double center, String fill_color, double radius){
        this.center=center;
        this.fill_color=fill_color;
//        this.stroke_color=stroke_color;
        this.radius=radius;
    }
    @Override
    public Point2D.Double getCenter() {
        return this.center;
    }

    @Override
    public void setCenter(Point2D.Double center) {
        this.center = center;
    }

    @Override
    public String getFillColor() {
        return this.fill_color;
    }

    @Override
    public void setFillColor(String fill_color) {
        this.fill_color=fill_color;
    }

//    @Override
//    public void setStrokeColor(String stroke_color) {
//        this.stroke_color=stroke_color;
//    }
//
//    @Override
//    public String getStrokeColor() {
//        return this.stroke_color;
//    }

    // Circle-specific

    public double getRadius()
    {
        return this.radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }

}

