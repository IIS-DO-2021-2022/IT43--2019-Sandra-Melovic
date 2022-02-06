package command;

import geometry.Shape;
import mvc.DrawingModel;

public class RemoveShapeCmd implements Command{
	
	private DrawingModel model;
	private Shape shape;
	
	public RemoveShapeCmd(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		model.remove(shape);
		model.getSelectedShapes().remove(shape);
		System.out.println("obrisano");
	}

	@Override
	public void unexecute() {
		model.getShapes().add(shape);
	}

}
