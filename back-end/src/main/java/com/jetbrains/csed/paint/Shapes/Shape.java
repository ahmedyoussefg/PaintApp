package com.jetbrains.csed.paint.Shapes;

import java.awt.geom.Point2D;

public class Shape implements Cloneable {
    // default values
    private int id;
    private Point2D.Double position = new Point2D.Double(0,0);
    private String fill_color = "red";
    private String stroke_color = "black";
    private double rotation;
    private double stroke_width;
    Shape(){}

    Shape(ShapeDTO data) {
        this.id=data.id;
        this.fill_color=data.fill;
        this.position=new Point2D.Double(data.x, data.y);
        this.rotation=data.rotation;
        this.stroke_color=data.strokeColor;
        this.stroke_width=data.strokeWidth;
    }

    public void setStrokeWidth(double stroke_width) {
        this.stroke_width=stroke_width;
    }
    public double getStrokeWidth() {
        return this.stroke_width;
    }

    public double getRotation(){
        return this.rotation;
    }
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
    public int getId(){
        return this.id;
    }
    public void setId(int id) {
        this.id=id;
    }
    public Point2D.Double getPosition() {
        return this.position;
    }

    public void setPosition(Point2D.Double position) {
        this.position = position;
    }

    public String getFill() {
        return this.fill_color;
    }

    public void setFill(String fill_color) {
        this.fill_color=fill_color;
    }

    public void setStrokeColor(String stroke_color) {
        this.stroke_color=stroke_color;
    }

    public String getStrokeColor() {
        return this.stroke_color;
    }

    @Override
    public Shape clone() throws CloneNotSupportedException {
        return (Shape) super.clone();
    }

    public ShapeDTO shapeToDTO(){
        // logic
        return new ShapeDTO();
    }
}

