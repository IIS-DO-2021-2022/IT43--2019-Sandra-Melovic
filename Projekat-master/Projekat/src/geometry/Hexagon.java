package geometry;

import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Color;
import java.io.Serializable;

public class Hexagon implements Serializable
{
    private static final long serialVersionUID = 1L;
    private boolean selected;
    private int x;
    private int y;
    private int r;
    private Color borderColor;
    private Color areaColor;
    
    public Hexagon(final int x, final int y, final int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
    
    public void paint(final Graphics g) {
        final int[] xovi = new int[6];
        final int[] yoni = new int[6];
        final Polygon plg = new Polygon();
        for (int i = 0; i < 6; ++i) {
            xovi[i] = (int)(this.x + this.r * Math.cos(i * 2 * 3.141592653589793 / 6.0));
            yoni[i] = (int)(this.y + this.r * Math.sin(i * 2 * 3.141592653589793 / 6.0));
            plg.addPoint(xovi[i], yoni[i]);
        }
        g.setColor(this.areaColor);
        g.fillPolygon(plg);
        g.setColor(this.borderColor);
        g.drawPolygon(plg);
        if (this.selected) {
            g.setColor(Color.BLUE);
            for (int i = 0; i < 6; ++i) {
                g.drawRect(xovi[i] - 2, yoni[i] - 2, 5, 5);
            }
        }
    }
    
    public boolean doesContain(final int x, final int y) {
        final Polygon plg = new Polygon();
        for (int i = 0; i < 6; ++i) {
            plg.addPoint((int)(this.x + this.r * Math.cos(i * 2 * 3.141592653589793 / 6.0)), (int)(this.y + this.r * Math.sin(i * 2 * 3.141592653589793 / 6.0)));
        }
        return plg.contains(x, y);
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getR() {
        return this.r;
    }
    
    public void setR(final int r) {
        this.r = r;
    }
    
    public Color getBorderColor() {
        return this.borderColor;
    }
    
    public Color getAreaColor() {
        return this.areaColor;
    }
    
    public void setBorderColor(final Color borderColor) {
        this.borderColor = borderColor;
    }
    
    public void setAreaColor(final Color areaColor) {
        this.areaColor = areaColor;
    }
    
    public boolean isSelected() {
        return this.selected;
    }
    
    public void setSelected(final boolean selected) {
        this.selected = selected;
    }
    
    
}