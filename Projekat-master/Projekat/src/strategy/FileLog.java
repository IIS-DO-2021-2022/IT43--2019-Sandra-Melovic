package strategy;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import adapter.HexagonAdapter;
import command.AddCircleCmd;
import command.AddDonutCmd;
import command.AddHexagonCmd;
import command.AddLineCmd;
import command.AddPointCmd;
import command.AddRectangleCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.DeselectCmd;
import command.RemoveShapeCmd;
import command.SelectCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import mvc.DlgLogParser;
import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class FileLog implements FileChooser{
	
	private BufferedReader reader;
	private DrawingFrame frame;
	private DrawingModel model;
	private DrawingController controller;
	private DlgLogParser logParser;
	public Donut donut;
	public HexagonAdapter hexagonAdapter;
	private String commands;
	
	public FileLog(DrawingFrame frame, DrawingModel model, DrawingController controller) {
		this.frame = frame;
		this.model = model;
		this.controller = controller;
	}
	
	public FileLog() {
		
	}


	@Override
	public void save(String filePath) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(commands);
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void open(String filePath) {
		try {
			
			reader = new BufferedReader(new FileReader(filePath));
			logParser = new DlgLogParser();
			logParser.setFileLog(this);
			String text = "";
			text = reader.readLine();
			logParser.addCommand(text);
			logParser.setVisible(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void execute(String line) {
		String [] result =line.split("->");
		String command= result[0];
		String [] result2 = result[1].split(":");
		String shape= result2[0];
		try {
			if (command.equals("Added"))
			{
				if (shape.equals("Point"))
				{
					Point p = (Point) parseShape(result2[1],shape);
					AddPointCmd  addPointCmd = new AddPointCmd(p, model);
					addPointCmd.execute();
					frame.getList().addElement(addPointCmd.toString());
					model.pushToUndoStack(addPointCmd);
					
					
				} else if (shape.equals("Line")) {
					
					Line l = (Line) parseShape(result2[1],shape);
					AddLineCmd  addLineCmd = new AddLineCmd(l, model);
					addLineCmd.execute();
					frame.getList().addElement(addLineCmd.toString());
					model.pushToUndoStack(addLineCmd);
					
				} else if (shape.equals("Rectangle")) {
					
					Rectangle r = (Rectangle) parseShape(result2[1],shape);
					AddRectangleCmd  addRectangleCmd = new AddRectangleCmd(r, model);
					addRectangleCmd.execute();
					frame.getList().addElement(addRectangleCmd.toString());
					model.pushToUndoStack(addRectangleCmd);
					
				} else if (shape.equals("Circle")) {
					
					Circle c = (Circle) parseShape(result2[1],shape);
					AddCircleCmd  addCircleCmd = new AddCircleCmd(c, model);
					addCircleCmd.execute();
					frame.getList().addElement(addCircleCmd.toString());
					model.pushToUndoStack(addCircleCmd);
					
				} else if (shape.equals("Donut")) {
					
					Donut d = (Donut) parseShape(result2[1],shape);
					AddDonutCmd  addDonutCmd = new AddDonutCmd(d, model);
					addDonutCmd.execute();
					frame.getList().addElement(addDonutCmd.toString());
					model.pushToUndoStack(addDonutCmd);
					
				} else if (shape.equals("Hexagon")) {
					
					HexagonAdapter h = (HexagonAdapter) parseShape(result2[1],shape);
					AddHexagonCmd  addHexagonCmd = new AddHexagonCmd(h, model);
					addHexagonCmd.execute();
					frame.getList().addElement(addHexagonCmd.toString());
					model.pushToUndoStack(addHexagonCmd);
					
				}
				
			}  else if (command.equals("Updated")) {
				
				String [] result3 = result[2].split(":");
				Shape oldShape = parseShape(result2[1],shape);
				int index = model.getIndexOf(oldShape);
				
				if (shape.equals("Point"))
				{
					Point newPoint = (Point) parseShape(result3[1],shape);
					UpdatePointCmd  updatePointCmd = new UpdatePointCmd((Point) model.getByIndex(index), newPoint);
					updatePointCmd.execute();
					frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newPoint.toString());
					model.pushToUndoStack(updatePointCmd);
					
				} else if (shape.equals("Line")) {
					
					Line newLine = (Line) parseShape(result3[1],shape);
					UpdateLineCmd  updateLineCmd = new UpdateLineCmd((Line) model.getByIndex(index), newLine);
					updateLineCmd.execute();
					frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newLine.toString());
					model.pushToUndoStack(updateLineCmd);
					
				} else if (shape.equals("Rectangle")) {
					
					Rectangle newRectangle = (Rectangle) parseShape(result3[1],shape);
					UpdateRectangleCmd  updateRectangleCmd = new UpdateRectangleCmd((Rectangle) model.getByIndex(index), newRectangle);
					updateRectangleCmd.execute();
					frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newRectangle.toString());
					model.pushToUndoStack(updateRectangleCmd);
					
				} else if (shape.equals("Circle")) {
					
					Circle newCircle = (Circle) parseShape(result3[1],shape);
					UpdateCircleCmd  updateCircleCmd = new UpdateCircleCmd((Circle) model.getByIndex(index), newCircle);
					updateCircleCmd.execute();
					frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newCircle.toString());
					model.pushToUndoStack(updateCircleCmd);

					
				} else if (shape.equals("Donut")) {
					
					Donut newDonut = (Donut) parseShape(result3[1],shape);
					UpdateDonutCmd  updateDonutCmd = new UpdateDonutCmd((Donut) model.getByIndex(index), newDonut);
					updateDonutCmd.execute();
					frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newDonut.toString());
					model.pushToUndoStack(updateDonutCmd);
					
				} else if (shape.equals("Hexagon")) {
					
					HexagonAdapter newHexagon = (HexagonAdapter) parseShape(result3[1],shape);
					UpdateHexagonCmd  updateHexagonCmd = new UpdateHexagonCmd((HexagonAdapter) model.getByIndex(index), newHexagon);
					updateHexagonCmd.execute();
					frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newHexagon.toString());
					model.pushToUndoStack(updateHexagonCmd);
					
				}
				
			} else if (command.equals("Deleted")){
				
				while (model.getSelectedShapes().size() != 0) {
					Shape removeShape = parseShape(result2[1],shape);
					RemoveShapeCmd  removeShapeCmd = new RemoveShapeCmd(model, model.getSelectedShapes().get(0));
					frame.getList().addElement("Deleted->" + removeShape.toString());
					removeShapeCmd.execute();
					model.pushToUndoStack(removeShapeCmd);
				}
				
			} else if (command.equals("Selected")){
				
				Shape selectedShape = parseShape(result2[1], shape);
				for (int i = 0; i < model.getShapes().size(); i++) {
					if (selectedShape.equals(model.getShapes().get(i))) {
						selectedShape = model.getShapes().get(i);
						SelectCmd cmdSelect = new SelectCmd(model, selectedShape);
						cmdSelect.execute();
						frame.getList().addElement("Selected->" + selectedShape.toString());
						model.pushToUndoStack(cmdSelect);
					}
				}				
			} else if (command.equals("Deselected")){
				
				Shape deselectedShape = parseShape(result2[1], shape);
				for (int i = 0; i < model.getShapes().size(); i++) {
					if (deselectedShape.equals(model.getShapes().get(i))) {
						deselectedShape = model.getShapes().get(i);
						DeselectCmd cmdDeselect = new DeselectCmd(model, deselectedShape);
						cmdDeselect.execute();
						frame.getList().addElement("Deselected->" + deselectedShape.toString());
						model.pushToUndoStack(cmdDeselect);
					}
				}		
				
			} else if (command.equals("Bring to front")){
				
				int index = model.getShapes().indexOf(model.getSelectedShapes().get(0));
				Shape bringToFrontShape = model.getShapes().get(index);
				BringToFrontCmd bringToFrontCmd = new BringToFrontCmd(model, bringToFrontShape);
				bringToFrontCmd.execute();
				frame.getList().addElement("Bring to front->" + bringToFrontShape.toString());
				model.pushToUndoStack(bringToFrontCmd);
				frame.repaint();
				
			} else if (command.equals("Bring to back")){
				
				int index = model.getShapes().indexOf(model.getSelectedShapes().get(0));
				Shape bringToBackShape = model.getShapes().get(index);
				BringToBackCmd bringTobackCmd = new BringToBackCmd(model, bringToBackShape, model.getIndexOf(bringToBackShape));
				bringTobackCmd.execute();
				frame.getList().addElement("Bring to back->" + bringToBackShape.toString());
				model.pushToUndoStack(bringTobackCmd);
				frame.repaint();
							
			} else if (command.equals("To front")){
				
				Shape toFrontShape = parseShape(result2[1], shape);
				ToFrontCmd toFrontCmd = new ToFrontCmd(model, model.getIndexOf(toFrontShape), toFrontShape);
				toFrontCmd.execute();
				frame.getList().addElement("To front->" + toFrontShape.toString());
				model.pushToUndoStack(toFrontCmd);
				frame.repaint();
				
			} else if (command.equals("To back")){
				
				Shape toBackShape = parseShape(result2[1], shape);
				ToBackCmd tobackCmd = new ToBackCmd(model, model.getIndexOf(toBackShape), toBackShape);
				tobackCmd.execute();
				frame.getList().addElement("To back->" + toBackShape.toString());
				model.pushToUndoStack(tobackCmd);
				frame.repaint();
				
			} else if (command.equals("Undo")){
				
				
				controller.undo();

				
			} else if (command.equals("Redo")){
				
				controller.redo();
			
			}
			frame.getView().repaint();
			String read = reader.readLine();
			if (read != null) 
				logParser.addCommand(read);
			else {
				logParser.closeDialog();
				return;
			}
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	
	private Shape parseShape(String result, String shapeName)
	{
		boolean bool = false;
		Shape shape=null;
		String[] prpShape = result.split(",");
		if(shapeName.equals("Point"))
		{
			int x =Integer.parseInt(prpShape[0]) ;
			int y= Integer.parseInt(prpShape[1]);
			int r=Integer.parseInt(prpShape[2]);
			int g= Integer.parseInt(prpShape[3]);
			int b= Integer.parseInt(prpShape[4]);
			String isSelected= prpShape[5];
			
			if (isSelected.equals("false"))
			{
				bool=false;
			}
			else bool=true;
			
			shape= new Point(x,y,bool,new Color(r,g,b));
			
		} else if (shapeName.equals("Line")) {
			
			int x1 =Integer.parseInt(prpShape[0]);
			int y1= Integer.parseInt(prpShape[1]);
			int x2 =Integer.parseInt(prpShape[2]) ;
			int y2= Integer.parseInt(prpShape[3]);
			int r=Integer.parseInt(prpShape[4]);
			int g= Integer.parseInt(prpShape[5]);
			int b= Integer.parseInt(prpShape[6]);
			String isSelected= prpShape[7];

			if (isSelected.equals("false"))
			{
				bool=false;
			}
			else bool=true;
				
			shape = new Line(new Point(x1,y1), new Point(x2,y2), bool, new Color(r, g, b));
			
		} else if (shapeName.equals("Rectangle")) {
			
			int x =Integer.parseInt(prpShape[0]) ;
			int y= Integer.parseInt(prpShape[1]);
			int width =Integer.parseInt(prpShape[2]) ;
			int height = Integer.parseInt(prpShape[3]);
					
			int r1=Integer.parseInt(prpShape[4]);
			int g1= Integer.parseInt(prpShape[5]);
			int b1= Integer.parseInt(prpShape[6]);
			
			int r2=Integer.parseInt(prpShape[7]);
			int g2= Integer.parseInt(prpShape[8]);
			int b2= Integer.parseInt(prpShape[9]);
			
			String isSelected= prpShape[10];
			
			if (isSelected.equals("false"))
			{
				bool=false;
			}
			else bool=true;

			shape =new Rectangle(new Point(x,y), width,height, bool, new Color(r1, g1, b1), new Color(r2, g2, b2));
			
		} else if (shapeName.equals("Circle")) {
			
			int x =Integer.parseInt(prpShape[0]) ;
			int y= Integer.parseInt(prpShape[1]);
			int r=Integer.parseInt(prpShape[2]);
			
			int r1=Integer.parseInt(prpShape[3]);
			int g1= Integer.parseInt(prpShape[4]);
			int b1= Integer.parseInt(prpShape[5]);
			
			int r2=Integer.parseInt(prpShape[6]);
			int g2= Integer.parseInt(prpShape[7]);
			int b2= Integer.parseInt(prpShape[8]);
			
			String isSelected= prpShape[9];
			
			if (isSelected.equals("false"))
			{
				bool=false;
			}
			else bool=true;
			
			shape = new Circle(new Point(x,y), r,bool,  new Color(r1,g1,b1), new Color(r2, g2, b2));
			
		} else if (shapeName.equals("Donut")) {
			
			int x =Integer.parseInt(prpShape[0]) ;
			int y= Integer.parseInt(prpShape[1]);
			int r=Integer.parseInt(prpShape[2]);
			int inR = Integer.parseInt(prpShape[3]);
			
			int r1=Integer.parseInt(prpShape[4]);
			int g1= Integer.parseInt(prpShape[5]);
			int b1= Integer.parseInt(prpShape[6]);
			
			int r2=Integer.parseInt(prpShape[7]);
			int g2= Integer.parseInt(prpShape[8]);
			int b2= Integer.parseInt(prpShape[9]);
			
			String isSelected= prpShape[10];
			
			if (isSelected.equals("false"))
			{
				bool=false;
			}
			else bool=true;
			
			shape=  new Donut(new Point(x,y), r, inR, bool, new Color(r1, g1, b1), new Color(r2, g2, b2));
			
		} else if (shapeName.equals("Hexagon")) {
			
			int x =Integer.parseInt(prpShape[0]) ;
			int y= Integer.parseInt(prpShape[1]);
			int r=Integer.parseInt(prpShape[2]);
			
			int r1=Integer.parseInt(prpShape[3]);
			int g1= Integer.parseInt(prpShape[4]);
			int b1= Integer.parseInt(prpShape[5]);
			
			int r2=Integer.parseInt(prpShape[6]);
			int g2= Integer.parseInt(prpShape[7]);
			int b2= Integer.parseInt(prpShape[8]);
			
			String isSelected= prpShape[9];
			
			if (isSelected.equals("false"))
			{
				bool=false;
			}
			else bool=true;
			
			shape = new HexagonAdapter(x, y, r, bool, new Color(r1, g1, b1), new Color(r2, g2, b2));
			
		}
		return shape;
	}
	
	public void setCommands(String commands) {
		this.commands=commands;
	}
	
}