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
                /*for(int k=0; k<links.length; k++){
                    System.out.println(k +": " + links[k]);
                }*/
                String prediction = getPredictedLink(links);
                //if the prediction is a blank, replaces with the contents of the node
                if(prediction.compareTo("") == 0){
                    
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
        for(int i=0; i<numLinks; i++){
            if(Integer.parseInt(links[i].split(",")[1]) == 1) break;
            counter++;
        }
        String first = links[0];//links[r.nextInt(counter/2)];
        String second = links[counter];//links[r.nextInt((numLinks - counter)/2) + counter];
        return first.split(",")[2] + "," + first.split(",")[3] + "," +  
               second.split(",")[2] + "," + second.split(",")[3];
    }  
    
    //obtains the contents of the node based on the node list given
    public String[] getPrediction(String[] nodeList, int range){
        for(int i=0; i<nodeList.length; i++){
            String[] coordinates = nodeList[i].split(",");
            for(int j=0; j<coordinates.length; j+=2){
                String curOutput = getNodeContents(Integer.parseInt(coordinates[j]),
                                                   Integer.parseInt(coordinates[j+1]),
                                                   range); 
                //System.out.println(curOutput);
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
