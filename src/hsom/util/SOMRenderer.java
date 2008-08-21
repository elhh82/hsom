
/** SOMRenderer builds a graphicsl representation of the SOM.
   * using a JTextArea.
   *
   * @author Edwin Law Hui Hean
   */
   
package hsom.util;

import hsom.core.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;


public class SOMRenderer{
	
    protected int width, height;	
    protected SOMMap map;
    protected JTextArea[][] SOMArea;
    private JPanel SOMPanel;

    /** The constructor for the SOMRenderer
       * @param m		the SOMMap associated to this renderer
       */
    public SOMRenderer(SOMMap m){

        width = m.getWidth();
        height = m.getHeight();
        map = m;
        SOMPanel = new JPanel(new SpringLayout());
        buildMap();

    }

    /** Builds the JTextArea using the SpringUtilities class
       */
    public void buildMap(){

        Border border = BorderFactory.createLineBorder(Color.black);

        SOMArea = new JTextArea[width][height]; 
        //SOMPanel.setMinimumSize(new java.awt.Dimension(600,600));
        for(int i=0; i< width; i++) {
            for(int j=0; j< height; j++){
                SOMArea[i][j] = new JTextArea(2,2);
                SOMArea[i][j].setEditable(false);
                SOMArea[i][j].setBorder(border);   
                SOMPanel.add(SOMArea[i][j]);  
            }          
        }
        SpringUtilities.makeCompactGrid(SOMPanel, width, height, 0, 0, 0, 0); 

    }

    /** Returns the panel containing the JTextArea map
       * @return 	the JPanel containing the visualized SOM
       */
    public JPanel getPanel(){
        return SOMPanel;
    }

    /** Returns the whole visualized SOM in the form of a
       * 2 dimensional JTextArea array.
       * @return 	a 2 dimensional JTextArea array
       */
    public JTextArea[][] getMap(){
        return SOMArea;
    }

}