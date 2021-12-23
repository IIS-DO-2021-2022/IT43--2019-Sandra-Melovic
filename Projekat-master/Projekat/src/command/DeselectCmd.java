package command;

import geometry.Shape;
import mvc.DrawingModel;

public class DeselectCmd implements Command {
	
	private DrawingModel model;
	private Shape shape;
	
	public DeselectCmd(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		System.out.println("deselektovan");
		shape.setSelected(false);
		model.getSelectedShapes().remove(shape);
		System.out.println(shape.isSelected());

	}

	@Override
	public void unexecute() {
		shape.setSelected(true);
		model.getSelectedShapes().add(shape);

	}

}
