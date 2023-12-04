package com.jetbrains.csed.paint;

public class Rectangle extends Shape {
    private double width, length;
    Rectangle(){}
    Rectangle(double width, double length) {
        this.width=width;
        this.length=length;
    }
    public double getWidth() {
        return this.width;
    }
    public double getLength() {
        return this.length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
