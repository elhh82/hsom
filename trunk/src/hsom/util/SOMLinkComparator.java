/**
  * This class is used to compare the SOMLinks so that it is sorted
  * with respect to the freqeuncy of the links.
  *
  * @author Edwin Law Hui Hean
  */

package hsom.util;

import hsom.core.SOMNode;
import java.util.*;


public class SOMLinkComparator implements Comparator{

    public int compare(Object obj1, Object obj2){

        int result=0; 
        Map.Entry e1 = (Map.Entry)obj1 ;
        Map.Entry e2 = (Map.Entry)obj2 ;
        
        //Sort based on values.
        Integer value1 = (Integer)e1.getValue();
        Integer value2 = (Integer)e2.getValue();

        if(value1.compareTo(value2)==0){

            String word1 =e1.getKey().toString();
            String word2= e2.getKey().toString();

            //Sort String in an alphabetical order
            result=word1.compareToIgnoreCase(word2);

        } 
        else{
            //Sort values in a descending order
            result=value2.compareTo( value1 );
        }

        return result;
    }

}
