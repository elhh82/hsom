/**
 * This class is a container for the SOMMap with some added functionalities
 * to help with prediction.
 * @author Edwin Law Hui Hean
 */

package hsom.music;

import hsom.core.*;
import java.util.Vector;
import java.util.Random;

public class SOMMusicMap {
    
    SOMMap map;
    
    //a constructor for the SOMMusicMap
    public SOMMusicMap(SOMMap m){
        map = m;
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
                        prediction = "" + java.lang.Math.round(nodeContents.get(k) * xRange);
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
    public String[] getPredictedNodes(String[] predictedNodes){
        
        return null;
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
