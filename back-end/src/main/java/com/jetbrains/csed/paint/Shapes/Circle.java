package com.jetbrains.csed.paint.Shapes;
public class Circle extends Shape {
    private double radius;
    Circle() {}
    Circle(ShapeDTO data) {
        super(data);
        this.radius=data.radius;
    }
    public double getRadius()
    {
        return this.radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public Shape clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
