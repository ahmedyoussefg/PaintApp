package com.jetbrains.csed.paint.Shapes;

public class LineSegment extends Shape{

    double[] points;
    LineSegment(){}

    LineSegment(ShapeDTO data){
        super(data);
        this.points=data.points;
    }

    public void setPoints(double[] points) {
        this.points = points;
    }

    public double[] getPoints() {
        return this.points;
    }
}
