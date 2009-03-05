
/**
  * SOMVector is a subclass of the standard java Vector class. It includes 
  * one extra functionality, where the Euclidean distance from this SOMVector 
  * to another SOMVector is measured.
  *
  * @author Edwin Law Hui Hean
  */

package hsom.music;

import hsom.core.SOMVector;


public class MusicVector<E> extends SOMVector<E>{
	
    /**
      * The sole constructor.
      */
    public MusicVector(){}
    

    /**
     * Overrides the toString method of a standard vector
     * @return Provides a formatted float version of the output
     */
    @Override
    public String toString(){
        String output = "[";
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setMinimumFractionDigits(1);
        nf.setMaximumFractionDigits(1);
        for(int i=0; i<size(); i++){
            output = output + nf.format(elementAt(i)) + ",";
        }
        output = output.substring(0,output.length()-1);
        output = output + "]";
        return output;
    }
		
}
					  
		
		 
	
	
	