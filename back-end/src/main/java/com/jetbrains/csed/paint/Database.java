package com.jetbrains.csed.paint;

import com.jetbrains.csed.paint.Actions.DrawingArea;
import com.jetbrains.csed.paint.Shapes.LineSegment;
import com.jetbrains.csed.paint.Shapes.Shape;
import com.jetbrains.csed.paint.Shapes.ShapeDTO;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Database {
    private static Database database;

    private DrawingArea drawingArea;
    private Stack<DrawingArea.DrawingMemento> undo_stack;
    private Stack<DrawingArea.DrawingMemento> redo_stack;
    private Database(){
        undo_stack = new Stack<>();
        redo_stack = new Stack<>();
        drawingArea = new DrawingArea();
        this.undo_stack.push(this.drawingArea.takeSnapshot());
    }
    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public HashMap<Integer, Shape> getDrawnShapes() {
        return this.drawingArea.getDrawnShapes();
    }
    public static void cleanDatabase(){
        database = new Database();
    }

    public void resetRedoStack() {
        while (!this.redo_stack.empty()){
            this.redo_stack.pop();
        }
    }

    public void clear(){
        HashMap<Integer, Shape> curr=new HashMap<>();
        drawingArea.setShapes(curr);
        this.undo_stack.push(this.drawingArea.takeSnapshot());
    }
    public void draw(Shape new_shape) {
        HashMap<Integer, Shape> curr= drawingArea.getDrawnShapes();
        curr.put(new_shape.getId(), new_shape);
        drawingArea.setShapes(curr);
        System.out.println("Drawing");
        this.undo_stack.push(this.drawingArea.takeSnapshot());
        System.out.println("TAKEN SNAPSHOT COMPLETED");
        this.resetRedoStack();
    }

    public Shape copy(Shape old_version, int copied_id) throws CloneNotSupportedException {
        Shape cloned = old_version.clone();
        // make small offset in x and y of position
        double offset_x=0;
        double offset_y=0;
        offset_x=15;
        offset_y=15;
        if(old_version instanceof LineSegment){
            offset_y=30;
            offset_x=30;
        }
        Point2D.Double new_point = new Point2D.Double(cloned.getPosition().getX()+offset_x,cloned.getPosition().getY()+offset_y);

        cloned.setPosition(new_point);
        cloned.setId(copied_id);
        draw(cloned);
        this.resetRedoStack();
        return cloned;
    }
    public void delete(int deleted_id){
        HashMap<Integer, Shape> curr= drawingArea.getDrawnShapes();

        if (curr.containsKey(deleted_id)){
            curr.remove(deleted_id);
        }
        drawingArea.setShapes(curr);
        this.undo_stack.push(this.drawingArea.takeSnapshot());
        this.resetRedoStack();
    }
    public void redo() {
        if (!redo_stack.empty()) {
            DrawingArea.DrawingMemento top = redo_stack.pop();
            undo_stack.push(top);
            drawingArea.restoreSnapshot(top);
        }
    }
    public void undo(){
        if(undo_stack.empty() || undo_stack.size() == 1)
            return;
        DrawingArea.DrawingMemento top= undo_stack.pop();
        System.out.println("popping last undo_stack");
        redo_stack.push(top);
        drawingArea.restoreSnapshot(undo_stack.peek());
        System.out.println("snapshot restored");
    }
    public Shape getShapeByID(int id) {
        System.out.println("Shape ID: "+id);
        System.out.println("Shapes : "+drawingArea.getDrawnShapes().getOrDefault(id, null));

        return drawingArea.getDrawnShapes().getOrDefault(id, null);
    }

    public void update(Shape modified_shape) {
        HashMap<Integer, Shape> curr=this.drawingArea.getDrawnShapes();
        curr.put(modified_shape.getId(), modified_shape);
        this.drawingArea.setShapes(curr);
        this.undo_stack.push(this.drawingArea.takeSnapshot());
        this.resetRedoStack();
    }
    public ArrayList<ShapeDTO> getDrawnShapesDTOs(){
        HashMap<Integer, Shape> saved_shapes = this.drawingArea.getDrawnShapes();
        ArrayList<ShapeDTO> shapeDTOs = new ArrayList<>();
        for (Map.Entry<Integer, Shape> set : saved_shapes.entrySet()) {
            Shape saved = set.getValue();
            shapeDTOs.add(saved.shapeToDTO());
        }
        return shapeDTOs;
    }

}
