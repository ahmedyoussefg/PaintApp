package com.jetbrains.csed.paint;

public class ShapeDTO {
    // for line segments
    public double [] points;
    //for all shapes
    public double x;
    public double y;
    public double radius; //for circle
    //for ellipse
    public double radiusX;
    public double radiusY;
    // for rectangle, square
    public double width;
    public double height;
    public double rotation;
    public double strokeWidth;
    public String fill;
    public String strokeColor;
    public String commandType;
    public String shapeType;
}
