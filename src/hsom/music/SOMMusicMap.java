/**
 * This class is a container for the SOMMap with some added functionalities
 * to help with prediction.
 * @author Edwin Law Hui Hean
 */

package hsom.music;

import hsom.core.*;
import java.util.Vector;
import java.util.Random;
import java.io.*;

public class SOMMusicMap implements Serializable{
    
    SOMMap map;
    protected double[][] UMatrix;
    protected String[][] labels;
    
    //a constructor for the SOMMusicMap
    public SOMMusicMap(SOMMap m){
        map = m;
        
        //initialize and set the labels to blanks
        labels = new String[m.getWidth()][m.getHeight()];
        for(int x = 0; x<m.getWidth(); x++){
            for(int y=0; y<m.getHeight(); y++){
                labels[x][y] = "";
            }
        }

    }

    /**
     * Returns the SOMMap
     * @return returns a SOMMap
     */
    public SOMMap getMap(){
        return map;
    }

    /**
     * Calculates the UMatrix for the map
     */
    public void calcUMatrix(){

        double max = 0;
        UMatrix = new double[map.getWidth()][map.getHeight()];
        for(int x=0; x < map.getWidth(); x++){
            for(int y=0; y< map.getHeight(); y++){
                if(x==0 && y==0){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> rightNode = map.getNode(x+1,y).getVector();
                    SOMVector<Float> bottomNode = map.getNode(x,y+1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(rightNode)+
                                    centerNode.euclideanDistance(bottomNode))/2;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else if(x==map.getWidth()-1 && y==map.getHeight()-1){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> leftNode = map.getNode(x-1,y).getVector();
                    SOMVector<Float> topNode = map.getNode(x,y-1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(leftNode)+
                                    centerNode.euclideanDistance(topNode))/2;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else if(x==0 && y==map.getHeight()-1){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> rightNode = map.getNode(x+1,y).getVector();
                    SOMVector<Float> topNode = map.getNode(x,y-1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(rightNode)+
                                    centerNode.euclideanDistance(topNode))/2;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else if(x==map.getWidth()-1 && y==0){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> leftNode = map.getNode(x-1,y).getVector();
                    SOMVector<Float> bottomNode = map.getNode(x,y+1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(leftNode)+
                                    centerNode.euclideanDistance(bottomNode))/2;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else if(x==0){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> rightNode = map.getNode(x+1,y).getVector();
                    SOMVector<Float> topNode = map.getNode(x,y-1).getVector();
                    SOMVector<Float> bottomNode = map.getNode(x,y+1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(rightNode)+
                                    centerNode.euclideanDistance(topNode)+
                                    centerNode.euclideanDistance(bottomNode))/3;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else if(y==0){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> leftNode = map.getNode(x-1,y).getVector();
                    SOMVector<Float> rightNode = map.getNode(x+1,y).getVector();
                    SOMVector<Float> bottomNode = map.getNode(x,y+1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(leftNode)+
                                    centerNode.euclideanDistance(rightNode)+
                                    centerNode.euclideanDistance(bottomNode))/3;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else if(x==map.getWidth()-1){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> leftNode = map.getNode(x-1,y).getVector();
                    SOMVector<Float> topNode = map.getNode(x,y-1).getVector();
                    SOMVector<Float> bottomNode = map.getNode(x,y+1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(leftNode)+
                                    centerNode.euclideanDistance(topNode)+
                                    centerNode.euclideanDistance(bottomNode))/3;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else if(y==map.getHeight()-1){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> leftNode = map.getNode(x-1,y).getVector();
                    SOMVector<Float> rightNode = map.getNode(x+1,y).getVector();
                    SOMVector<Float> topNode = map.getNode(x,y-1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(leftNode)+
                                    centerNode.euclideanDistance(rightNode)+
                                    centerNode.euclideanDistance(topNode))/3;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else{
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> leftNode = map.getNode(x-1,y).getVector();
                    SOMVector<Float> rightNode = map.getNode(x+1,y).getVector();
                    SOMVector<Float> topNode = map.getNode(x,y-1).getVector();
                    SOMVector<Float> bottomNode = map.getNode(x,y+1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(leftNode)+
                                    centerNode.euclideanDistance(rightNode)+
                                    centerNode.euclideanDistance(topNode)+
                                    centerNode.euclideanDistance(bottomNode))/4;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }

            }
        }
        for(int x=0; x < map.getWidth(); x++){
            for(int y=0; y< map.getHeight(); y++){
                UMatrix[x][y] = UMatrix[x][y] / max;
            }
        }


    }

    /** Returns the UMatrix (2-dim double array) for this map
     *
     * @return a 2 dimensional double array which is the UMatrix
     */
    public double[][] getUMatrix(){
        if(UMatrix == null) calcUMatrix();
        return UMatrix;
    }

    /** Returns the UMatrix for a specific node
     *
     * @param   x The x coodinate of the node
     * @param   y The y coordinate of the node
     * @return  The Umatrix of the node
     */
    public double getUMatrix(int x, int y){
        if(UMatrix == null) calcUMatrix();
        return UMatrix[x][y];
    }

    /**
     * Sets one label
     * @param label     The label to set to
     * @param x         The x coordinate of the node
     * @param y         The y coordinate of the node
     */
    public void setLabel(String label, int x, int y){
        labels[x][y] = label;
    }

    /**
     * Returns all the labels for this map
     * @return          All the labels in a 2 dimensional string array
     */
    public String[][] getLabels(){
        return labels;
    }

    /**
     * Returns the label for the node specified
     * @param x         The x coordinate of the node
     * @param y         The y coordinate of the node
     * @return          The label of the node specified
     */
    public String getLabel(int x, int y){
        return labels[x][y];
    }
    
    //obtains a predicted downlink using outputs from the SOMPredictor
    @SuppressWarnings("unchecked")
    public String[] getPredictedNodes(Vector predictorOutputs, int xRange, int yRange){
         String[] output = new String[predictorOutputs.size()];
         for(int i=0; i<predictorOutputs.size(); i++){
            //first we find the nodes
            int numNodes = ((SOMVector<Float>)predictorOutputs.get(i)).size()/2;
            //System.out.println((SOMVector<Float>)predictorOutputs.get(i));
            String predictedCoordinates = "";
            for(int j=0; j<numNodes; j++){
                int x = new Float(((SOMVector<Float>)predictorOutputs.get(i)).get(j*2)*map.getWidth()).intValue();
                int y = new Float(((SOMVector<Float>)predictorOutputs.get(i)).get(j*2+1)*map.getHeight()).intValue();
                String[] links = map.getNode(x,y).getLinks(false);
                /*System.out.println(j + ": ");
                for(int k=0; k<links.length; k++){
                    System.out.println(k +": " + links[k]);
                }*/
                String prediction = getPredictedLink(links);
                //if the prediction is a blank, replaces with the contents of the node
                if(prediction.compareTo("") == 0){
                    SOMVector<Float> nodeContents = map.getNode(x,y).getVector();
                    for(int k=0; k<nodeContents.size(); k+=2){
                        prediction = prediction + "" + java.lang.Math.round(nodeContents.get(k) * xRange);
                        prediction = prediction + "," + java.lang.Math.round(nodeContents.get(k) * yRange) + ",";
                    }
                    prediction = prediction.substring(0,prediction.length()-1);
                }
                predictedCoordinates = predictedCoordinates + "," + prediction;              
            }
            predictedCoordinates = predictedCoordinates.substring(1);
            //System.out.println(predictedCoordinates);
            output[i] = predictedCoordinates;
        }
        //we return one set of links for each predictor output in a string
         return output;
    }
    
    //obtains a predicted downlink using a set of downlinks predicted from the higher som
    public String[] getPredictedNodes(String[] nodeList, int xRange, int yRange){
        String[] output = new String[nodeList.length];
        for(int i=0; i<nodeList.length; i++){
            String predictedCoordinates = "";
            String[] coordinates = nodeList[i].split(",");
            for(int j=0; j<coordinates.length; j+=2){
                int x = Integer.parseInt(coordinates[j]);
                int y = Integer.parseInt(coordinates[j+1]);
                String[] links = map.getNode(x,y).getLinks(false);
                String prediction = getPredictedLink(links);
                //if the prediction is a blank, replaces with the contents of the node
                if(prediction.compareTo("") == 0){
                    System.out.println("waaaaaaaa");
                    SOMVector<Float> nodeContents = map.getNode(x,y).getVector();
                    for(int k=0; k<nodeContents.size(); k+=2){
                        prediction = prediction + "" + java.lang.Math.round(nodeContents.get(k) * xRange);
                        prediction = prediction + "," + java.lang.Math.round(nodeContents.get(k) * yRange) + ",";
                    }
                    prediction = prediction.substring(0,prediction.length()-1);
                }
                predictedCoordinates = predictedCoordinates + "," + prediction;
            }
            predictedCoordinates = predictedCoordinates.substring(1);
            //System.out.println(predictedCoordinates);
            output[i] = predictedCoordinates;
        }
        return output;
    }
    
    //does the actual prediction
    public String getPredictedLink(String[] links){
        int numLinks = links.length;
        //in the case there are no downlinks, we return a blank
        if(numLinks == 0) return "";
        Random r = new Random();
        int counter = 0;
        //count the number of links for chunk 1
        for(int i=0; i<numLinks; i++){
            if(Integer.parseInt(links[i].split(",")[1]) == 1){
                counter++;             
            }
        }
        String[] link0 = new String[numLinks-counter];
        String[] link1 = new String[counter];
        int counter0 = 0;
        int counter1 = 0;
        for(int i=0; i<numLinks; i++){
            if(Integer.parseInt(links[i].split(",")[1]) == 0){
                link0[counter0] = links[i];
                counter0++;
            }
            else{
                link1[counter1] = links[1];
                counter1++;
            }
        }
        //just double checking here
        if(counter0 + counter1 != numLinks) System.out.println("Error in counting links");
        String[] parts0 = link0[r.nextInt(java.lang.Math.round((float)counter0/2))].split(",");
        String[] parts1 = link1[r.nextInt(java.lang.Math.round((float)counter1/2))].split(",");
        String output = parts0[2] + "," + parts0[3] +
                        "," + parts1[2] + "," + parts1[3];
        
        return output;
        
    }  
    
    //obtains the contents of the node based on the node list given
    public String[] getPrediction(String[] nodeList, int range){
        for(int i=0; i<nodeList.length; i++){
            String[] coordinates = nodeList[i].split(",");
            for(int j=0; j<coordinates.length; j+=2){
                String curOutput = getNodeContents(Integer.parseInt(coordinates[j]),
                                                   Integer.parseInt(coordinates[j+1]),
                                                   range); 
                System.out.println(curOutput);
            }
            
        }
        return null;
    }
    
    //obtains a predicted downlink using outputs from the SOMPredictor
    @SuppressWarnings("unchecked")
    public String[] getPrediction(Vector predictorOutputs, int range){
         String[] output = new String[predictorOutputs.size()];
         for(int i=0; i<predictorOutputs.size(); i++){
            //first we find the nodes
            //int numNodes = ((SOMVector<Float>)predictorOutputs.get(i)).size()/2;
            //System.out.println((SOMVector<Float>)predictorOutputs.get(i));
            String predictedCoordinates = "";
            for(int j=0; j<((SOMVector<Float>)predictorOutputs.get(i)).size(); j+=2){
                int x = new Float(((SOMVector<Float>)predictorOutputs.get(i)).get(j)*map.getWidth()).intValue();
                int y = new Float(((SOMVector<Float>)predictorOutputs.get(i)).get(j+1)*map.getHeight()).intValue();
                String curOutput = getNodeContents(x,y,range);
                System.out.println(curOutput);
                
            }
        }
         return null;
    }
    
    //get the contents of the specified node
    public String getNodeContents(int x, int y, int range){
        String output = "";
        SOMVector<Float> weights = map.getNode(x,y).getVector();
        for(int i=0; i<weights.size(); i++){
            output = output + "," + weights.get(i) * range;
        }
        return output.substring(1);
    }

}
