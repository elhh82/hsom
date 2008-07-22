
/**
  * SOMMAP is the class that represents the SOM. The SOM is created
  * using a 2 dimensional array of SOM Nodes, with the array indexes
  * serving as the X and Y coordinates of the nodes.
  * <p>
  * The SOMMap class is also Serializable.
  *
  * @author Edwin Law Hui Hean
  */
 
package HSOM.Core;

import java.util.*;
import java.io.*;

 
public class SOMMap implements Serializable{
	
	//the array of SOMNodes that is used to create the 2D SOM
	private SOMNode[][] map;
	
	
	/** The sole constructor, initializes the map with random
	  * weight values at each node
	  * @param width		The width of the map (X coordinates)
	  * @param height 		The height of the map (Y coordinates)
	  * @param numWeights	The number of inputs that the map will take
	  */
		 
	public SOMMap(int width, int height, int numWeights){
		
		map = new SOMNode[width][height];
		
		for(int i=0; i<width; i++){
			
			for(int j=0; j<height; j++){				
				map[i][j] = new SOMNode(numWeights);
				map[i][j].setPos(i,j);				
			}
			
		}
		
	}	
		
	/** Returns the Node at the given point in the map
	  * @param	x	The x coordinates of the required node
	  * @param 	y	The y coordinates of the required node
	  * @return		The node pointed to by the 2 parameters
	  */
	public SOMNode getNode(int x, int y){
		
		return map[x][y];
		
	}
	
	/** Returns the width of the map
	  * @return		The width of the map
	  */
	public int getWidth(){
		
		return map.length;
		
	}
	
	/** Returns the height of the map
	  * @return		The height of the map
	  */
	public int getHeight(){
		
		return map[0].length;
		
	}
		
	/** Finds the Best Matching Unit for the given input vector
	  * @param	input	The input vector where the BMU is to be searched for
	  * @return 		The SOMNode corresponding to the Best Matching Unit
	  */
	public SOMNode getBMU(SOMVector<Float> input){
		
		/*Start at map position 0,0 and assume this as the
		 *BMU for now.
		 */		 
		SOMNode bmu = map[0][0];
		
		double bmuDist = input.euclideanDistance(bmu.getVector());
		double currentDist;
			
		// Step through the entire matrix and check the euclidean distance
		// between the input vector and the input node
		for (int i=0; i<this.getWidth(); i++){
			
			for (int j=0; j<this.getHeight(); j++){
				currentDist = input.euclideanDistance(map[i][j].getVector());
				
				if (currentDist < bmuDist){
					
					/*If the distance from the current node to the input vector
					 *is less than the distance to our current BMU, then set
					 *the current input as the new BMU.
					 */
					bmu = map[i][j];
					bmuDist = currentDist;					
				}				
			}			
		}		
		return bmu;
		
	}
	
}	
		
		
		