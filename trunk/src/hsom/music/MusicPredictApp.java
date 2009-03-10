/**
  * Manages the training of the music SOM
  * @author Edwin Law Hui Hean
  */

package hsom.music;

import hsom.core.*;
import hsom.util.*;
import java.io.*;
import java.util.Vector;

public class MusicPredictApp {

    private SOMMap pitchMap, durationMap, topMap, joinMap;
    private SOMMusicMap pitchMusicMap, durationMusicMap, topMusicMap, joinMusicMap;
    private SOMMusicOutput pitchOutput, durationOutput;
    private SOMOutput joinOutput, topOutput;
    private SOMMusicInput  predictPitch, predictDuration;
    private SOMLinker joinLinker, topLinker;

    /**
     * The sole constructor
     * @param in The name of the input file
     */
    public MusicPredictApp(String srcPitch, String srcDuration, String predPitch, String predDuration, String maps){
        readMaps(maps);
        predictPitch = new SOMMusicInput(srcPitch,predPitch);
        predictDuration = new SOMMusicInput(srcDuration, predDuration);
        pitchOutput = new SOMMusicOutput(pitchMap, predictPitch);
        durationOutput = new SOMMusicOutput(durationMap, predictDuration);
        joinLinker = new SOMLinker(pitchOutput, durationOutput);
        joinOutput = new SOMOutput(joinMap, joinLinker);
        joinLinker.setHigherSOM(joinOutput);
        topLinker = new SOMLinker(joinOutput);
        topOutput = new SOMOutput(topMap, topLinker);
        topLinker.setHigherSOM(topOutput);
    }

    /**
     * Runs the prediction
     * @param outFile   The name of the output file
     */
     
    private void start(String outFile){
        //runs it once to get all the output values
        topOutput.start();

        //gets the prediction
        //bottomSOMPrediction(pitchOutput.getOutput(), outFile, true);
        //bottomSOMPrediction(durationOutput.getOutput(), outFile, true);
        //joinSOMPrediction(joinOutput.getOutput(), outFile);
        topSOMPrediction(topOutput.getOutput(), outFile);
    }

    /**
     * Get the prediction from the topmost level. Using the SOM contents.
     * @param topOutput The output (bmu) of the top level
     * @param outFile   The name of the output file
     */
    private void topSOMPrediction(Vector topOutput, String outFile){
        Vector topPrediction = topMusicMap.getLowerNodes(topOutput);
        joinSOMPrediction(topPrediction, outFile);
    }

    /**
     * Get the prediction from the join level. Using the SOM contents.
     * @param topOutput The output (bmu) of the join level
     * @param outFile   The name of the output file
     */
    private void joinSOMPrediction(Vector joinOutput, String outFile){
        Vector[] joinPrediction = joinMusicMap.getJoinedLowerNodes(joinOutput);
        predictPitch.revert(pitchMusicMap.getNodeContents(joinPrediction[0]),outFile);
        predictDuration.revert(durationMusicMap.getNodeContents(joinPrediction[1]),outFile);
    }

    /**
     * Get the prediction from the bottom level.
     * @param bottomOutput  The output(bmu) of the bottom level
     * @param outFile       The name of the output file
     * @param pitch         Select if we are predicting for the pitch(true) or duration SOM
     */
    private void bottomSOMPrediction(Vector bottomOutput, String outFile, boolean pitch){
        if(pitch){
            predictPitch.revert(pitchMusicMap.getNodeContents(bottomOutput),outFile);
        }
        else{
            predictDuration.revert(durationMusicMap.getNodeContents(bottomOutput),outFile);
        }
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
            topMap = (SOMMap) ois.readObject();
            joinMap = (SOMMap)ois.readObject();
            pitchMap = (SOMMap)ois.readObject();
            durationMap = (SOMMap)ois.readObject();
            topMusicMap = new SOMMusicMap(topMap);
            joinMusicMap = new SOMMusicMap(joinMap);
            pitchMusicMap = new SOMMusicMap(pitchMap);
            durationMusicMap = new SOMMusicMap(durationMap);
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
        final MusicPredictApp app = new MusicPredictApp(args[0], args[1], args[2], args[3], args[4]);
        app.start(args[5]);
    }


}

