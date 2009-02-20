
  /**NumbersRenderer extends SOMRenderer.
   * Puts colors into the boxes.
   *
   * @author Edwin Law Hui Hean
   */
   
package hsom.music;

import hsom.util.*;
import hsom.core.*;
import java.awt.Color;

public class MusicUMatrixRenderer extends SOMRenderer{
	
    /** The constructor which just calls the superclass constructor
       * @param m		the SOMMap associated with this ColorRenderer
       */
    public MusicUMatrixRenderer(SOMMap m, float treshold){
        super(m);
        numberMap(treshold);
    }

    /** Adds colours to each of the textareas in the map
       */    
    public void numberMap(float treshold){
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                float val = new Float(getUMatrix(i,j));
                if((1-val) < treshold) val = 0;
                SOMArea[i][j].setBackground(new Color(1-val, 1-val, 1-val));
            }
        }
    }
        
}
   