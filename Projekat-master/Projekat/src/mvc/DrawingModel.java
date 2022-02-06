package mvc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import command.Command;
import geometry.Point;
import geometry.Shape;

public class DrawingModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public List<Shape> shapes = new ArrayList<Shape>();
	private ArrayList<Shape> selectedShapes = new ArrayList<Shape>();
	public Point startPoint;
	public Shape selectedShape;
	private Stack<Command> undoStack = new Stack<>();
	private Stack<Command> redoStack = new Stack<>();
	public int undoCounter = 0;

	public void remove(Shape toBeRemoved)
	{
		if(shapes.remove(toBeRemoved) == false) {
			System.out.println("Shape does not exist in list of shapes!");
		}
		
		selectedShapes.remove(toBeRemoved);

	}
	
	public void add(Shape toBeAdded) {
			shapes.add(toBeAdded);
			
	}
	
	public void addSelectedShape(Shape toBeAdded) {
		selectedShapes.add(toBeAdded);
		
}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Shape getSelectedShape() {
		return selectedShape;
	}

	public void setSelectedShape(Shape selectedShape) {
		this.selectedShape = selectedShape;
	}

	public List<Shape> getShapes() {
		return shapes;
	}

	public void setShapes(List<Shape> shapes) {
		this.shapes = shapes;
	}
	public Stack<Command> getUndoStack() {
		return undoStack;
	}
	public void setUndoStack(Stack<Command> undoStack) {
		this.undoStack = undoStack;
	}
	public Stack<Command> getRedoStack() {
		return redoStack;
	}
	public void setRedoStack(Stack<Command> redoStack) {
		this.redoStack = redoStack;
	}
	
	public void pushToUndoStack(Command toBePushed) {
		undoCounter++;
		this.undoStack.push(toBePushed);
	
	}
	
	public void removeFromUndoStack() {
		undoCounter--;
		if(undoStack.peek()!=null) {
			this.undoStack.pop().unexecute();
		}
		
	}
	
	public void pushToRedoStack(Command toBePushed) {
		this.redoStack.push(toBePushed);
	
	}
	
	public void removeFromRedoStack() {
		if(redoStack.peek()!=null) {
			this.redoStack.pop().execute();
		}
		
	}
	
	public void clear() {
		
		shapes.clear();
		
	}
	
	public List<Shape> getSelectedShapes() {
		return selectedShapes;
	}

	public void addMultiple(ArrayList<Shape> shapes) {
		this.shapes.addAll(shapes);
		
		
	}
	
	public int getIndexOf(Shape shape) {
		return shapes.indexOf(shape);
	}
	
	public Shape getByIndex(int index) {
		return shapes.get(index);
	}


}
