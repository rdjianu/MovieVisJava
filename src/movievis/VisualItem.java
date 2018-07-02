/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movievis;

import java.awt.Graphics2D;

/**
 *
 * @author Radu
 */
public abstract class VisualItem {
    
    private double x;
    private double y;
    
    private boolean selected;
    private boolean highlighted;
    private boolean hovered;
    
    private boolean draggable;
    
    public abstract void render(Graphics2D g);
    
    public abstract boolean isInside(double x, double y);
    
    public void setX(double x){
        this.x = x;
    }
    public double getX(){
        return x;
    }
    public void setY(double y){
        this.y = y;
    }
    public double getY(){
        return y;
    }
    
    protected void dragged(double x, double y){
        
    }
    
    
   
    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public boolean isDraggable(){
        return draggable;
    }
    
    public void setDraggable(boolean how){
        draggable = how;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }
    
    
    
    
}
