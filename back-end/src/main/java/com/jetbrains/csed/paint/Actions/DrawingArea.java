package com.jetbrains.csed.paint.Actions;

import com.jetbrains.csed.paint.Shapes.Shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DrawingArea {
    private HashMap<Integer, Shape> drawnShapes = new HashMap<>();

    public void setShapes(HashMap<Integer, Shape> shapes){
        this.drawnShapes=shapes;
    }

    public HashMap<Integer, Shape> getDrawnShapes() {
        return drawnShapes;
    }

    public DrawingMemento takeSnapshot(){
        return new DrawingMemento(this.drawnShapes);
    }
    public void restore(DrawingMemento memento) {
        this.drawnShapes=memento.getSavedShapes();
    }
    public static class DrawingMemento {
        private final HashMap<Integer, Shape> shapes;

        private DrawingMemento(HashMap<Integer, Shape> shapesToSave) {
            this.shapes = shapesToSave;
        }

        private HashMap<Integer, Shape> getSavedShapes() {
            return this.shapes;
        }
    }
}
