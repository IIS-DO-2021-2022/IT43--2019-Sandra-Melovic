package observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ButtonObserver {
	
	private boolean deleteEnabled;
	private boolean modifyEnabled;
	private boolean BtBEnabled;
	private boolean BtFEnabled;
	private boolean toBackEnabled;
	private boolean toFrontEnabled;

	
	private PropertyChangeSupport propertyChangeSupport;

	public ButtonObserver() {
		propertyChangeSupport = new PropertyChangeSupport(this); 
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		propertyChangeSupport.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		propertyChangeSupport.removePropertyChangeListener(pcl);
	}
	
	
	public void setDeleteEnabled(boolean deleteEnabled) {
		propertyChangeSupport.firePropertyChange("btnDelete", this.deleteEnabled, deleteEnabled);
		this.deleteEnabled = deleteEnabled;
	}

	public void setModifyEnabled(boolean modifyEnabled) {
		propertyChangeSupport.firePropertyChange("btnModify", this.modifyEnabled, modifyEnabled);
		this.modifyEnabled = modifyEnabled;
	}

	public void setBtBEnabled(boolean BtBEnabled) {
		propertyChangeSupport.firePropertyChange("btnBtB", this.BtBEnabled, BtBEnabled);
		this.BtBEnabled = BtBEnabled;
		
	}
	
	public void setBtFEnabled(boolean btfEnabled) {
		propertyChangeSupport.firePropertyChange("btnBtF", this.BtFEnabled, btfEnabled);
		this.BtFEnabled = btfEnabled;
		
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
