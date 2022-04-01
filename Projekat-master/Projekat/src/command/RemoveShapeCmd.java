package command;

import geometry.Shape;
import mvc.DrawingModel;

public class RemoveShapeCmd implements Command{
	
	private DrawingModel model;
	private Shape shape;
	private int index;
	
	public RemoveShapeCmd(DrawingModel model, Shape shape, int index) {
		this.model = model;
		this.shape = shape;
		this.index=index;
	}

	@Override
	public void execute() {
		model.remove(shape);
		model.getSelectedShapes().remove(shape);
		System.out.println("obrisano");
	}

	@Override
	public void unexecute() {
		model.getShapes().add(index, shape);  
	}
	
	@Override
	public String toString() {
		return "Deleted->" + shape.toString();
	}

}
