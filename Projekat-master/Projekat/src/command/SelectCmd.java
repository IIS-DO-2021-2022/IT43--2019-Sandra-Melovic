package command;

import geometry.Shape;
import mvc.DrawingModel;

public class SelectCmd implements Command{

	
	private  DrawingModel model ;
	private Shape shape;
	public SelectCmd( DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		shape.setSelected(true);
		model.addSelectedShape(shape);
		
	}

	@Override
	public void unexecute() {
		shape.setSelected(false);
		model.getSelectedShapes().remove(shape);
		
	}

}
