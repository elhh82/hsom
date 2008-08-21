
/**
  * SOMNode is the class that represents the functionality of one node of the SOM.
  * Each node contains a vector of weights, and its corresponding x and y position
  * on the 2D SOM map.
  * <p>
  * The SOMNode class is also Serializable.
  *
  * @author Edwin Law Hui Hean
  */
 
package hsom.core;

import java.util.Random;
import java.io.*;


public class SOMNode implements Serializable{
	
    //the weights and the X and Y position of this Node
    private SOMVector<Float> weights;
    private int xPos, yPos; 

    /** The sole constructor, initializes each weight to a random float value
      * between 0 and 1.
      * @param numWeights		The number of weights that this node will have
      */
    @SuppressWarnings("unchecked")
    public SOMNode(int numWeights){	

        Random rand = new Random();
        weights = new SOMVector();

        for(int i=0; i<numWeights; i++){						
                weights.addElement(rand.nextFloat());					
        }	

    }

    /** Sets the X and Y coordinates in the current node in the map
      * @param x	The x coordinates to be set to the node
      * @param y	The y coordinates to be set to the node
      */
    public void setPos(int x, int y){

        xPos = x;
        yPos = y;

    }

    /** Returns the X coordinates of the current node in the map
      * @return		the X coordinate
      */
    public int getX(){

        return xPos;

    }

    /** Returns the Y coordinates of the current node in the map
      * @return		the Y coordinate
      */
    public int getY(){

        return yPos;

    }


    /** Computes the Euclidean distance (in terms of position)
      * from this node to another node. The value returned is 
      * the squared distance.
      * @param node 	The node where the distance is to be measured
      *			from this node
      * @return		The squared Euclidean distance
      */	 
    public int distance(SOMNode node){

        int xDist, yDist;

        xDist = getX() - node.getX();
        xDist *= xDist;

        yDist = getY() - node.getY();
        yDist *= yDist;

        return xDist + yDist;	

    }

    /** returns the set of weights in a SOMVector
      * @return		The SOMVector containing all the weights
      */

    public SOMVector<Float> getVector(){

        return weights;

    }

    /** Adjusts the weights for this node according to the learning rate 
      * and its distance from BMU. The new weight is calculated using the
      * SOM training formula.
      * 	nw = d * r * (i - w)
      *	<ul>
      *	 <li>nw = the new weights
      *  <li>d = the distance of this node from the BMU
      *  <li>r = the learning rate
      *  <li>i = the input weights
      *  <li>w = the current weights
      * </ul>
      * @param input		The set of input weights
      * @param learningRate	The learning rate, calculated at the SOMTrainer
      * @param distance		The distance of this node from the BMU, calculated
      *				at the SOMTrainer
      */
    public void adjustWeights(SOMVector<Float> input, double learningRate, double distance){
        float wt, vw;
        for(int w=0; w<weights.size(); w++){
                wt = weights.get(w);
                vw = input.get(w);
                wt += distance * learningRate * (vw - wt);
                weights.set(w,new Float(wt));
        }

    }
    /** Prints out the x and y coordinates of this node
     * 
     */
    @Override
    public String toString(){

        return xPos + " " + yPos;
    }

    /** Prints out all the weights of this node in human readable form
      * @param range 	used adjusts the weights so that they correspond to the
      *			unnormalized values.
      */
    public void print(int range){
        System.out.print("[");
        for (int w=0; w<weights.size()-1; w++){	
                System.out.print(java.lang.Math.round((Float)weights.elementAt(w)*range));
                System.out.print(",");
        }
        System.out.print(java.lang.Math.round((Float)weights.elementAt(weights.size()-1)*range));
        System.out.println("]");
    }	
	

}
		
		
	
	