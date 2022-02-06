package observer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


import mvc.DrawingFrame;

public class ButtonObserverUpdate implements PropertyChangeListener{
	
private DrawingFrame frame;
	
	public ButtonObserverUpdate(DrawingFrame frame) {
		this.frame = frame;
	}
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("btnDelete")) {
			frame.getBtnDelete().setEnabled((boolean) evt.getNewValue());
		}
		if(evt.getPropertyName().equals("btnModify")) {
			frame.getBtnModify().setEnabled((boolean) evt.getNewValue());
		}
		if(evt.getPropertyName().equals("btnBtB")) {
			frame.getBtnBtB().setEnabled((boolean) evt.getNewValue());
		}
		if(evt.getPropertyName().equals("btnBtF")) {
			frame.getBtnBtF().setEnabled((boolean) evt.getNewValue());
		}
		if(evt.getPropertyName().equals("btnToBack")) {
			frame.getBtnToBack().setEnabled((boolean) evt.getNewValue());
		}
		if(evt.getPropertyName().equals("btnToFront")) {
			frame.getBtnToFront().setEnabled((boolean) evt.getNewValue());
		}

		
	}
}
