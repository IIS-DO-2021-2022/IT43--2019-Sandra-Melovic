package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape{
	
	private Point startPoint;
	private Point endPoint;
	
	
	public Line() {

	}
	
	public Line(Point startPoint, Point endPoint) {
		this.startPoint=startPoint;
		this.endPoint=endPoint;
	}
	
	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint, endPoint);
		this.selected=selected;
	}
	
	public Line(Point startPoint, Point endPoint, Color color) {
		this(startPoint, endPoint);
		setColor(color);
	}
	
	public Line(Point startPoint, Point endPoint, boolean selected, Color color) {
		this(startPoint, endPoint, selected);
		setColor(color);
	}

	public double length() {
		return startPoint.distance(endPoint.getX(), endPoint.getY());
	}
	
	public boolean contains(int x, int y) {
		return (startPoint.distance(x, y) + endPoint.distance(x, y)) - length() <= 2;
	}
	
	@Override
	public void moveTo(int x, int y) {
				
	}

	@Override
	public void moveBy(int byX, int byY) {
		startPoint.moveBy(byX, byY);
		endPoint.moveBy(byX, byY);		
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(this.startPoint.getX(), this.startPoint.getY(), this.endPoint.getX(), this.endPoint.getY());
		
		if (selected) {
			g.setColor(Color.BLUE);
			g.drawRect(startPoint.getX()-2, startPoint.getY() - 2, 4, 4);
			g.drawRect(endPoint.getX() - 2, endPoint.getY() - 2, 4, 4);
		}
	}
	
	@Override
	public String toString() {
		return startPoint + " --> " + endPoint;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Line) {
			Line temp=(Line) obj;
			return startPoint.equals(temp.startPoint) && endPoint.equals(temp.endPoint);
		}
		return false;
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof Line) {
			return (int)(this.length()- ((Line)o).length());
		}
		return 0;
	}
	
	public void setStartPoint(Point startPoint) {
		this.startPoint=startPoint;		
	}
	
	public Point getStartPoint() {
		return startPoint;
	}
	
	public void setEndPoint(Point endPoint) {
		this.endPoint=endPoint;	
	}
	
	public Point getEndPoint() {
		return endPoint;
	}
	
	@Override
	public Shape clone() {
		Line line = new Line();
		line.setStartPoint(this.startPoint);
		line.setEndPoint(this.endPoint);
		line.getStartPoint().setX(this.getStartPoint().getX());
		line.getStartPoint().setY(this.getStartPoint().getY());
		line.getStartPoint().setColor(this.getStartPoint().getColor());

		line.getEndPoint().setX(this.getEndPoint().getX());
		line.getEndPoint().setY(this.getEndPoint().getY());
		line.getEndPoint().setColor(this.getEndPoint().getColor());

		line.setColor(this.getColor());

		return line;

	}

	
	
	
	
	

}
