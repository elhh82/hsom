
  /**NumbersRenderer extends SOMRenderer.
   * Puts colors into the boxes.
   *
   * @author Edwin Law Hui Hean
   */
   
package hsom.music;

import hsom.util.*;
import hsom.core.*;
import java.awt.Color;

public class MusicRenderer extends SOMRenderer{
	
    /** The constructor which just calls the superclass constructor
       * @param m		the SOMMap associated with this ColorRenderer
       */
    public MusicRenderer(SOMMap m){
        super(m);
        numberMap();
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
}
   