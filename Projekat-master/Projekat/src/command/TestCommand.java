package command;

import geometry.Point;
import mvc.DrawingModel;

public class TestCommand {

	public static void main(String[] args) {
		Point p1=new Point(18,18);
		Point p2=new Point(25,25);
		DrawingModel model = new DrawingModel();
		
		AddPointCmd addPointCmd = new AddPointCmd(p1, model);
		addPointCmd.execute();
		
		System.out.println(model.getShapes().size());
		addPointCmd.unexecute();
		
		System.out.println(model.getShapes().size());
		
		UpdatePointCmd updatePointCmd = new UpdatePointCmd(p1,p2);
		
		updatePointCmd.execute();
		System.out.println(p1);
		
		updatePointCmd.unexecute();
		System.out.println(p1);

	}

}
