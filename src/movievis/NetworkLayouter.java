/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movievis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 *
 * @author Radu
 */
public class NetworkLayouter {
    
    private double[] posX, posY, fx, fy;
    private ConnectedItems items;
    
    private double minX, minY, maxX, maxY;
    
    private boolean stopped;
    private double maxStep;
    private double repulsionForce;
    private double attractionForce;

    public double getMaxStep() {
        return maxStep;
    }

    public void setMaxStep(double maxStep) {
        this.maxStep = maxStep;
    }

    public double getRepulsionForce() {
        return repulsionForce;
    }

    public void setRepulsionForce(double repulsionForce) {
        this.repulsionForce = repulsionForce;
    }

    public double getAttractionForce() {
        return attractionForce;
    }

    public void setAttractionForce(double attractionForce) {
        this.attractionForce = attractionForce;
    }
    
    public NetworkLayouter(ConnectedItems items){
        
        this.stopped = false;
        this.attractionForce = 10; 
        this.repulsionForce = 10;
        this.maxStep = 5;
        
        posX = new double[items.getItemCount()]; 
        posY = new double[items.getItemCount()]; 
        for (int i=0; i<items.getItemCount(); i++){
            posX[i] = Math.random()*500;
            posY[i] = Math.random()*500;
        }
        this.items = items;
    }
    
    public void iterate(){
        if (!stopped){
            computeForces();
            applyForces();  
        }
    }
    
    public void computeForces(){
        fx = new double[items.getItemCount()];
        fy = new double[items.getItemCount()];
 	for (int i=0; i<fx.length; i++){
		fx[i] = 0;
		fy[i] = 0;
	}
		
		
	for (int i=0; i<items.getConnectionCount(); i++){
            Connection c = items.getConnection(i);
            double x1 = posX[c.from];
            double y1 = posY[c.from];
            double x2 = posX[c.to];
            double y2 = posY[c.to];
			
            double vx = x1 - x2;
            double vy = y1 - y2;
			
            double vl = Math.sqrt(vx*vx + vy*vy);
			
            while (vl == 0){
		x2 = x2 + Math.random();
		y2 = y2 + Math.random();
		vx = x1 - x2;
		vy = y1 - y2;				
		vl = Math.sqrt(vx*vx + vy*vy);
            }
			
            double fmag = -attractionForce * 0.01 * vl;
			
            fx[c.from] += fmag * vx;
            fy[c.from] += fmag * vy;
            fx[c.to] -= fmag * vx;
            fy[c.to] -= fmag * vy;			
	}
		
		
	for (int i=0; i<items.getItemCount(); i++){
            for (int j=0; j<items.getItemCount(); j++){
		if (i == j) continue;
		
                double x1 = posX[i];
		double y1 = posY[i];
		double x2 = posX[j];
		double y2 = posY[j];
				
		double vx = x1 - x2;
		double vy = y1 - y2;
				
		double vl = Math.sqrt(vx*vx + vy*vy);
				
		while (vl == 0){
                    x2 = x2 + Math.random();
                    y2 = y2 + Math.random();
                    vx = x1 - x2;
                    vy = y1 - y2;				
                    vl = Math.sqrt(vx*vx + vy*vy);
		}
			
		double fmag = -repulsionForce * 10000. / (vl*vl);
				
		fx[i] -= fmag * vx;
		fy[i] -= fmag * vy;
		fx[j] += fmag * vx;
		fy[j] += fmag * vy;	
				
            }		
        }
        
    }
    
    public void applyForces(){
        
        
        
        for (int i=0; i<fx.length; i++){
            
            double mag = Math.sqrt(fx[i]*fx[i] + fy[i]*fy[i]);
            if (mag > maxStep){
                fx[i] = maxStep*fx[i]/mag;
                fy[i] = maxStep*fy[i]/mag;
            }
            
            posX[i] += fx[i];
            posY[i] += fy[i];
	}
    }
    
    public double getX(int index){
       return posX[index];// - minX;
        //return (positions[index].getX() - minX) / (maxX-minX);
    }
    public double getY(int index){
        return posY[index];// - maxY;
        //return (positions[index].getY() - minY) / (maxY - minY);
    }
    
    public void setX(int index, double x){
        posX[index] = x;
    }
    
    public void setY(int index, double y){
        posY[index] = y;
    }
    
    public double getForceX(int index){
        return fx[index];
    }
    
    public double getForceY(int index){
        return fy[index];
    }    
    
    public void iteration(){
        if (!stopped){
        System.out.println("iteration");
        computeForces();
        applyForces();
        }
    }
    
    public void save(String filePath){
        
        try{
        FileWriter writer = new FileWriter(filePath);
        
        for (int i=0; i<posX.length; i++){
            writer.write(posX[i] + "\t" + posY[i]);
            if (i != posX.length-1)
                writer.write("\n");
        }
        
        writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void load(String filePath){
                
        BufferedReader reader;
        
        try{
            
                       
            reader = new BufferedReader(new FileReader(filePath));
            String line = null;
            int index = 0;
            while ((line = reader.readLine()) != null){
                String[] fields = line.split("\t");
                
                setX(index, Double.parseDouble(fields[0]));
                setY(index, Double.parseDouble(fields[1]));
                index++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void pause(){
        stopped = true;
    }
    public void play(){
        stopped = false;
    }
    
}
