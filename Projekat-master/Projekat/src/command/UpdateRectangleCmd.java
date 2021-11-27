package command;

import java.awt.Color;

import geometry.Point;
import geometry.Rectangle;

public class UpdateRectangleCmd implements Command {

	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle original= new Rectangle(new Point(445,24), 45, 65,Color.BLACK,Color.BLACK );
	
	public UpdateRectangleCmd(Rectangle oldState, Rectangle newState) {
		this.oldState=oldState;
		this.newState=newState;
	}
	

	@Override
	public void execute() {
		original.getUpperLeft().setX(oldState.getUpperLeft().getX());
		original.getUpperLeft().setY(oldState.getUpperLeft().getY());
		original.setHeight(oldState.getHeight());
		original.setWidth(oldState.getWidth());
		
		oldState.getUpperLeft().setX(newState.getUpperLeft().getX());
		oldState.getUpperLeft().setY(newState.getUpperLeft().getY());
		oldState.setHeight(newState.getHeight());
		oldState.setWidth(newState.getWidth());

	}

	@Override
	public void unexecute() {
		oldState.getUpperLeft().setX(original.getUpperLeft().getX());
		oldState.getUpperLeft().setY(original.getUpperLeft().getY());
		oldState.setHeight(original.getHeight());
		oldState.setWidth(original.getWidth());
	}

}
