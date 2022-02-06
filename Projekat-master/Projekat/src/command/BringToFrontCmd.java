package command;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToFrontCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int index;
	
	public BringToFrontCmd(DrawingModel model,Shape shape) {
		this.model=model;
		this.shape=shape;
		
	}
	

	@Override
	public void execute() {	
			index=model.getIndexOf(shape);
			model.getShapes().remove(shape);
			model.getShapes().add(shape);		

	}

	@Override
	public void unexecute() {
		model.getShapes().remove(shape);
		model.getShapes().add(index,shape);

	}


	@Override
	public String toString() {
		return "BringToFront:" + shape.toString();
	}

}
