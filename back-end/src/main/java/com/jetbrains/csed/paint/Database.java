package com.jetbrains.csed.paint;

import java.util.HashMap;
import java.util.Stack;

public class Database {
    private static Database database;

    private Stack<Shape> undo_stack = new Stack<>();
    private Stack<Shape> redo_stack = new Stack<>();
    private int id_counter = 1;
    private HashMap<Integer, Shape> drawnShapes;
    private Database(){}
    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public HashMap<Integer, Shape> getDrawnShapes() {
        return this.drawnShapes;
    }

    public void setDrawnShapes(HashMap<Integer, Shape> drawnShapes) {
        this.drawnShapes = drawnShapes;
    }

    public void resetRedoStack() {
        while (!this.redo_stack.empty()){
            this.redo_stack.pop();
        }
    }

    public void draw(Shape new_shape) {
        this.drawnShapes.put(this.id_counter++, new_shape);
        this.undo_stack.push(new_shape);
        this.resetRedoStack();
    }
}
