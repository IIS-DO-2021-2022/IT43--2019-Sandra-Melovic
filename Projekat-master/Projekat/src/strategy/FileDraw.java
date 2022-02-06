package strategy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingModel;

public class FileDraw implements FileChooser{
	
	private FileOutputStream fileOutputStream;
	private FileInputStream fileInputStream;
	private DrawingModel model;
	private List<Shape> shapes;
	DrawingController controller;
	
	public FileDraw(DrawingModel model, DrawingController controller) {
		
		this.model=model;
		this.controller=controller;
		
	}
	
	public FileDraw() {
		
	}
	
	
	@Override
	public void save(String filePath) {
		if (filePath == null)
			return;		
		try {
			fileOutputStream = new FileOutputStream(filePath);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(model.getShapes());
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}

	@SuppressWarnings("unchecked")
	@Override
	public void open(String filePath) {
		try {
			fileInputStream = new FileInputStream(filePath);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	        model.addMultiple((ArrayList<Shape>) objectInputStream.readObject());
	        model.setShapes((List<Shape>)objectInputStream.readObject());
	        objectInputStream.close();
	        fileInputStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setShapes(List<Shape> shapes) {
		this.shapes = shapes;
	}
	
	public void setModel(DrawingModel model) {
		this.model = model;
	}

}
