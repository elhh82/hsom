/**
  * Manages the training of the music SOM
  * @author Edwin Law Hui Hean
  */

package hsom.music;

import hsom.core.*;
import hsom.util.*;
import java.io.*;

public class MusicApp3 {
    
    private SOMMap pitchMap;
    private SOMTrainer pitchTrainer;
    //private SOMLinker midLinker, topLinker;
    private SOMMusicInput inputPitch;
    
    /**
     * The sole constructor
     * @param in The name of the input file
     */
    public MusicApp3(String pitch){
        inputPitch = new SOMMusicInput(pitch);
        pitchMap = new SOMMap(100,100,8);
        pitchTrainer = new SOMTrainer(pitchMap, inputPitch);
        
    }
    
    /** The functions that starts the training
       * @param	iterations	the number of times to train
       */
    public void start(int iterations){

        pitchTrainer.start(iterations);

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
            oos.close();

        }catch(Exception e){};
    }
    
    /**
     * Prints out the input values, used for testing.
     */
    public void printInputs(){
        //java.util.Vector inputs = inputPitch.getInput();
        //for(int i=0; i<inputs.size(); i++){
        //    System.out.println(inputs.get(i));
        //}
        
    }
    
    
    /**
     * The main function
     * @param args The arguments, two file names.
     */
    public static void main(String args[]){
        final MusicApp3 app = new MusicApp3(args[0]);
        //app.printInputs();
        app.start(1000);
	if(args[1] == null) args[1] = "defaultoutput.txt";
        app.writeMap(args[1]);
    }

}
