/**
  * Manages the training of the music SOM
  * @author Edwin Law Hui Hean
  */

package hsom.music;

import hsom.core.*;
import hsom.util.*;
import java.io.*;

public class ClassifyApp2 {

    private SOMMap pitchMap, durationMap, joinMap, topMap;
    private SOMTrainer pitchTrainer, durationTrainer, joinTrainer, topTrainer;
    private SOMOutput pitchOutput, durationOutput, joinOutput;
    private SOMLinker joinLinker, topLinker;
    private SOMMusicInput inputPitch, inputDuration;

    /**
     * The sole constructor
     * @param in The name of the input file
     */
    public ClassifyApp2(String pitch, String duration, String maps){
        readMaps(maps);
        //pitchMap = new SOMMap(40,40,8);
        //durationMap = new SOMMap(20,20,8);
        inputPitch = new SOMMusicInput(pitch);
        inputDuration = new SOMMusicInput(duration);
        //pitchTrainer = new SOMTrainer(pitchMap, inputPitch);
        //durationTrainer = new SOMTrainer(durationMap, inputDuration);
        pitchOutput = new SOMOutput(pitchMap, inputPitch);
        durationOutput = new SOMOutput(durationMap, inputDuration);
        joinMap = new SOMMap(60,60,8);
        joinLinker = new SOMLinker(pitchOutput, durationOutput);
        joinTrainer = new SOMTrainer(joinMap, joinLinker);
        //joinOutput = new SOMOutput(joinMap, joinLinker);
        joinLinker.setHigherSOM(joinOutput);
        //topMap = new SOMMap(30,30,8);
        //topLinker = new SOMLinker(joinOutput);
        //topTrainer = new SOMTrainer(topMap, topLinker);
        //topLinker.setHigherSOM(topTrainer);

    }

    /**
     * Read the input maps
     * @param pitch     The pitch map file
     * @param duration  The duration map file
     */
    private void readMaps(String maps){
        try{
            FileInputStream fis = new FileInputStream(maps);
            BufferedInputStream bis =  new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            //joinMap = (SOMMap)ois.readObject();
            pitchMap = (SOMMap)ois.readObject();
            durationMap = (SOMMap)ois.readObject();
            ois.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }


    /** The functions that starts the training
       * @param iterations      the number of times to train
       */
    public void start(int iterations){
        //pitchTrainer.start(iterations);
        //durationTrainer.start(iterations);
        joinTrainer.start(iterations);
        //topTrainer.start(iterations);
        /*
        for(int i=0; i<durationMap.getWidth(); i++){
            for(int j=0; j<durationMap.getHeight(); j++){
                System.out.println(i + "," + j + " - ");
                String[] links = durationMap.getNode(i,j).getLinks(true);
                for(int k=0; k<links.length; k++){
                    System.out.print("<" + links[k] + "> ");
                }
                System.out.println("");
            }
        }
        */
    }

   /** Writes the map into a file
       * @param filename        the name of the output file
       */
    @SuppressWarnings("empty-statement")
    public void writeMap(String filename){
        try{
            FileOutputStream fos = new FileOutputStream(filename);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            //oos.writeObject(topMap);
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
        final ClassifyApp2 app = new ClassifyApp2(args[0], args[1], args[2]);
        //app.printInputs();
        app.start(1000);
        if(args[3] == null) args[3] = "defaultoutput.txt";
        app.writeMap(args[3]);
    }


}

