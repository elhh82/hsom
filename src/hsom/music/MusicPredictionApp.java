/**
  * Manages the training of the music SOM
  * @author Edwin Law Hui Hean
  */

package hsom.music;

import hsom.core.*;
import hsom.util.*;
import java.io.*;

public class MusicPredictionApp {
    
    private SOMMap bottomMap, midMap, topMap;
    private SOMMusicMap bottomMusicMap, midMusicMap, topMusicMap;
    private SOMPredictor bottomPredictor, midPredictor, topPredictor;
    private SOMPredictorLinker midLinker, topLinker;
    private SOMMusicPredictionInput input;
    
    /**
     * The sole constructor
     * @param in The name of the input file
     */
    public MusicPredictionApp(String predictFile, String sourceFile, String inMap){
        
        input = new SOMMusicPredictionInput(predictFile, sourceFile);
        readMap(inMap);
        bottomPredictor = new SOMPredictor(bottomMap, input);
        midLinker = new SOMPredictorLinker(bottomPredictor);
        midPredictor = new SOMPredictor(midMap, midLinker);
        midLinker.setHigherSOM(midPredictor);
        topLinker = new SOMPredictorLinker(midPredictor);
        topPredictor = new SOMPredictor(topMap, topLinker);
        topLinker.setHigherSOM(topPredictor); 
        
    }
    
    /** The method that reads the SOMMap from the file
       * @param	inFile	The name of the input file
       */
    private void readMap(String inFile){
        try{
            FileInputStream fis = new FileInputStream(inFile);
            BufferedInputStream bis =  new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            bottomMap = (SOMMap)ois.readObject();
            midMap = (SOMMap)ois.readObject();
            topMap = (SOMMap)ois.readObject();
            bottomMusicMap = new SOMMusicMap(bottomMap);
            midMusicMap = new SOMMusicMap(midMap);
            topMusicMap = new SOMMusicMap(topMap);
            ois.close();			
        }catch(Exception e){
            System.out.println(e);
        }
    }

    
    /** The functions that starts the training
       * @param	iterations	the number of times to train
       */
    public void start(){

        topPredictor.start();        

    }
    
    /**
     * Prints out the input values, used for testing.
     */
    public void printInputs(){
        java.util.Vector inputs = input.getInput();
        for(int i=0; i<inputs.size(); i++){
            System.out.println(inputs.get(i));
        } 
        
    }
    
    /**
     * Prints out the output values, used for testing.
     */
    public void printOutputs(){
        java.util.Vector outputs = bottomPredictor.getOutput();
        for(int i=0; i<outputs.size(); i++){
            System.out.println(outputs.get(i));
        } 
        System.out.println("============================");
        outputs = midPredictor.getOutput();
        for(int i=0; i<outputs.size(); i++){
            System.out.println(outputs.get(i));
        }
        outputs = topPredictor.getOutput();
        System.out.println("============================");
        for(int i=0; i<outputs.size(); i++){
            System.out.println(outputs.get(i));
        }
        
    }
    
    /**
     * Produces the prediction from the midmap
     */    
    @SuppressWarnings("unchecked")
    public void getPrediction(){
        java.util.Vector outputs = midPredictor.getOutput();
        midMusicMap.getPrediction(outputs, input.getRange());
    
    }
    
    /**
     * Returns the first(best) link corresponding to the position required
     */
    public SOMNode[] getBestLink(String[] links){
        SOMNode[] bestNodes = new SOMNode[2];
        
        for(int i=0; i<links.length; i++){
            String[] splitLink = links[i].split("\\p{Punct}");
            //temporary patch here to get rid of the space in splitLink[1]
            if(Integer.parseInt(splitLink[1].split(" ")[0]) == 0){
                bestNodes[0] = bottomMap.getNode(Integer.parseInt(splitLink[2]),Integer.parseInt(splitLink[3]));
                break;
            }
        }
        for(int i=0; i<links.length; i++){
            String[] splitLink = links[i].split("\\p{Punct}");
            //temporary patch here to get rid of the space in splitLink[1]
            if(Integer.parseInt(splitLink[1].split(" ")[0]) == 1){
                bestNodes[1] = bottomMap.getNode(Integer.parseInt(splitLink[2]),Integer.parseInt(splitLink[3]));
                break;
            }
        }
        
        return bestNodes;
    }
    
    /**
     * The main function
     * @param args The arguments, two file names.
     */
    public static void main(String args[]){
        final MusicPredictionApp app = new MusicPredictionApp(args[0], args[1], args[2]);
        //app.printInputs();
        //app.printInputs();
        app.start();
        //app.writeMap(args[1]);
        
        //app.printOutputs();
        app.getPrediction();
    }

}
