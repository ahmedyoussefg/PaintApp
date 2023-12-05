package com.jetbrains.csed.paint;

public class Square extends Shape {
    private double side;

    Square(){}

    Square(ShapeDTO data) {
        super(data);
        this.side=data.height;
    }

    public double getSide() {
        return this.side;
    }

    public void setSide(double side) {
        this.side = side;
    }
}
