package observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ButtonObserver {
	
	private boolean deleteEnabled;
	private boolean modifyEnabled;
	private boolean btbEnabled;
	private boolean btfEnabled;
	private boolean toBackEnabled;
	private boolean toFrontEnabled;

	
	//upravlja listom lister-a i prosledjuje im event
	private PropertyChangeSupport propertyChangeSupport;

	public ButtonObserver() {
		propertyChangeSupport = new PropertyChangeSupport(this); //prihvata klasu koja ga poziva
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		propertyChangeSupport.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		propertyChangeSupport.removePropertyChangeListener(pcl);
	}
	
	
	//SETERI
	public void setDeleteEnabled(boolean deleteEnabled) {
		propertyChangeSupport.firePropertyChange("btnDelete", this.deleteEnabled, deleteEnabled);
		this.deleteEnabled = deleteEnabled;
	}

	public void setModifyEnabled(boolean modifyEnabled) {
		propertyChangeSupport.firePropertyChange("btnModify", this.modifyEnabled, modifyEnabled);
		this.modifyEnabled = modifyEnabled;
	}

	public void setBtBEnabled(boolean btbEnabled) {
		propertyChangeSupport.firePropertyChange("btnBtB", this.btbEnabled, btbEnabled);
		this.btbEnabled = btbEnabled;
		
	}
	
	public void setBtFEnabled(boolean btfEnabled) {
		propertyChangeSupport.firePropertyChange("btnBtF", this.btfEnabled, btfEnabled);
		this.btfEnabled = btfEnabled;
		
	}
	public void setToBackEnabled(boolean toBackEnabled) {
		propertyChangeSupport.firePropertyChange("btnToBack", this.toBackEnabled, toBackEnabled);
		this.toBackEnabled = toBackEnabled;
		
	}
	
	public void setToFrontEnabled(boolean toFrontEnabled) {
		propertyChangeSupport.firePropertyChange("btnToFront", this.toFrontEnabled, toFrontEnabled);
		this.toFrontEnabled = toFrontEnabled;
		
	}

}
