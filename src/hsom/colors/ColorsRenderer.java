
/** ColorsRenderer extends SOMRenderer.
   * Puts colors into the boxes.
   *
   * @author Edwin Law Hui Hean
   */
   
package hsom.colors;

import hsom.util.*;
import hsom.core.*;
import java.awt.Color;

public class ColorsRenderer extends SOMRenderer{
	
    /** The constructor which just calls the superclass constructor
       * @param m		the SOMMap associated with this ColorRenderer
       */
    public ColorsRenderer(SOMMap m){
        super(m);
        colorMap();
    }

    /** Adds colours to each of the textareas in the map
       */
    public void colorMap(){
        for(int i=0; i<width; i++){
            for(int j=0; j<width; j++){
                SOMVector<Float> temp = map.getNode(i,j).getVector();
                float r = (Float)temp.elementAt(0);
                float g = (Float)temp.elementAt(1);
                float b = (Float)temp.elementAt(2);
                SOMArea[i][j].setBackground(new Color(r,g,b));
            }
        }
    }
}
   