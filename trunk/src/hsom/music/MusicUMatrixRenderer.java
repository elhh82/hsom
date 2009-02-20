
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
    public MusicUMatrixRenderer(SOMMap m, int numVals){
        super(m);
        numberMap();
    }

    /** Adds colours to each of the textareas in the map
       */    
    public void numberMap(){
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                float val = new Float(getUMatrix(i,j));
                SOMArea[i][j].setBackground(new Color(val, val, val, val));
            }
        }
    }
        
}
   