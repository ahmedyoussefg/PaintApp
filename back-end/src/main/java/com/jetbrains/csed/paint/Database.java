package com.jetbrains.csed.paint;

import com.jetbrains.csed.paint.Actions.DrawingArea;
import com.jetbrains.csed.paint.Shapes.Shape;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Stack;

public class Database {
    private static Database database;

    private DrawingArea drawingArea;
    private Stack<DrawingArea.DrawingMemento> undo_stack;
    private Stack<DrawingArea.DrawingMemento> redo_stack;
    private int id_counter = 1;
    private Database(){
        undo_stack = new Stack<>();
        redo_stack = new Stack<>();
        drawingArea = new DrawingArea();
    }
    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public HashMap<Integer, Shape> getDrawnShapes() {
        return drawingArea.getDrawnShapes();
    }

    public void resetRedoStack() {
        while (!this.redo_stack.empty()){
            this.redo_stack.pop();
        }
    }

    public void draw(Shape new_shape) {
        HashMap<Integer, Shape> curr= drawingArea.getDrawnShapes();
        curr.put(this.id_counter++, new_shape);
        drawingArea.setShapes(curr);
        this.undo_stack.push(drawingArea.takeSnapshot());
        this.resetRedoStack();
    }

    public Shape copy(Shape old_version) throws CloneNotSupportedException {
        Shape cloned = old_version.clone();
        cloned.setId(id_counter);
        // make small offset in x and y of position
        Point2D.Double new_point = new Point2D.Double(cloned.getPosition().getX()+0.6,cloned.getPosition().getY()+0.6);
        cloned.setPosition(new_point);
        draw(cloned);
        return cloned;
    }

    public void redo() {
        if (!redo_stack.empty()) {
            DrawingArea.DrawingMemento top = redo_stack.pop();
            undo_stack.push(top);
            drawingArea.restore(top);
        }
    }
    public void undo(){
        if(undo_stack.empty())
            return;
        DrawingArea.DrawingMemento top= undo_stack.pop();
        redo_stack.push(top);
        drawingArea.restore(top);
    }
    public Shape getShapeByID(int id) {
        return drawingArea.getDrawnShapes().getOrDefault(id, null);
    }
}
