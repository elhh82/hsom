
/**
  * SOMVector is a subclass of the standard java Vector class. It includes 
  * one extra functionality, where the Euclidean distance from this SOMVector 
  * to another SOMVector is measured.
  *
  * @author Edwin Law Hui Hean
  */

package hsom.core;

import java.util.*;


public class SOMVector<E> extends Vector<E>{
	
    /**
      * The sole constructor.
      */
    public SOMVector(){}

    /**
      * Calculates the Euclidean Distance between this node and the 
      * supplied node. Both SOMVectors should be of the same length.
      * The difference is calculated for each element of the vectors
      * and the sum-squared distance for all elements is returned.
      *
      * @param node 	the other SOMVector where the distance will be 
      *			measured to
      * @return		a double value containing the sum squared distance
      *			of all the elements in the vector
      */
    public double euclideanDistance(SOMVector<Float> node){

        double distance = 0;
        double temp = 0;

        for(int i=0; i<size(); i++){

                temp = (Float)elementAt(i) - (Float)node.elementAt(i);	
                distance += temp*temp;			
        }

        return distance;
    }
    
    /**
      * Calculates the Euclidean Distance between this node and the 
      * supplied node. Both SOMVectors should be of the same length.
      * The difference is calculated for each element of the vectors
      * and the sum-squared distance for all elements is returned.
      *
      * @param node 	the other SOMVector where the distance will be 
      *			measured to
      * @param pos      the positions where we are to check
      * @return		a double value containing the sum squared distance
      *			of all the elements in the vector
      */
    public double euclideanDistancePartial(SOMVector<Float> node, boolean[] pos){

        double distance = 0;
        double temp = 0;

        for(int i=0; i<size(); i++){
            if(pos[i]){
                temp = (Float)elementAt(i) - (Float)node.elementAt(i);	
                distance += temp*temp;	
            }
        }

        return distance;
    }
		
}
					  
		
		 
	
	
	