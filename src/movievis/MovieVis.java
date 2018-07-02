/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movievis;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Radu
 */
public class MovieVis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        final JFrame frame = new JFrame("Event handling");
        
        Container controlPanel = new ControlPanel();
        
        frame.add(controlPanel, BorderLayout.WEST);

        // quit the application when the game window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        
        
        MovieNetworkController controller = new MovieNetworkController();
        
        
        JTextArea textArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(textArea); 
        textArea.setEditable(false); 
        
        frame.add(textArea, BorderLayout.SOUTH);
        
        textArea.setText("blabla");
        
        frame.add(controller, BorderLayout.WEST);
        // display the world in the window
       
        // don't let the game window be resized
       // frame.setResizable(false);
        // size the game window to fit the world view

        
        MovieData movieData = new MovieData("data/ActorSmall.txt", "data/MovieSmall.txt");
        movieData.removeMovies(new MovieSelector(){
            @Override
            public boolean selected(Movie movie) {
                if (movie.rating < 8 || movie.releaseYear < 2010)
                    return true;
                return false;
            };
        });
        MovieNetworkViewer viewer = new MovieNetworkViewer(1000,1000, movieData);
         frame.add(viewer);
        
        //MovieNetworkViewer viewer = new MovieNetworkViewer(canvas, "C:\\Users\\Radu\\Documents\\moviedata");
        
        
        
        controller.setViewer(viewer);
        
        
        controller.getLayouterController().setNetworkLayouter(viewer.getLayouter());
      //  controller.getAdditionalSlot().add(layouterController);
      // frame.add(layouterController, BorderLayout.WEST);
        
        frame.pack();
        // make the window visible
        frame.setVisible(true);
        // get keyboard focus
        frame.requestFocus();
       
       //NetworkViewer viewer = new NetworkViewer(canvas, new TestNetwork1());
        
            
    }
    
}
