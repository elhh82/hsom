/**
  * Manages the training of the music SOM
  * @author Edwin Law Hui Hean
  */

package hsom.music;

import hsom.core.*;
import hsom.util.*;
import java.io.*;

public class MusicPredictionApp {
    
    private SOMMap pitchMap, durationMap, midMap, topMap;
    private SOMMusicMap pitchMusicMap, durationMusicMap, midMusicMap, topMusicMap;
    private SOMPredictor pitchPredictor, durationPredictor, midPredictor, topPredictor;
    private SOMPredictorLinker midLinker, topLinker;
    private SOMMusicPredictionInput inputPitch, inputDuration;
    
    /**
     * The sole constructor
     * @param in The name of the input file
     */
    public MusicPredictionApp(String predictFilePitch, String sourceFilePitch, 
                              String predictFileDuration, String sourceFileDuration,
                              String inMap){
        
        inputPitch = new SOMMusicPredictionInput(predictFilePitch, sourceFilePitch);
        inputDuration = new SOMMusicPredictionInput(predictFileDuration, sourceFileDuration);
        readMap(inMap);
        pitchPredictor = new SOMPredictor(pitchMap, inputPitch);
        durationPredictor = new SOMPredictor(durationMap, inputDuration);
        midLinker = new SOMPredictorLinker(pitchPredictor, durationPredictor);
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
            pitchMap = (SOMMap)ois.readObject();
            durationMap = (SOMMap)ois.readObject();
            midMap = (SOMMap)ois.readObject();
            topMap = (SOMMap)ois.readObject();
            pitchMusicMap = new SOMMusicMap(pitchMap);
            durationMusicMap = new SOMMusicMap(durationMap);
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
     * Prints out the pitch input values, used for testing.
     */
    public void printInputs(){
        java.util.Vector inputs = inputPitch.getInput();
        for(int i=0; i<inputs.size(); i++){
            System.out.println(inputs.get(i));
        } 
        
    }
    
    /**
     * Prints out the pitch output values, used for testing.
     */
    public void printOutputs(){
        java.util.Vector outputs = pitchPredictor.getOutput();
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
        /*
        //the midmap output and contents 
        java.util.Vector outputs = midPredictor.getOutput();
        for(int i=0; i<outputs.size(); i++){
            System.out.println(outputs.get(i));
        }
        midMusicMap.getPrediction(outputs, 100);
        System.out.println("===============");
        //the bottommap output and contents
        outputs = bottomPredictor.getOutput();
        for(int i=0; i<outputs.size(); i++){
            System.out.println(outputs.get(i));
        }
        bottomMusicMap.getPrediction(outputs, input.getRange());
        */
        /*
        //predict from top
        System.out.println("top");
        java.util.Vector outputs = topPredictor.getOutput();
        String[] topPrediction = topMusicMap.getPredictedNodes(outputs, midMap.getHeight(), midMap.getWidth());
        String[] predictions = midMusicMap.getPredictedNodes(topPrediction, bottomMap.getHeight(), bottomMap.getWidth());
        bottomMusicMap.getPrediction(predictions, input.getRange());
        
        //predict from middle
        System.out.println("Middle");
        java.util.Vector midoutputs = midPredictor.getOutput();
        String[] midpredictions = midMusicMap.getPredictedNodes(midoutputs, bottomMap.getHeight(), bottomMap.getWidth());
        bottomMusicMap.getPrediction(midpredictions, input.getRange());
        */
        //predict from boyyom
        System.out.println("Bottom");
        java.util.Vector pitchoutputs = pitchPredictor.getOutput();
        pitchMusicMap.getPrediction(pitchoutputs, inputPitch.getRange());
        //System.out.println("===============");
        //contents of the nodes in question
        //midMusicMap.getPrediction(outputs, 100);
        /*for(String predict:predictions){
            System.out.println(predict);
        }*/
        /*System.out.println(bottomMap.getHeight());
        java.util.Vector outputs = bottomPredictor.getOutput();
        for(int i=0; i<outputs.size(); i++){
            System.out.println(outputs.get(i));
        }
        bottomMusicMap.getPrediction(outputs, input.getRange());*/
        //bottomMusicMap.getPrediction(predictions, input.getRange());
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
                
//please recheck this               
                bestNodes[0] = pitchMap.getNode(Integer.parseInt(splitLink[2]),Integer.parseInt(splitLink[3]));
                break;
            }
        }
        for(int i=0; i<links.length; i++){
            String[] splitLink = links[i].split("\\p{Punct}");
            //temporary patch here to get rid of the space in splitLink[1]
            if(Integer.parseInt(splitLink[1].split(" ")[0]) == 1){
//please recheck this
                bestNodes[1] = pitchMap.getNode(Integer.parseInt(splitLink[2]),Integer.parseInt(splitLink[3]));
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
        final MusicPredictionApp app = new MusicPredictionApp(args[0], args[1], args[2], args[3], args[4]);
        //app.printInputs();
        //app.printInputs();
        app.start();
        //app.writeMap(args[1]);
        
        //app.printOutputs();
        app.getPrediction();
    }

}
