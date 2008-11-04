
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

import hsom.util.SOMLinkComparator;
import java.util.*;
import java.io.*;


public class SOMNode implements Serializable{
	
    //the weights and the X and Y position of this Node
    private SOMVector<Float> weights;
    private int xPos, yPos;
    private SOMMap map;
    private int mapNumber;
    
    //the links from the current node to nodes in the upper and lower maps
    //together with their frequency
    Map <String, Integer> upLinks = new HashMap<String, Integer>();
    Map <String, Integer> downLinks = new HashMap<String, Integer>();

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
    
    /**
     * Sets the map number for this node, corresponding to the map it belongs to
     * @param num the map number to set.
     */
    public void setMapNumber(int num){
        mapNumber = num;
    }
    
    /**
     * Returns the map number for the node corresponding to this map
     * @return the mapnumber for this node's map
     */
    public int getMapNumber(){
        return mapNumber;
    }

    /** Sets the X and Y coordinates in the current node in the map
      * @param x	The x coordinates to be set to the node
      * @param y	The y coordinates to be set to the node
      */
    public void setPos(int x, int y){

        xPos = x;
        yPos = y;

    }
    
    /** Sets the map there this node is located in
     * @param m The map to set this node to
     */
    public void setMap(SOMMap m){
        
        map = m;
        
    }
    
    /**
     * Returns the map where this node belongs to
     * @return The map where this node belongs to
     */
    public SOMMap getMap(){
        
        return map;
   
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
     * @return the string containing the x and y position of the node
     */
    @Override
    public String toString(){

        return "<" + xPos + "," + yPos + ">";
    }
    
    /** Returns the link specified (up or down) sorted in order
     * or its frequency
     * @param up    Specifies if we want to return the up links (true) or down
     *              links (false).
     * @return      An array of the linked SOMNodes in order of their frequency
     */
    @SuppressWarnings("unchecked")
    public String[] getLinks(boolean up){
              
        ArrayList sortedArrayList = (up) ? new ArrayList(upLinks.entrySet()) :
                                          new ArrayList(downLinks.entrySet());
        Collections.sort(sortedArrayList, new SOMLinkComparator());
        
        String[] sortedNodes = new String[sortedArrayList.size()];
        
        for(int i=0; i<sortedArrayList.size(); i++)
        {
            sortedNodes[i] =    (String)((Map.Entry)sortedArrayList.get(i)).
                                getKey();
        }        
        
        return sortedNodes;
        
    }
    
    /**
     * Links this node with another node, either above or below it.
     * If the link already exists, the frequency is incresed, if not, a new link
     * is created.
     * @param up    Determines if we are setting the upLinks or downLinks
     * @param nodeToLink    The node to link from, from this node
     * @param i The position of this node, if its the first, second etc in the list
     */
    
    public void setLink(boolean up, SOMNode nodeToLink, int i){
        String nodeString = nodeToLink.getMapNumber() + "," + String.valueOf(i) + "," + nodeToLink.toString().substring(1,nodeToLink.toString().length()-1); 
        if(up){
            Integer freq = upLinks.get(nodeToLink);
            upLinks.put(nodeString, (freq == null) ? 1 : freq + 1);
        }
        else{
            Integer freq = downLinks.get(nodeToLink);
            downLinks.put(nodeString, (freq == null) ? 1 : freq + 1);            
        }
        
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
		
		
	
	