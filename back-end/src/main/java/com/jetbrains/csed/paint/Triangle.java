package com.jetbrains.csed.paint;

public class Triangle extends Shape {
    private int numSides;
    private double triangleRadius; // radius of a circle surrounding triangle

    Triangle(){}

    Triangle(ShapeDTO data){
        super(data);
        this.numSides=data.sides;
        this.triangleRadius=data.radius;
    }
    public double getTriangleRadius() {
        return this.triangleRadius;
    }

    public void setTriangleRadius(double triangleRadius) {
        this.triangleRadius = triangleRadius;
    }

    public int getNumberOfSides() {
        return this.numSides;
    }

    public void setNumOfSides(int sides) {
        this.numSides=sides;
    }

}
