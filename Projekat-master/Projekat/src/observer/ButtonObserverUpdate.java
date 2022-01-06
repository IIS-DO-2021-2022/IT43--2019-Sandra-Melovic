package observer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;

import mvc.DrawingFrame;

public class ButtonObserverUpdate implements PropertyChangeListener{
	
private DrawingFrame frame;
	
	public ButtonObserverUpdate(DrawingFrame frame) {
		this.frame = frame;
	}

//	PropertyChangeEvent se salje kada se promeni vrednost propertija
//	i prosledjuje se kao argument PropertyChangeListeneru
	

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
