package command;

import geometry.Circle;
import geometry.Point;

public class UpdateCircleCmd implements Command {

	private Circle oldState;
	private Circle newState;
	private Circle original;
	
	
	public UpdateCircleCmd(Circle oldState, Circle newState) {
		this.oldState=oldState;
		this.newState=newState;
		original=(Circle)oldState.clone();
	}

	@Override
	public void execute()  {
		/*original.getCenter().setX(oldState.getCenter().getX());
		original.getCenter().setY(oldState.getCenter().getY());
		try {
			original.setR(oldState.getR());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		oldState.getCenter().setX(newState.getCenter().getX());
		oldState.getCenter().setY(newState.getCenter().getY());
		try {
			oldState.setR(newState.getR());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void unexecute() {
		oldState.getCenter().setX(original.getCenter().getX());
		oldState.getCenter().setY(original.getCenter().getY());
		try {
			oldState.setR(original.getR());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
