package command;

import geometry.Donut;
import mvc.DrawingModel;

public class AddDonutCmd implements Command {

	private Donut donut;
	private DrawingModel model;
	
	
	public AddDonutCmd(Donut donut, DrawingModel model) {
		this.donut = donut;
		this.model = model;
	}

	@Override
	public void execute() {
		model.add(donut);

	}

	@Override
	public void unexecute() {
		model.remove(donut);

}
	@Override
	public String toString() {
		return "Added->" + donut.toString();
	}
}
