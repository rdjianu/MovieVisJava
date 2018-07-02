/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movievis;

/**
 *
 * @author Radu
 */
public class Vector2D {
    private double x;
    private double y;
    
    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public void add(double x, double y){
        this.x += x;
        this.y += y;
    }
    
    public void add(Vector2D v){
        this.x += v.x;
        this.y += v.y;
    }
    
    public double length(){
        return Math.sqrt(x*x + y*y);
    }
    
    public void normalize(){
        double l = length();
        x /= l;
        y /= l;
    }
}
