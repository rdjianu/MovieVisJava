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
public abstract class ConnectedItems {
    
    private Connection[] connections;
    private int[] degrees;
    
    public abstract int getItemCount();
    
    public int getConnectionCount(){
        return connections.length;
    };
    
    
    public Connection getConnection(int index){
        return connections[index];
    }
            
    protected abstract double computeConnection(int index1, int index2);
    
    public void computeConnections(){
     
        int cnt = 0;
        for (int i=0; i<getItemCount()-1; i++)
            for (int j=i+1; j<getItemCount(); j++)
                if (computeConnection(i,j) > 0)
                    cnt++;
        
        connections = new Connection[cnt];
        
        degrees = new int[getItemCount()];
        for (int i=0; i<degrees.length; i++)
            degrees[i] = 0;
        
        cnt = 0;
        for (int i=0; i<getItemCount()-1; i++)
            for (int j=i+1; j<getItemCount(); j++){
                double con = computeConnection(i,j);
                if (con > 0){
                    connections[cnt++] = new Connection(i,j,con); 
                    degrees[i]++; degrees[j]++;
                }
            }  
    }
    
}
