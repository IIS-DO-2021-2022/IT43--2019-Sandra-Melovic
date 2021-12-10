package command;

import adapter.HexagonAdapter;
import geometry.Circle;
import geometry.Hexagon;
import geometry.Point;
import mvc.DrawingModel;

public class UpdateHexagonCmd implements Command {
	
	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter original;
	
	
	public UpdateHexagonCmd(HexagonAdapter oldState, HexagonAdapter newState) {
		this.oldState=oldState;
		this.newState=newState;
		original=(HexagonAdapter) oldState.clone();
		System.out.println("udje u update" + newState);
	}

	@Override
	public void execute()  {
		System.out.println("udje u execute updatea" + newState);
		/*original.getHexagon().setX(oldState.getHexagon().getX());
		original.getHexagon().setY(oldState.getHexagon().getY());
		try {
			original.getHexagon().setR(oldState.getHexagon().getR());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		oldState.getHexagon().setX(newState.getHexagon().getX());
		oldState.getHexagon().setY(newState.getHexagon().getY());
		try {
			oldState.getHexagon().setR(newState.getHexagon().getR());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void unexecute() {
		oldState.getHexagon().setX(original.getHexagon().getX());
		oldState.getHexagon().setY(original.getHexagon().getY());
		try {
			oldState.getHexagon().setR(original.getHexagon().getR());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
