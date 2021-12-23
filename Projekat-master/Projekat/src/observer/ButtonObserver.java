package observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ButtonObserver {
	
	private boolean deleteEnabled;
	private boolean modifyEnabled;

	
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

}
