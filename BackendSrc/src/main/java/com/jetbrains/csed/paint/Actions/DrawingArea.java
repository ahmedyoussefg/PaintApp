package com.jetbrains.csed.paint.Actions;

import com.jetbrains.csed.paint.Shapes.Shape;

import java.util.HashMap;

public class DrawingArea {
    private HashMap<Integer, Shape> drawnShapes = new HashMap<>();

    public void setShapes(HashMap<Integer, Shape> shapes){
        this.drawnShapes = new HashMap<>(shapes);
    }

    public HashMap<Integer, Shape> getDrawnShapes() {
        return drawnShapes;
    }

    public DrawingMemento takeSnapshot(){
        System.out.println("taking snapshot..");
        return new DrawingMemento(new HashMap<>(this.drawnShapes));
    }
    public void restoreSnapshot(DrawingMemento memento) {
        System.out.println("restoring snapshot");
        this.drawnShapes=new HashMap<>(memento.getSavedShapes());
    }
    public static class DrawingMemento {
        private final HashMap<Integer, Shape> shapes;

        private DrawingMemento(HashMap<Integer, Shape> shapesToSave) {
            this.shapes = shapesToSave;
            System.out.println("saving snapshot");
        }

        private HashMap<Integer, Shape> getSavedShapes() {
            System.out.println("returning saved shapes");
            return this.shapes;
        }
    }
}
