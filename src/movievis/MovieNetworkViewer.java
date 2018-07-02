/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movievis;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Radu
 */
public class MovieNetworkViewer extends NetworkViewer{
    
    MovieData movieData;
 
    
    public MovieNetworkViewer(int width, int height, MovieData data) {
        super(width, height, data);
        
        this.movieData = data;
    }
    

    public MovieNetworkViewer(int width, int height, String dirPath){
        super(width, height);
        
        movieData = new MovieData(dirPath + "/actors.txt", dirPath + "/movies.txt");
        this.setNetwork(movieData);
        this.layouter.load(dirPath + "/coords.txt");
        
    }
    
    
    public VisualItem constructNetworkNode(int index){
        VisualItem item = new VisualItem(){
            @Override
            public void render(Graphics2D g) {
                
                if (this.isSelected())
                    g.setColor(Color.red);
                else if (this.isHovered())
                    g.setColor(Color.pink);
                else
                    g.setColor(Color.black);
                g.fillOval((int)getX()-3, (int)getY()-3, 6, 6);
                
                g.setColor(Color.black);
                Movie m = movieData.getMovie(index);
                g.drawString( m.title, (int)getX(), (int)getY());
            }

            @Override
            public boolean isInside(double x, double y) {
                if (Math.sqrt((getX()-x)*(getX()-x) +  (getY()-y)*(getY()-y)) < 6)
                    return true;
                return false;
            }

            @Override
            protected void dragged(double x, double y) {
                layouter.setX(index, x);
                layouter.setY(index, y);
            }
            
            
        };
        return item;
    }

    @Override
    protected void renderPoint(Graphics2D g, int pointIndex, int x, int y) {
        
    }
    
    public void save(String dirPath){
        movieData.save(dirPath);
        layouter.save(dirPath + "/coords.txt");
    }
    
    public NetworkLayouter getLayouter(){
        return layouter;
    }
    

}
