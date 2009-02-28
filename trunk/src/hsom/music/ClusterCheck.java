/**
  * Manages the training of the music SOM
  * @author Edwin Law Hui Hean
  */

package hsom.music;

import hsom.core.*;
import hsom.util.*;
import java.io.*;
import java.util.Vector;

public class ClusterCheck {
    
    private SOMMusicMap topMap, midMap, joinMap, pitch2Map, pitch4Map, durationMap;
    private SOMMusicInput inputPitch, inputDuration;
    private SOMTrainer pitchTrainer2, durationTrainer, pitchTrainer4, joinTrainer, midTrainer, topTrainer, top64Trainer;
    private SOMLinker pitchLinker, joinLinker, midLinker, topLinker, top64Linker;


    /**
     * The sole constructor
     * @param in The name of the input file
     */
    public ClusterCheck(String pitch, String duration, String inMap){

        inputPitch = new SOMMusicInput(pitch);
        inputDuration = new SOMMusicInput(duration);
        Vector output;
        SOMVector<Float> curOutput;
        
        readMap(inMap);

        pitchTrainer2 = new SOMTrainer(pitch2Map.getMap(), inputPitch);
        durationTrainer = new SOMTrainer(durationMap.getMap(), inputDuration);
        pitchLinker = new SOMLinker(pitchTrainer2);
        pitchTrainer4 = new SOMTrainer(pitch4Map.getMap(), pitchLinker);
        pitchLinker.setHigherSOM(pitchTrainer4);
        joinLinker = new SOMLinker(pitchTrainer4, durationTrainer);
        joinTrainer = new SOMTrainer(joinMap.getMap(), joinLinker);
        joinLinker.setHigherSOM(joinTrainer);
        midLinker = new SOMLinker(joinTrainer);
        midTrainer = new SOMTrainer(midMap.getMap(), midLinker);
        midLinker.setHigherSOM(midTrainer);
        topLinker = new SOMLinker(midTrainer);
        topTrainer = new SOMTrainer(topMap.getMap(), topLinker);
        topLinker.setHigherSOM(topTrainer);
        topTrainer.start(1);
        output = topTrainer.getOutput();

        for(int i=0; i<output.size(); i++){
            curOutput = (SOMVector<Float>)output.elementAt(i);
            printOutput(curOutput, topMap.getMap());
            //System.out.println(curOutput);
        }
        
    }

    private void printOutput(SOMVector<Float> curOutput, SOMMap map){
        for(int i=0; i<curOutput.size(); i+=2){
            System.out.print("<");
            System.out.print(new Float(curOutput.elementAt(i)*map.getWidth()).intValue() + "," +
                             new Float(curOutput.elementAt(i+1)*map.getHeight()).intValue());
            //System.out.print(curOutput.elementAt(i)*map.getWidth() + "," +
            //                 curOutput.elementAt(i+1)*map.getHeight());
            System.out.print(">");
        }
        System.out.println("");
    }
    
    /** The method that reads the SOMMap from the file
       * @param	inFile	The name of the input file
       */
    private void readMap(String inFile){
        try{
            FileInputStream fis = new FileInputStream(inFile);
            BufferedInputStream bis =  new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            pitch2Map = (SOMMusicMap)ois.readObject();
            pitch4Map = (SOMMusicMap)ois.readObject();
            durationMap = (SOMMusicMap)ois.readObject();
            joinMap = (SOMMusicMap)ois.readObject();
            midMap = (SOMMusicMap)ois.readObject();
            topMap = (SOMMusicMap)ois.readObject();
            ois.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    
    
    
    /**
     * The main function
     * @param args The arguments, two file names.
     */
    public static void main(String args[]){
        final ClusterCheck app = new ClusterCheck(args[0], args[1], args[2]);
    }

}
