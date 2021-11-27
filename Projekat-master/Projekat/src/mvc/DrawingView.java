package mvc;

import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import geometry.Shape;

public class DrawingView extends JPanel {
	
	public DrawingView() {

	}
	
	private DrawingModel model = new DrawingModel();

	public void setModel(DrawingModel model) {
		this.model = model;
	}
	
	public DrawingModel getModel() {
		return model;
	}

	

	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Shape> iterator = model.getShapes().iterator();
		while (iterator.hasNext())
			iterator.next().draw(g);

	}
	
	
}
