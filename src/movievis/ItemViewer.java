/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movievis;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Radu
 */
public class ItemViewer extends Viewer{
    
    private ArrayList<VisualItem> items;
    
    private VisualItem picked;
    
    public ItemViewer(int width, int height){
        super(width, height);
        picked = null;
        items = new ArrayList<VisualItem>();
    }
    
    public void addItem(VisualItem item){
        items.add(item);
    }
    
    public VisualItem getItem(int index){
        return items.get(index);
    }

    @Override
    public void renderModelView(Graphics2D g) {
        for (int i=0; i<items.size(); i++)
            items.get(i).render(g);
    }

    @Override
    protected boolean mouseDragged(int ex, int ey, int pex, int pey, int button) {
        if (picked != null && button == 1){
            picked.setPosition(ex, ey);
            picked.dragged(ex, ey);
            return true;
        }
        
        return false;
    }

    @Override
    protected boolean mouseReleased(int ex, int ey, int button) {
        if (button != 1)
            return false;
        
        if (picked != null){
            picked = null;
            return true;
        }
        
        return false;
    }

    @Override
    protected boolean mousePressed(int ex, int ey, int button) {
        
        if (button != 1)
            return false;
        
        for (int i=0; i<items.size(); i++)
            if (items.get(i).isDraggable() && items.get(i).isInside(ex, ey)){
                picked = items.get(i);
                return true;
            }
        
        return false;                  
    }
    
    
}
