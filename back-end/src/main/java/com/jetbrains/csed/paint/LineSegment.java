package com.jetbrains.csed.paint;

import java.awt.geom.Point2D;

public class LineSegment {

    private Point2D.Double start;
    private Point2D.Double end;
    LineSegment(){}

    LineSegment(Point2D.Double start, Point2D.Double end){
        this.start=start;
        this.end=end;
    }

    public Point2D.Double getStart() {
        return this.start;
    }

    public void setStart(Point2D.Double start) {
        this.start = start;
    }

    public Point2D.Double getEnd() {
        return this.end;
    }

    public void setEnd(Point2D.Double end) {
        this.end = end;
    }
}
