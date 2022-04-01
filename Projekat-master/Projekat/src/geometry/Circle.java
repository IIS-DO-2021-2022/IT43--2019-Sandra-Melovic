package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Circle extends SurfaceShape implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point center;
	protected int r;
	
	
	public Circle() {

	}
	
	public Circle(Point center, int r) {
		this.center=center;
		this.r=r;
	}
	
	public Circle(Point center, int r, boolean selected) {
		this(center,r);
		this.selected=selected;
	}

	public Circle(Point center, int r, boolean selected, Color color) {
		this(center, r, selected);
		setColor(color);
	}
	
	public Circle(Point center, int r, Color color, Color innerColor) {
		this(center, r);
		setColor(color);
		setInnerColor(innerColor);
	}
	
	public Circle(Point center, int r, boolean selected, Color color, Color innerColor) {
		this(center, r, selected, color);
		setInnerColor(innerColor);
		setSelected(selected);
	}
	


	public double area() {
		
		return r*r*Math.PI;
	}
	
	public double circumference() {
		
		return 2*r*Math.PI;
	}
	
	public boolean contains(int x, int y) {
		return center.distance(x, y) <= r; 
	}
	
	public boolean contains(Point p) {
		return this.contains(p.getX(),p.getY());
	}
	
	@Override
	public void moveTo(int x, int y) {
		center.moveTo(x, y);	
	}

	@Override
	public void moveBy(int byX, int byY) {
		center.moveBy(byX, byY);		
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawOval(this.center.getX() - r	, this.center.getY() - r, r*2, r*2); 
		this.fill(g);
		
		if (selected) {
			g.setColor(Color.BLUE);
			g.drawRect(this.center.getX() - 2, this.center.getY() - 2, 4, 4);
			g.drawRect(this.center.getX() - r - 2, this.center.getY() - 2, 4, 4);
			g.drawRect(this.center.getX() + r - 2, this.center.getY() - 2, 4, 4);
			g.drawRect(this.center.getX() - 2, this.center.getY() - r - 2, 4, 4);
			g.drawRect(this.center.getX() - 2, this.center.getY() + r - 2, 4, 4);
			
		}
	}
	
	@Override
	public String toString() {
		return "Circle" + ":" +  getCenter().getX()+ ","+ getCenter().getY()+ "," + getR() + "," + getColor().getRed()+"," + getColor().getGreen()+","+getColor().getBlue()  + "," + getInnerColor().getRed() + "," + getInnerColor().getGreen() + "," + getInnerColor().getBlue()+","+isSelected();
		//return "Circle: radius=" + r + "; x=" + center.getX() + "; y=" + center.getY() + "; edge color=" + getColor().toString().substring(14).replace('=', '-') + "; area color=" + getInnerColor().toString().substring(14).replace('=', '-');
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Circle) {
			Circle temp=(Circle) obj;
			if (center.equals(temp.center) && r==temp.r) {
				return true;
			}		 
		}
		return false;		
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof Circle) {
			return (int)(this.area() - ((Circle) o).area());
		}
		return 0;
	}
	
	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillOval(this.center.getX() - r	 + 1, this.center.getY() - r + 1, r*2 - 2, r*2 - 2);		
	}
	
	public Point getCenter() {
		return center;
	}
		
	public int getR() {
		return r;
	}
	
	public void setCenter(Point center) {
		this.center=center;
	}

	public void setR(int r) throws Exception {
		if (r > 0) {
			this.r = r;
		} else {
			throw new NumberFormatException("Radius has to be a value greater then 0!");
		}
		
	}
	
	@Override
	public Circle clone() {
		Circle circle = new Circle(new Point(), r, getColor(), getInnerColor());
		circle.getCenter().setX(this.getCenter().getX()); 
		circle.getCenter().setY(this.getCenter().getY()); 
		try {
			circle.setR(this.getR());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		circle.setInnerColor(this.getInnerColor());
		circle.setColor(this.getColor());
		return circle;
	}


	
	
	
	
	
	
	

}
