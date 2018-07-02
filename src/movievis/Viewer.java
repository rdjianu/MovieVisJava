/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movievis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Radu
 */
public abstract class Viewer extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
	
	
	private double zoom;
	private double translatex;
	private double translatey;
	
	BufferedImage image;
	
	//keeping track of dragging; 
	public boolean rightButtonDown = false;
	public int dragPrevX = 0;
	public int dragPrevY = 0;
	
	public int zoomOriginX = 0;
	public int zoomOriginY = 0;
	
	public AffineTransform transform;
		
	boolean antialiasingSet = false;
        
        private int width;
        private int height;        
	
	public Viewer(int width, int height)
	{
            zoom = 1;
	    translatex = 0;
            translatey = 0;
	    transform = AffineTransform.getTranslateInstance(0, 0);
	
			
            this.width = width;
            this.height = height;
            
            this.setWidth(width);
            this.setHeight(height);
            this.setMinimumSize(new Dimension(width, height));
            this.setPreferredSize(new Dimension(width, height));

            
            this.addKeyListener(this);
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            
	}


        
        public void requestRender(){
            this.renderCanvas();
        }
        
        protected Color getBackgroundColor(){
            return Color.white;
        }

    @Override
    public void update(Graphics g) {
        if (image == null)
            this.renderCanvas();
        
    }
        
    @Override
    protected void paintComponent(Graphics g) {
        if (image == null)
            this.renderCanvas();
        g.drawImage(image, 0, 0, this);
    }        
	
	
    private void renderCanvas()
    {	
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gc = (Graphics2D)image.getGraphics();

        gc.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        gc.setColor(getBackgroundColor());
        gc.fillRect(0, 0, this.getWidth(), this.getHeight()); // fill in background
           		
        gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

        gc.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


        zoomOriginX = image.getWidth()/2;
        zoomOriginY = image.getHeight()/2;

	//sets the proper transformation for the Graphics context that will passed into the rendering function	
	gc.setTransform(AffineTransform.getTranslateInstance(zoomOriginX, zoomOriginY));	          
	gc.transform(AffineTransform.getScaleInstance(zoom,zoom));		           
	gc.transform(AffineTransform.getTranslateInstance(translatex-zoomOriginX, translatey-zoomOriginY));
		
	transform = gc.getTransform();
		
        renderModelView(gc);
		
	gc.setTransform(AffineTransform.getTranslateInstance(0, 0));
         
        renderScreenView(gc);
	}
    
    
    public abstract void renderModelView(Graphics2D g);
    public void renderScreenView(Graphics2D g){
        
    }

	

	
	public void setWidth(int w)
	{
		width = w;
	}
	public void setHeight(int h)
	{
		height = h;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}

		
        protected void keyTyped(int code, int modifiers){
            
        }

	//for 2D viewers we add automatic zooming and panning; this can happen using the arrow keys and +,- keys; the key strokes are also sent to the viewer as interaction event
	protected void keyPressed(int code, int modifiers)
	{
	}

	protected void keyReleased(int code, int modifiers) {
		
	}
	

	//we add listeners both to send those events to the 2D viewer but also to implement zooming an panning; there's a 25pixel offset on the y-axes because of the title bar of ViewerContainers; for zooming and panning we use
	//the raw coordinates; when passing the coordinates to the viewer they need to be transformed so they are in the Viewer's space, hence the operations based on translate and zoom.
	 
	public void mouseEntered(int ex, int ey) {
	}
        
        
	public void mouseExited(int ex, int ey) {
	}
	
        protected boolean mouseClicked(int x, int y, int button){
            return false;
        }
        
	protected boolean mousePressed(int ex, int ey, int button) {
            return false;
	}
	
	protected boolean mouseReleased(int ex, int ey, int button){
            return false;
	}	
	

	protected boolean mouseDragged(int ex, int ey, int pex, int pey, int button) {
            return false;
	}
	
	protected boolean mouseMoved(int ex, int ey){
            return false;
	}
	
	
	public Point2D modelToScreen(Point2D modelPoint)
	{
		AffineTransform[] transform = null;
		
		Point2D p = transform[0].transform(modelPoint, null);
		return new Point2D.Double((int)p.getX(), (int)p.getY());
		
	}
	
	
	public Point2D screenToModel(Point2D screenPoint)
	{
		Point2D p;
		try {
			p = transform.inverseTransform(screenPoint, null);
			return new Point2D.Double((int)p.getX(), (int)p.getY());
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public double getZoom(){
		return zoom;
	}
	
	public void setZoom(double z)
	{
		zoom = z;
	}
	
	
	public void setTranslation(double x, double y)
	{
		translatex = x;	
		translatey = y;
	}
	
	public Point2D getTranslation()
	{
		return new Point2D.Double(translatex, translatey);
	}

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseClicked(e.getX(), e.getY(), e.getButton());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try{
            Point2D.Double tp = new Point2D.Double();
            transform.inverseTransform(new Point2D.Double(e.getX(),e.getY()), tp);
            int x = (int)tp.x;
            int y = (int)tp.y;
		
            boolean processed = mousePressed(x,y, e.getButton());
						
            dragPrevX = e.getX();
            dragPrevY = e.getY();
			
            if (e.getButton() == MouseEvent.BUTTON3){
		rightButtonDown = true;
		zoomOriginX = dragPrevX;
		zoomOriginY = dragPrevY;
            }
                        
            this.renderCanvas();
                        
	}
        catch(Exception ee){
            ee.printStackTrace();
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
	try{
            Point2D.Double tp = new Point2D.Double();
            transform.inverseTransform(new Point2D.Double(e.getX(), e.getY()), tp);
            int x = (int)tp.x;
            int y = (int)tp.y;
		
            boolean processed = mouseReleased(x,y, e.getButton());
            
			
            if (e.getButton() == MouseEvent.BUTTON3)
                rightButtonDown = false;
                        
            this.renderCanvas();
	}
	catch(Exception ee){
            ee.printStackTrace();
	} 
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
	try{
            
            Point2D.Double tp = new Point2D.Double();
            transform.inverseTransform(new Point2D.Double(e.getX(), e.getY()), tp);
            int x = (int)tp.x;
            int y = (int)tp.y;
			
            transform.inverseTransform(new Point2D.Double(dragPrevX, dragPrevY), tp);
            int px = (int)tp.x;
            int py = (int)tp.y;		
		
            if (!mouseDragged(x,y, px, py, rightButtonDown ? 3 : 1))
            {
            	if (rightButtonDown) //zoom
		{
                    if (Math.abs(e.getX()-dragPrevX) > Math.abs(e.getY()-0 - dragPrevY))
                    	zoom *= (1+(e.getX()-dragPrevX)/100.);
                    else
			zoom *= (1+(e.getY()-0-dragPrevY)/100.);
		
                    if (zoom <0.01)
			zoom = 0.01;
		}
		else
		{
                    System.out.println(translatex);
                    translatex += (e.getX()-dragPrevX)/zoom;
                    translatey += (e.getY()-dragPrevY)/zoom;
		}
		//this.render();
            }
            
            this.renderCanvas();
            this.repaint();
            dragPrevX = e.getX();
            dragPrevY = e.getY();
	}
	catch(Exception ee){
            ee.printStackTrace();
	} 
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	//AffineTransform transform = Aff;
			
	try{
            Point2D.Double tp = new Point2D.Double();
            transform.inverseTransform(new Point2D.Double(e.getX(),e.getY()), tp);
            int x = (int)tp.x;
            int y = (int)tp.y;		
		
           
	}
	catch(Exception ee){
            ee.printStackTrace();
	}
    }

    @Override
    public void keyTyped(KeyEvent e) {
        keyTyped(e.getKeyCode(), e.getModifiers());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP){	
            translatey++; this.renderCanvas();
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            translatey--;this.renderCanvas();	
        }
	else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            translatex--;this.renderCanvas();	
        }
	else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            translatex++;this.renderCanvas();	
        }
	else if (e.getKeyCode() == KeyEvent.VK_PLUS){
            zoom = zoom + 0.1;this.renderCanvas();	
        }
	else if (e.getKeyCode() == KeyEvent.VK_MINUS){
            zoom = zoom - 0.1;
            if (zoom < 0.1)
                zoom = 0.1;
            this.renderCanvas();
        }
              
        keyPressed(e.getKeyCode(), e.getModifiers());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyReleased(e.getKeyCode(), e.getModifiers());
    }

}
