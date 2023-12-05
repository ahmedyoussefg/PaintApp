package com.jetbrains.csed.paint;

public class Rectangle extends Shape {
    private double width, height;
    Rectangle(){}
    Rectangle(ShapeDTO data) {
        super(data);
        this.width=data.width;
        this.height=data.height;
    }
    public double getWidth() {
        return this.width;
    }
    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
