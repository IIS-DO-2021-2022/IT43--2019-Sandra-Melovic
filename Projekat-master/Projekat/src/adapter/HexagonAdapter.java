package adapter;

import java.awt.Color;
import java.awt.Graphics;

import geometry.Hexagon;
import geometry.SurfaceShape;

public class HexagonAdapter extends SurfaceShape{
	
	private Hexagon hexagon;
	
	public HexagonAdapter() {
			
		}
	
	public HexagonAdapter(Hexagon hexagon) {
		this.hexagon=hexagon;
		
	}

	@Override
	public void moveTo(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveBy(int byX, int byY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public double area() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
		
	}

	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}

	@Override
	public String toString() {
		return  "Center: " + hexagon.getX() +hexagon.getY() + ", radius: " + hexagon.getR();
	}
	
	public int getX() {
        return hexagon.getX();
    }
    
    public void setX(final int x) {
    	hexagon.setX(x);
    }
    
    public int getY() {
        return hexagon.getY();
    }
    
    public void setY(final int y) {
    	hexagon.setY(y);
    }
    
    public int getR() {
        return hexagon.getR();
    }
    
    public void setR(final int r) {
    	hexagon.setR(r);;
    }
    
    public Color getBorderColor() {
        return hexagon.getBorderColor();
    }
    
    public Color getAreaColor() {
        return hexagon.getAreaColor();
    }
    
    public void setBorderColor(final Color borderColor) {
    	hexagon.setBorderColor(borderColor);
    }
    
    public void setAreaColor(final Color areaColor) {
    	hexagon.setAreaColor(areaColor);
    }
    
    public boolean isSelected() {
        return hexagon.isSelected();
    }
    
    public void setSelected(final boolean selected) {
    	hexagon.setSelected(selected);
    }
    
    public HexagonAdapter clone() {
		HexagonAdapter hexagon2 = new HexagonAdapter();
		hexagon2.setX(this.getX());
		hexagon2.setY(this.getY());
		hexagon2.setR(this.getR());
		hexagon2.setColor(this.getColor());
		hexagon2.setAreaColor(this.getAreaColor());
		return hexagon2;
	}
    

}
