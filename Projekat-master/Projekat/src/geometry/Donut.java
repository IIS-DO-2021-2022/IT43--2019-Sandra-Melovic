package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;
import java.awt.Shape;


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
		setSelected(selected);
	}
	
	
	
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		super.fill(g);
		g.setColor(Color.WHITE);
		g.fillOval(getCenter().getX() - this.innerR, getCenter().getY() - this.innerR, this.innerR * 2, this.innerR * 2);
	}
	
	@Override
	public void draw(Graphics gr) {
		Graphics2D g = (Graphics2D)gr.create();
		 Shape donut= drawDonut();
		 
		  g.setColor(getInnerColor());
	        g.fill(donut);
	        g.setColor(getColor());
		 
		if (selected) {
			g.setColor(Color.BLUE);
			g.drawRect(getCenter().getX() - innerR - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() + innerR - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() - innerR - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() + innerR - 2, 4, 4);
			
		}
	}
	
	public Shape drawDonut() {
		Shape out = new Ellipse2D.Double(getCenter().getX() - getR(),
				 getCenter().getY() - getR(),
				 getR()*2, 
				 getR()*2);
		
		Shape in=new Ellipse2D.Double(
				getCenter().getX() - innerR, 
				getCenter().getY() - innerR,
	            innerR*2, 
	            innerR*2);
		
		Area area = new Area(out);
        area.subtract(new Area(in)); //subtracting the inner shape from the outer shape
        return area;
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
	@Override
	public Donut clone(){
		Donut donut = new Donut();
		donut.setCenter(this.getCenter());
		try {
			donut.setR(this.getR());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			donut.setInnerRadius(this.getInnerR());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		donut.setInnerColor(this.getInnerColor());
		donut.setColor(this.getColor());
		return donut;
	}
}
