package com.jetbrains.csed.paint.Shapes;

import javax.sound.sampled.Line;
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
        Shape cloned = (Shape) super.clone();
        cloned.id = this.id;
        cloned.position = new Point2D.Double(this.position.getX(), this.position.getY());
        return cloned;
    }

    public ShapeDTO shapeToDTO() {
        ShapeDTO dto = new ShapeDTO();
        dto.id = this.id;
        dto.x = this.position.getX();
        dto.y = this.position.getY();
        dto.rotation = this.rotation;
        dto.strokeWidth = this.stroke_width;
        dto.fill = this.fill_color;
        dto.strokeColor = this.stroke_color;
        if (this instanceof Circle) {
            Circle circle = (Circle) this;
            dto.radius = circle.getRadius();
            dto.shapeType = "circle";
        } else if (this instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) this;
            dto.width = rectangle.getWidth();
            dto.height = rectangle.getHeight();
            dto.shapeType = "rectangle";
        } else if (this instanceof Ellipse) {
            Ellipse ellipse = (Ellipse) this;
            dto.radiusX= ellipse.getRadius().getX();
            dto.radiusY=ellipse.getRadius().getY();
            dto.shapeType="ellipse";
        }
        else if (this instanceof Triangle) {
            Triangle triangle = (Triangle) this;
            dto.sides = triangle.getNumberOfSides();
            dto.radiusX = triangle.getTriangleRadius();
            dto.shapeType="triangle";
        }
        else if (this instanceof LineSegment){
            LineSegment line = (LineSegment) this;
            dto.points = line.points;
            dto.shapeType="line_segment";
        }
        else if (this instanceof Square){
            Square square = (Square) this;
            dto.height =square.getSide();
            dto.width=square.getSide();
            dto.shapeType="square";
        }
        return dto;
    }
}

