/**
  * Manages the training of the music SOM
  * @author Edwin Law Hui Hean
  */

package hsom.music;

import hsom.core.*;
import hsom.util.*;
import java.io.*;

public class MusicApp {
    
    private SOMMap bottomMap, midMap, topMap;
    private SOMTrainer bottomTrainer, midTrainer, topTrainer;
    private SOMLinker midLinker, topLinker;
    private SOMMusicInput input;
    
    /**
     * The sole constructor
     * @param in The name of the input file
     */
    public MusicApp(String in){
        input = new SOMMusicInput(in);
        bottomMap = new SOMMap(100,100,4);
        midMap = new SOMMap(80,80,4);
        topMap = new SOMMap(60,60,4);
        bottomTrainer = new SOMTrainer(bottomMap, input);
        midLinker = new SOMLinker(bottomTrainer);
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

            oos.writeObject(bottomMap);
            oos.writeObject(midMap);
            oos.writeObject(topMap);
            oos.close();

        }catch(Exception e){};
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
     * The main function
     * @param args The arguments, two file names.
     */
    public static void main(String args[]){
        final MusicApp app = new MusicApp(args[0]);
        //app.printInputs();
        app.start(50);
        app.writeMap(args[1]);
    }

}
