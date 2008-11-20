/**
  * Manages the training of the music SOM
  * @author Edwin Law Hui Hean
  */

package hsom.music;

import hsom.core.*;
import hsom.util.*;
import java.io.*;

public class MusicApp {
    
    private SOMMap pitchMap, durationMap, midMap, topMap;
    private SOMTrainer pitchTrainer, durationTrainer, midTrainer, topTrainer;
    private SOMLinker midLinker, topLinker;
    private SOMMusicInput inputPitch, inputDuration;
    
    /**
     * The sole constructor
     * @param in The name of the input file
     */
    public MusicApp(String pitch, String duration){
        inputPitch = new SOMMusicInput(pitch);
        inputDuration = new SOMMusicInput(duration);
        pitchMap = new SOMMap(100,100,8);
        durationMap = new SOMMap(60,60,4);
        midMap = new SOMMap(80,80,4);
        topMap = new SOMMap(60,60,4);
        pitchTrainer = new SOMTrainer(pitchMap, inputPitch);
        durationTrainer = new SOMTrainer(durationMap, inputDuration);
        midLinker = new SOMLinker(pitchTrainer, durationTrainer);
        midTrainer = new SOMTrainer(midMap, midLinker);
        midLinker.setHigherSOM(midTrainer);
        topLinker = new SOMLinker(midTrainer);
        topTrainer = new SOMTrainer(topMap, topLinker);
        topLinker.setHigherSOM(topTrainer); 
        
    }
    
    /** The functions that starts the training
       * @param	iterations	the number of times to train
       */
    public void start(int iterations){

        topTrainer.start(iterations);

    }
    
     /** Writes the map into a file
       * @param	filename	the name of the output file
       */
    @SuppressWarnings("empty-statement")
    public void writeMap(String filename){
        try{
            FileOutputStream fos = new FileOutputStream(filename);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(pitchMap);
            oos.writeObject(durationMap);
            oos.writeObject(midMap);
            oos.writeObject(topMap);
            oos.close();

        }catch(Exception e){};
    }
    
    /**
     * Prints out the input values, used for testing.
     */
    public void printInputs(){
        java.util.Vector inputs = inputPitch.getInput();
        for(int i=0; i<inputs.size(); i++){
            System.out.println(inputs.get(i));
        } 
        
    }
    
    
    /**
     * The main function
     * @param args The arguments, two file names.
     */
    public static void main(String args[]){
        final MusicApp app = new MusicApp(args[0], args[1]);
        //app.printInputs();
        app.start(1000);
        app.writeMap(args[2]);
    }

}
