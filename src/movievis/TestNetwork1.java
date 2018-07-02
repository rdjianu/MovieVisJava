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
public class TestNetwork1 extends ConnectedItems{

    public TestNetwork1(){
        this.computeConnections();
    }
    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    protected double computeConnection(int index1, int index2) {
        if (index1 == 0 && index2 == 1)
            return 1;
        if (index1 == 1 && index2 == 2)
            return 1;
        if (index1 == 2 && index2 == 3)
            return 1;
        if (index1 == 0 && index2 == 3)
            return 1;        
        if (index1 == 0 && index2 == 2)
            return 1;        
        if (index1 == 1 && index2 == 3)
            return 1; 
        return 0;
    }
    
    
    
}
