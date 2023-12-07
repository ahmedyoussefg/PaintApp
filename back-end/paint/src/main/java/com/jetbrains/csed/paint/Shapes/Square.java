package com.jetbrains.csed.paint.Shapes;

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
    @Override
    public Shape clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public void setSide(double side) {
        this.side = side;
    }
}