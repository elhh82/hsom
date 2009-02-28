/**
  * Manages the training of the music SOM
  * @author Edwin Law Hui Hean
  */

package hsom.music;

import hsom.core.*;
import hsom.util.*;
import java.io.*;

public class MusicApp3 {
    
    private SOMMap pitchMap, durationMap, joinMap;
    private SOMTrainer joinTrainer;
    private SOMOutput pitchOutput, durationOutput;
   private SOMLinker joinLinker;
    private SOMMusicInput inputPitch, inputDuration;
    
    /**
     * The sole constructor
     * @param in The name of the input file
     */
    public MusicApp3(String pitch, String duration, String pMap, String dMap){
        readMaps(pMap, dMap);
        inputPitch = new SOMMusicInput(pitch);
        inputDuration = new SOMMusicInput(duration);
        pitchOutput = new SOMOutput(pitchMap, inputPitch);
        durationOutput = new SOMOutput(durationMap, inputDuration);

        joinMap = new SOMMap(80,80,4);
        joinLinker = new SOMLinker(pitchOutput, durationOutput);
        joinTrainer = new SOMTrainer(joinMap, joinLinker);
        joinLinker.setHigherSOM(joinTrainer);
        
    }

    /**
     * Read the input maps
     * @param pitch     The pitch map file
     * @param duration  The duration map file
     */
    private void readMaps(String pitch, String duration){
        try{
            FileInputStream fis = new FileInputStream(pitch);
            BufferedInputStream bis =  new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            pitchMap = (SOMMap)ois.readObject();
            ois.close();
            fis = new FileInputStream(duration);
            bis =  new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);
            durationMap = (SOMMap)ois.readObject();
            ois.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    /** The functions that starts the training
       * @param	iterations	the number of times to train
       */
    public void start(int iterations){

        joinTrainer.start(iterations);

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
            oos.writeObject(joinMap);
            oos.writeObject(pitchMap);
            oos.writeObject(durationMap);
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
        final MusicApp3 app = new MusicApp3(args[0], args[1], args[2], args[3]);
        //app.printInputs();
        app.start(1000);
	if(args[1] == null) args[1] = "defaultoutput.txt";
        app.writeMap(args[1]);
    }

}
