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
public class Connection {
    public int from;
    public int to;
    public double strength;
    
    public Connection(int f, int t, double s){
        from = f;
        to = t;
        strength = s;
    }
}
