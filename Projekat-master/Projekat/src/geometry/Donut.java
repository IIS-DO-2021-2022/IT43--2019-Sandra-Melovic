package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Donut extends Circle {

	private int innerR;
	
	
	public Donut() {
		
	}
	
	public Donut(Point center, int r, int innerR) {
		super(center, r);
		this.innerR = innerR;
	}
	
	public Donut (Point center, int r, int innerR, boolean selected) {
		this(center, r, innerR);
		this.selected = selected;
	}
	
	public Donut(Point center, int r, int innerR, boolean selected, Color color) { 
		this(center, r, innerR, selected);
		setColor(color);
	}
	
	public Donut(Point center, int r, int innerR, boolean selected, Color color, Color innerColor) { 
		this(center, r, innerR, selected, color);
		setInnerColor(innerColor);
	}
	
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		super.fill(g);
		g.setColor(Color.WHITE);
		g.fillOval(getCenter().getX() - this.innerR, getCenter().getY() - this.innerR, this.innerR * 2, this.innerR * 2);
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		g.setColor(getColor());
		g.drawOval(getCenter().getX() - this.innerR, getCenter().getY() - this.innerR, this.innerR * 2, this.innerR * 2);
		
		if (selected) {
			g.setColor(Color.BLUE);
			g.drawRect(getCenter().getX() - innerR - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() + innerR - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() - innerR - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() + innerR - 2, 4, 4);
			
		}
	}
	
	@Override
	public boolean contains(int x, int y) {
		return super.contains(x, y) && getCenter().distance(x, y) >= innerR;
	}
	
	@Override
	public boolean contains(Point p) {
		return this.contains(p.getX(),p.getY());
	}
	
	@Override
	public double area() {
		return super.area() - innerR * innerR * Math.PI;
	}
	
	@Override
	public String toString() {
		return super.toString() + ", inner radius = " + innerR;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut temp = (Donut) obj;
			if (super.equals(new Circle(temp.getCenter(), temp.getR())) && temp.innerR == innerR) {
				return true;
			}
		}
		return false;
	}
	
	public int getInnerR() {
		return innerR;
	}	

	public void setInnerRadius(int innerR) throws Exception {
		if (innerR > 0) {
			this.innerR = innerR;
		} else {
			throw new NumberFormatException("Radius has to be a value greater then 0!");
		}
		
	}
}
