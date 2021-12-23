package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics;

public class Point extends Shape{

	private int x;
	private int y;
	
	
	public Point() {

	}
	
	public Point(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public Point(int x, int y, boolean selected) {
		this(x,y);
		this.selected=selected;
	}
	
	public Point(int x, int y, Color color) {
		this(x, y);
		setColor(color);
	}
	
	public Point(int x, int y, boolean selected, Color color) {
		this(x, y, selected);
		setColor(color);
		setSelected(selected);
	}

	
	public double distance(int x, int y) {
		int dX=this.x-x;
		int dY=this.y-y;
		double d=Math.sqrt(dX*dX+dY*dY);
		return d;		
	}
	
	public boolean contains(int x, int y) {
		return this.distance(x, y) <= 2;
	}
	
	@Override
	public void moveTo(int x, int y) {
		this.x=x;
		this.y=y;		
	}

	@Override
	public void moveBy(int byX, int byY) {
		x+=byX;
		y+=byY;		
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(x - 2, y, x + 2, y);
		g.drawLine(x, y + 2, x, y - 2);
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(x-2, y-2, 4, 4);			
		}
	}
	
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point temp=(Point) obj;
			if (x == temp.x && y == temp.y) {
				return true;			
		}			
	}
		return false;
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof Point) {
			return (int)(this.distance(0, 0) - ((Point) o).distance(0, 0));
		}
		return 0;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x=x;	
	}
	
	public void setY(int y) {
		this.y=y;	
	}
	
	public void setSelected(boolean selected) {
		this.selected=selected;	
	}
	public boolean isSelected() {
		return selected;	
	}
	
	@Override
	public Point clone() {
			Point point = new Point();
			point.setX(this.getX()); 
			point.setY(this.getY());
			point.setColor(this.getColor());
			return point;
		}
	

	

	
	
	
	
	
	
	
	
	

}
