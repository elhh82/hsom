
  /**NumbersRenderer extends SOMRenderer.
   * Puts colors into the boxes.
   *
   * @author Edwin Law Hui Hean
   */
   
package hsom.music;

import hsom.util.*;
import java.awt.Color;

public class MusicUMatrixRenderer extends SOMRenderer{

    SOMMusicMap musicMap;
    /** The constructor which just calls the superclass constructor
       * @param m		the SOMMap associated with this ColorRenderer
       */
    public MusicUMatrixRenderer(SOMMusicMap m, float treshold){
        super(m.getMap());
        musicMap = m;
        numberMap(treshold);
    }

    /** Adds colours to each of the textareas in the map
       */    
    public void numberMap(float treshold){
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                float val = new Float(musicMap.getUMatrix(i,j));
                if((1-val) < treshold) SOMArea[i][j].setBackground(new Color(0, 0, 0));
                else SOMArea[i][j].setBackground(new Color(1-val, 1-val, 1-val));
                /*float temp = 1-val;
                temp = new Float((java.lang.Math.pow(100, new Double(temp)) -1)/99);
                SOMArea[i][j].setBackground(new Color(temp, temp, temp));*/
                //SOMArea[i][j].setBackground(new Color(1/(1+val), 1/(1+val), 1/(1+val)));
            }
        }
    }
        
}
   