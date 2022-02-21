package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.awt.geom.Area;
import java.awt.Shape;


public class Donut extends Circle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	public Donut(Point center, int r, int innerR, Color color, Color innerColor) { 
		this(center, r, innerR);
		setColor(color);
		setInnerColor(innerColor);
	}
	
	
	/*
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		System.out.println("u fillu");
		super.fill(g);
		g.fillOval(getCenter().getX() - this.innerR, getCenter().getY() - this.innerR, this.innerR * 2, this.innerR * 2);
	}*/
	
	private void fill(Graphics g, Color innerColor, Area donut) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setColor(innerColor);
		graphics2d.fill(donut);
		
	}
	
	private void colorOutline(Graphics g, Color outlineColor, Area donut) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setColor(outlineColor);
		graphics2d.draw(donut);
		
	}
	
	@Override
	public void draw(Graphics g) {
		Area donut = drawDonut();
		
		this.fill(g, getInnerColor(), donut);
		
		this.colorOutline(g, getColor(), donut);
	        
		 
		if (selected) {
			g.setColor(Color.BLUE);
			g.drawRect(getCenter().getX() - innerR - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() + innerR - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() - innerR - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() + innerR - 2, 4, 4);
			
		}
	}
	
	public Area drawDonut() {
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
		return "Donut" + ":" +  getCenter().getX()+ ","+ getCenter().getY()+ "," + getR() + "," + getInnerR() + "," + getColor().getRed()+"," + getColor().getGreen()+","+getColor().getBlue()  + "," + getInnerColor().getRed() + "," + getInnerColor().getGreen() + "," + getInnerColor().getBlue()+","+isSelected();
		//return "Donut: radius=" + getR() + "; x=" + getCenter().getX() + "; y=" + getCenter().getY() + "; edge color=" + getColor().toString().substring(14).replace('=', '-') + "; area color=" + getInnerColor().toString().substring(14).replace('=', '-') + "; inner radius=" + innerR;
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
