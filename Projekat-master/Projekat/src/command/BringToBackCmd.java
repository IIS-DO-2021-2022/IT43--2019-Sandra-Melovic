package command;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToBackCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int index;
	
	 public BringToBackCmd(DrawingModel model, Shape shape, int index) {
		this.model=model;
		this.shape=shape;
		this.index=index;
	}
	
	

	@Override
	public void execute() {	
			model.getShapes().remove(shape);
			model.getShapes().add(0,shape);	

	}

	@Override
	public void unexecute() { // Undo 
		model.getShapes().remove(shape);
		model.getShapes().add(index,shape);

	}



	@Override
	public String toString() { //For log
		return "BringToBack:" + shape.toString();
	}
	

}
