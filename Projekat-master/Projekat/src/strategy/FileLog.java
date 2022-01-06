package strategy;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;

import adapter.HexagonAdapter;
import command.AddCircleCmd;
import command.AddDonutCmd;
import command.AddHexagonCmd;
import command.AddLineCmd;
import command.AddPointCmd;
import command.AddRectangleCmd;
import command.DeselectCmd;
import command.RemoveShapeCmd;
import command.SelectCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import geometry.Circle;
import geometry.Donut;
import geometry.Hexagon;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import mvc.DlgLogParser;
import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class FileLog implements FileChooser{
	
	private BufferedWriter writer;
	private BufferedReader reader;
	private DrawingFrame frame;
	private DrawingModel model;
	private DrawingController controller;
	private DlgLogParser logParser;
	
	private Point point;
	private Line line;
	private Circle circle;
	
	public FileLog(DrawingFrame frame, DrawingModel model, DrawingController controller) {
		this.frame = frame;
		this.model = model; 
		this.controller = controller;
	}

	/**
	 * Save forwarded file as log of commands.
	 */
	@Override
	public void save(File file) {
		try {
			writer = new BufferedWriter(new FileWriter(file + ".log"));
			DefaultListModel<String> list = frame.getList();
			for (int i = 0; i < frame.getList().size(); i++) {
				writer.write(list.getElementAt(i));
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Open forwarded log file and execute it command by command in interaction with user.
	 */
	@Override
	public void open(File file) {
		try {
			reader = new BufferedReader(new FileReader(file));
			logParser = new DlgLogParser();
			logParser.setFileLog(this);
			logParser.addCommand(reader.readLine());
			logParser.setVisible(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public void readLine(String command) {
		try {
			String[] commands = command.split("->");
			switch(commands[0]) {
				case "Added":
					Shape shape = parseShape(commands[1].split(":")[0], commands[1].split(":")[1]);
					if(commands[1].split(":")[0].equals("Donut")) {
						controller.executeCommand(new AddDonutCmd((Donut)shape,model));				
					}
					else if(commands[1].split(":")[0].equals("Circle")) {
						 circle =  (Circle) shape;
						controller.executeCommand(new AddCircleCmd(circle,model));
					}
					else if(commands[1].split(":")[0].equals("Hexagon")) {
						controller.executeCommand(new AddHexagonCmd((HexagonAdapter)shape,model));
					}
					else if(commands[1].split(":")[0].equals("Line")) {
						 line =  (Line) shape;
						controller.executeCommand(new AddLineCmd(line,model));
					}
					else if(commands[1].split(":")[0].equals("Point")) {
						 point =  (Point) shape;
						controller.executeCommand(new AddPointCmd(point,model));
					}
					
					else if(commands[1].split(":")[0].equals("Rectangle")) {
						controller.executeCommand(new AddRectangleCmd((Rectangle)shape,model));
					}
					frame.getList().addElement("Added->" + shape.toString());
					break;				
				case "Updated":
					Shape oldShape = parseShape(commands[1].split(":")[0], commands[1].split(":")[1]);
					int index = model.getIndexOf(oldShape);
					if (oldShape instanceof Point) {
						Point newPoint = parsePoint(commands[2].split(":")[1]);
						UpdatePointCmd updatePointCmd= (new UpdatePointCmd((Point) model.getByIndex(index), newPoint));
						frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newPoint.toString());
					}
					else if (oldShape instanceof Line) {
						Line newLine = parseLine(commands[2].split(":")[1]);
						controller.executeCommand(new UpdateLineCmd((Line) model.getByIndex(index), newLine));
						frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newLine.toString());
					}
					else if (oldShape instanceof Rectangle) {
						Rectangle newRectangle = parseRectangle(commands[2].split(":")[1]);
						controller.executeCommand(new UpdateRectangleCmd((Rectangle) model.getByIndex(index), newRectangle));
						frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newRectangle.toString());
					}
					else if (oldShape instanceof Donut) {
						Donut newDonut = parseDonut(commands[2].split(":")[1]);
						controller.executeCommand(new UpdateDonutCmd((Donut) model.getByIndex(index), newDonut));
						frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newDonut.toString());
					}
					else if (oldShape instanceof Circle) {
						Circle newCircle = parseCircle(commands[2].split(":")[1]);
						controller.executeCommand(new UpdateCircleCmd((Circle) model.getByIndex(index), newCircle));
						frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newCircle.toString());
					}
					else if (oldShape instanceof HexagonAdapter) {
						HexagonAdapter newHexagon = parseHexagon(commands[2].split(":")[1]);
						controller.executeCommand(new UpdateHexagonCmd((HexagonAdapter) model.getByIndex(index), newHexagon));
						frame.getList().addElement("Updated->" + oldShape.toString() + "->" + newHexagon.toString());
					}
					break;
				case "Deleted":
					controller.delete();
					break;
			/*	case "Moved to front":
					Shape shapeMovedToFront = parseShape(commands1[1].split(":")[0], commands1[1].split(":")[1]);
					controller.executeCommand(new CmdToFront(model, shapeMovedToFront));
					frame.getList().addElement("Moved to front->" + shapeMovedToFront.toString());
					break;
				case "Moved to back":
					Shape shapeMovedToBack = parseShape(commands1[1].split(":")[0], commands1[1].split(":")[1]);
					controller.executeCommand(new CmdToBack(model, shapeMovedToBack));
					frame.getList().addElement("Moved to back->" + shapeMovedToBack.toString());
					break;
				case "Bringed to front":
					Shape shapeBringedToFront = parseShape(commands1[1].split(":")[0], commands1[1].split(":")[1]);
					controller.executeCommand(new CmdBringToFront(model, shapeBringedToFront, model.getAll().size() - 1));
					frame.getList().addElement("Bringed to front->" + shapeBringedToFront.toString());
					break;
				case "Bringed to back":
					Shape shapeBringedToBack = parseShape(commands1[1].split(":")[0], commands1[1].split(":")[1]);
					controller.executeCommand(new CmdBringToBack(model, shapeBringedToBack));
					frame.getList().addElement("Bringed to back->" + shapeBringedToBack.toString());
					break;
					*/
				case "Selected":
					Shape selectedShape = parseShape(commands[1].split(":")[0], commands[1].split(":")[1]);
					controller.executeCommand(new SelectCmd(model,selectedShape));
					frame.getList().addElement("Selected->" + selectedShape.toString());
					break;
				case "Unselected":
					Shape unselectedShape = parseShape(commands[1].split(":")[0], commands[1].split(":")[1]);
					controller.executeCommand(new DeselectCmd(model,unselectedShape));
					frame.getList().addElement("Selected->" + unselectedShape.toString());
					break;
				case "Undo":
					controller.undo();
					break;
				case "Redo":
					controller.redo();
					break;
			}
		
			String line = reader.readLine();
			if (line != null) logParser.addCommand(line);
			else {
				logParser.closeDialog();
				return;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

	private Shape parseShape(String shape, String shapeParameters) throws Exception {
		if (shape.equals("Point")) return parsePoint(shapeParameters);
		else if (shape.equals("Hexagon")) return parseHexagon(shapeParameters);
		else if (shape.equals("Line")) return parseLine(shapeParameters);
		else if (shape.equals("Circle")) return parseCircle(shapeParameters);
		else if (shape.equals("Rectangle")) return parseRectangle(shapeParameters);
		else return parseDonut(shapeParameters);
	}

	
	private Point parsePoint(String string) {
		String [] pointParts = string.split(";"); 		
		String s = pointParts[2].split("=")[1].substring(1, pointParts[2].split("=")[1].length() - 1);
		String [] colors = s.split(",");
		return new Point(Integer.parseInt(pointParts[0].split("=")[1]), Integer.parseInt(pointParts[1].split("=")[1]), new Color(Integer.parseInt(colors[0].split("-")[1]), Integer.parseInt(colors[1].split("-")[1]), Integer.parseInt(colors[2].split("-")[1])));
	}
	
	private Circle parseCircle(String string) throws NumberFormatException, Exception {
		String [] circleParts = string.split(";"); 	
		int radius = Integer.parseInt(circleParts[0].split("=")[1]);
		int x = Integer.parseInt(circleParts[1].split("=")[1]);
		int y = Integer.parseInt(circleParts[2].split("=")[1]);
		String s = circleParts[3].split("=")[1].substring(1, circleParts[3].split("=")[1].length() - 1);
		String [] edgeColors = s.split(",");
		String s1 = circleParts[4].split("=")[1].substring(1, circleParts[4].split("=")[1].length() - 1);
		String [] interiorColors = s1.split(",");
		return new Circle(new Point(x, y), radius, false, new Color(Integer.parseInt(edgeColors[0].split("-")[1]), Integer.parseInt(edgeColors[1].split("-")[1]), Integer.parseInt(edgeColors[2].split("-")[1])), new Color(Integer.parseInt(interiorColors[0].split("-")[1]), Integer.parseInt(interiorColors[1].split("-")[1]), Integer.parseInt(interiorColors[2].split("-")[1])));
	}
	
	private Line parseLine(String string) {
		String [] lineParts = string.split(";"); 	
		int xStart = Integer.parseInt(lineParts[0].split("=")[1]);
		int yStart = Integer.parseInt(lineParts[1].split("=")[1]);
		int xEnd = Integer.parseInt(lineParts[2].split("=")[1]);
		int yEnd = Integer.parseInt(lineParts[3].split("=")[1]);
		String s = lineParts[4].split("=")[1].substring(1, lineParts[4].split("=")[1].length() - 1);
		String [] edgeColors = s.split(",");
		Point startPoint = new Point(xStart, yStart);
		Point endPoint = new Point(xEnd, yEnd);
		Color lineColor = new Color(Integer.parseInt(edgeColors[0].split("-")[1]), Integer.parseInt(edgeColors[1].split("-")[1]), Integer.parseInt(edgeColors[2].split("-")[1]));
		return new Line(startPoint, endPoint, lineColor);
	}
	
	private HexagonAdapter parseHexagon(String string) throws Exception {
		String [] hexagonParts = string.split(";"); 	
		int radius = Integer.parseInt(hexagonParts[0].split("=")[1]);
		int x = Integer.parseInt(hexagonParts[1].split("=")[1]);
		int y = Integer.parseInt(hexagonParts[2].split("=")[1]);
		String s = hexagonParts[3].split("=")[1].substring(1, hexagonParts[3].split("=")[1].length() - 1);
		String [] edgeColors = s.split(",");
		String s1 = hexagonParts[4].split("=")[1].substring(1, hexagonParts[4].split("=")[1].length() - 1);
		String [] interiorColors = s1.split(",");
		Hexagon h = new Hexagon(x, y, radius);
		h.setBorderColor(new Color(Integer.parseInt(edgeColors[0].split("-")[1]), Integer.parseInt(edgeColors[1].split("-")[1]), Integer.parseInt(edgeColors[2].split("-")[1])));
		h.setAreaColor(new Color(Integer.parseInt(interiorColors[0].split("-")[1]), Integer.parseInt(interiorColors[1].split("-")[1]), Integer.parseInt(interiorColors[2].split("-")[1])));
		return new HexagonAdapter(h);
	}
	
	private Donut parseDonut(String string) throws NumberFormatException, Exception {
		String [] donutParts = string.split(";"); 	
		int radius = Integer.parseInt(donutParts[0].split("=")[1]);
		int x = Integer.parseInt(donutParts[1].split("=")[1]);
		int y = Integer.parseInt(donutParts[2].split("=")[1]);
		String s = donutParts[3].split("=")[1].substring(1, donutParts[3].split("=")[1].length() - 1);
		String [] edgeColors = s.split(",");
		String s1 = donutParts[4].split("=")[1].substring(1, donutParts[4].split("=")[1].length() - 1);
		String [] interiorColors = s1.split(",");
		int innerRadius = Integer.parseInt(donutParts[5].split("=")[1]);
		return new Donut(new Point(x, y), radius, innerRadius,false, new Color(Integer.parseInt(edgeColors[0].split("-")[1]), Integer.parseInt(edgeColors[1].split("-")[1]), Integer.parseInt(edgeColors[2].split("-")[1])), new Color(Integer.parseInt(interiorColors[0].split("-")[1]), Integer.parseInt(interiorColors[1].split("-")[1]), Integer.parseInt(interiorColors[2].split("-")[1])));
	}
	
	private Rectangle parseRectangle(String string) {
		String [] rectangleParts = string.split(";"); 	
		int x = Integer.parseInt(rectangleParts[0].split("=")[1]);
		int y = Integer.parseInt(rectangleParts[1].split("=")[1]);
		int height = Integer.parseInt(rectangleParts[2].split("=")[1]);
		int width = Integer.parseInt(rectangleParts[3].split("=")[1]);
		String s = rectangleParts[4].split("=")[1].substring(1, rectangleParts[4].split("=")[1].length() - 1);
		String [] edgeColors = s.split(",");
		String s1 = rectangleParts[5].split("=")[1].substring(1, rectangleParts[5].split("=")[1].length() - 1);
		String [] interiorColors = s1.split(",");
		return new Rectangle(new Point(x, y), width, height, new Color(Integer.parseInt(edgeColors[0].split("-")[1]), Integer.parseInt(edgeColors[1].split("-")[1]), Integer.parseInt(edgeColors[2].split("-")[1])), new Color(Integer.parseInt(interiorColors[0].split("-")[1]), Integer.parseInt(interiorColors[1].split("-")[1]), Integer.parseInt(interiorColors[2].split("-")[1])));
	}
}