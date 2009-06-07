
  /**NumbersRenderer extends SOMRenderer.
   * Puts colors into the boxes.
   *
   * @author Edwin Law Hui Hean
   */
   
package hsom.music;

import hsom.util.*;
import hsom.core.*;
import java.awt.Color;
import java.awt.Font;

public class MusicRenderer extends SOMRenderer{

    int[][] counter;
    
     /** The constructor which just calls the superclass constructor
       * @param m		the SOMMap associated with this ColorRenderer
       */
    public MusicRenderer(SOMMap m, int numVals){
        super(m);
        if(numVals == 4) numberMap();
        else if(numVals == 8) numberMap2();
    }

    /**
     * Create a blank rendering of the map
     * @param m The SOMMap associated with this renderer
     */
    public MusicRenderer(SOMMap m){
        super(m);
        counter = new int[m.getWidth()][m.getHeight()];
        for(int i=0; i<SOMArea.length; i++){
            for(int j=0; j<SOMArea[0].length; j++){
                SOMArea[i][j].setFont(new Font("Arial", Font.BOLD, 12));
                SOMArea[i][j].setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
                SOMArea[i][j].setAlignmentY(java.awt.Component.CENTER_ALIGNMENT);
                setNodeColor(i,j,java.awt.Color.WHITE);
                setNodeText(i,j,"");
                counter[i][j] = 0;
            }
        }
    }

    /**
     * Sets the colour of a node of the map
     * @param x The x coordinate
     * @param y The y coordinate
     * @param c The color to set to
     */
    public void setNodeColor(int x, int y, Color c){
        SOMArea[x][y].setBackground(c);
    }

    /**
     * Sets the text of a node of the map
     * @param x The x coordinate
     * @param y The y coordinate
     * @param s The string to set to
     */
    public void setNodeText(int x, int y, String s){
        counter[x][y]++;
        String temp = SOMArea[x][y].getText();
        if(temp.equals("") || temp.equals(s)) SOMArea[x][y].setText(s);
        else if(temp.equals("@") && s.equals("0")) SOMArea[x][y].setText("Q");
        else if(temp.equals("0") && s.equals("@")) SOMArea[x][y].setText("Q");
        else if(temp.equals("Q") && (s.equals("@") || s.equals("0"))) SOMArea[x][y].setText("Q");
        else SOMArea[x][y].setText("X");
    }

    /**
     * Returns the colour of a node of the map
     * @param x The x coordinate
     * @param y The y coordinate
     * @return
     */
    public Color getNodeColored(int x, int y){
        return SOMArea[x][y].getBackground();
    }

    /** Adds colours to each of the textareas in the map
       */    
    public void numberMap(){
        for(int i=0; i<width; i++){
            for(int j=0; j<width; j++){
                SOMVector<Float> temp = map.getNode(i,j).getVector();
                float r = (Float)temp.elementAt(0);
                float g = (Float)temp.elementAt(1);
                float b = (Float)temp.elementAt(2);
                float a = ((Float)temp.elementAt(2)+9)/10;
                SOMArea[i][j].setBackground(new Color(r,g,b,a));
            }
        }
    }
    
    /** Adds colours to each of the textareas in the map
       */    
    public void numberMap2(){
        for(int i=0; i<width; i++){
            for(int j=0; j<width; j++){
                SOMVector<Float> temp = map.getNode(i,j).getVector();
                float r = (Float)temp.elementAt(0) + (Float)temp.elementAt(1);
                float g = (Float)temp.elementAt(2) + (Float)temp.elementAt(2);
                float b = (Float)temp.elementAt(3) + (Float)temp.elementAt(4);
                float a = ((Float)temp.elementAt(5) + (Float)temp.elementAt(6) + 9)/10;
                if(r > 1) r = 1;
                if(g > 1) g = 1;
                if(b > 1) b = 1;
                if(a > 1) a = 1;
                SOMArea[i][j].setBackground(new Color(r,g,b,a));
            }
        }
    }
}
   