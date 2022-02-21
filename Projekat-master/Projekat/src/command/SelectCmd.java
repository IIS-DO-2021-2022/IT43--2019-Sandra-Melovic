package command;

import adapter.HexagonAdapter;
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
		System.out.println("selektuj");
			shape.setSelected(true);
			System.out.println(shape.isSelected());
			model.getSelectedShapes().add(shape);
	}

	@Override
	public void unexecute() {
			shape.setSelected(false);
			model.getSelectedShapes().remove(shape);
	}
	
	@Override
	public String toString() {
		return "Selected->" + shape.toString();
	}

}
