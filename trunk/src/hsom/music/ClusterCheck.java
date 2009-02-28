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
    
    private SOMMusicMap durationMap;
    private SOMMusicInput inputDuration;
    private SOMOutput durationOutput;

    /**
     * The sole constructor
     * @param in The name of the input file
     */
    @SuppressWarnings("unchecked")
    public ClusterCheck(String duration, String inMap){

        inputDuration = new SOMMusicInput(duration);
        Vector output;
        SOMVector<Float> curOutput;
        
        readMap(inMap);

        durationOutput = new SOMOutput(durationMap.getMap(), inputDuration);
        durationOutput.start();
        output = durationOutput.getOutput();

        for(int i=0; i<output.size(); i++){
            System.out.print(((i/8)+1) + "-" + (i%8+1) + " - ");
            curOutput = (SOMVector<Float>)output.elementAt(i);
            printOutput(curOutput, durationMap.getMap());
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
            durationMap = new SOMMusicMap((SOMMap)ois.readObject());
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
        final ClusterCheck app = new ClusterCheck(args[0], args[1]);
    }

}
