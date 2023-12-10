package com.jetbrains.csed.paint.Shapes;

public class ShapeDTO {
    // for line segments
    public double [] points;
    //for all shapes
    public double x;
    public double y;
    public double radius; // for circle
    public double scaleX;
    public double scaleY;

    //for ellipse
    public double radiusX;
    public double radiusY;
    public int sides; // for triangle
    // for rectangle, square
    public double width;
    public double height;
    // for all
    public double rotation;
    public double strokeWidth;
    public String fill;
    public String strokeColor;
    public double offsetX;
    public double offsetY;
    public String shapeType;
    public int id;
}
