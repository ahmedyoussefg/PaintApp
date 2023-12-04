package com.jetbrains.csed.paint;

import java.awt.geom.Point2D;

public interface IShape {
    Point2D.Double getPosition();

    void setPosition(Point2D.Double position);

    public String getFill();

    public void setFill(String fill_color);

    public double getRotation();

    public void setRotation(double rotation);

    public void setStrokeColor(String stroke_color);

    public String getStrokeColor();

    public int getId();

    public void setId(int id);
}

