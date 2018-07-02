/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movievis;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Radu
 */
public class NetworkViewer extends ItemViewer implements ActionListener{
    
    private ConnectedItems data;
    protected NetworkLayouter layouter;
   
    
    private int lc = 0;
    private int pickedPoint = -1;
    
    public NetworkViewer(int width, int height, ConnectedItems network){
        super(width, height);
        setNetwork(network);
    }
    
    public NetworkViewer(int width, int height){
        super(width, height);
    }
    
    protected void setNetwork(ConnectedItems network){
        this.data = network;
        for (int i=0; i<data.getItemCount(); i++)
            this.addItem(constructNetworkNode(i));
            
        layouter = new NetworkLayouter(this.data);
        Timer t = new Timer(50, this);
        t.start();       
    }
    
    public VisualItem constructNetworkNode(int index){
        VisualItem item = new VisualItem(){
            @Override
            public void render(Graphics2D g) {
                g.setColor(Color.black);
                g.fillOval((int)getX()-3, (int)getY()-3, 6, 6);
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
        
        item.setDraggable(true);
        
        return item;
    }
    
    private void updateNodePositions(){
         for (int  i=0; i<data.getItemCount(); i++)
             getItem(i).setPosition((int)layouter.getX(i), (int)layouter.getY(i));
    }

    @Override
    public void renderModelView(Graphics2D g) {
        layouter.iteration();
        updateNodePositions();
        lc++;
       
        renderLinks(g);
        
        super.renderModelView(g); //To change body of generated methods, choose Tools | Templates.
        
        renderForces(g);   
    }
    

    
    public void renderLinks(Graphics2D g){
        for (int i=0; i<data.getConnectionCount(); i++){
            Connection c = data.getConnection(i);
            int px1 = (int)layouter.getX(c.from);
            int py1 = (int)layouter.getY(c.from);
            int px2 = (int)layouter.getX(c.to);
            int py2 = (int)layouter.getY(c.to);
            
            g.setColor(new Color(100,100,100,(int)(100*c.strength)));
            
            g.drawLine(px1, py1, px2, py2);
        }        
    }
    
    
    public void renderPoints(Graphics2D g){
        g.setColor(Color.black);
        for (int  i=0; i<data.getItemCount(); i++)
            renderPoint(g, i, (int)layouter.getX(i), (int)layouter.getY(i));     
    }
    
    protected void renderPoint(Graphics2D g, int pointIndex, int x, int y){
        g.fillOval(x-3, y-3, 6, 6);
    }
    
    public void renderForces(Graphics2D g){
        for (int i=0; i<data.getItemCount(); i++){
            int x1 = (int)layouter.getX(i);
            int y1 = (int)layouter.getY(i);
            
            int x2 = (int)(layouter.getX(i) + layouter.getForceX(i));
            int y2 = (int)(layouter.getY(i) + layouter.getForceY(i));
            
            g.setColor(Color.red);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        requestRender();
        repaint();
    }
    
}
