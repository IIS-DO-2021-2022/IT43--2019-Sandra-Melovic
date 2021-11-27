package command;

import geometry.Shape;
import mvc.DrawingModel;

public class RemoveShapeCmd implements Command{
	
	private DrawingModel model;
	private Shape shape;
	
	public RemoveShapeCmd(DrawingModel appModel, Shape shape) {
		this.model = appModel;
		this.shape = shape;
	}

	@Override
	public void execute() {
		model.remove(shape);
		model.getSelectedShapes().remove(shape);
	}

	@Override
	public void unexecute() {
		model.getShapes().add(shape);
	}

}
