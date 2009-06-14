/**
  * Randomly picks n positions in the input string to blank out
  * @author Edwin Law Hui Hean
  */

package hsom.music;

import java.io.*;
import java.util.Random;

public class InputRandomizer {

    String inFilePitch, inFileDuration, outFilePitch, outFileDuration;
    int numRandom;

    /**
     * The constructor
     * @param in The input file
     * @param out The output file
     * @param num the number of elements to randomize
     */
    public InputRandomizer(String inPitch, String inDuration, String outPitch, String outDuration, int num){
        inFilePitch = inPitch;
        inFileDuration = inDuration;
        outFilePitch = outPitch;
        outFileDuration = outDuration;
        numRandom = num;
    }

    /**
     * The randomizer function which read, randomizes and writes the output file
     */
    public void randomizeFile(){

        try{
            //reads in the input file line by line
            BufferedReader inputReaderPitch = new BufferedReader(new FileReader(inFilePitch));
            BufferedReader inputReaderDuration = new BufferedReader(new FileReader(inFileDuration));
            int numLines = 0;
            String linesPitch[], linesDuration[];

            while(inputReaderPitch.readLine() != null){
                numLines++;
            }
            inputReaderPitch.close();
            linesPitch = new String[numLines];
            linesDuration = new String[numLines];
            inputReaderPitch = new BufferedReader(new FileReader(inFilePitch));
            for(int i=0; i<numLines; i++){
                linesPitch[i] = inputReaderPitch.readLine();
                linesDuration[i] = inputReaderDuration.readLine();
            }
            inputReaderPitch.close();
            inputReaderDuration.close();
            BufferedWriter outputBufferPitch = new BufferedWriter(new FileWriter(outFilePitch));
            BufferedWriter outputBufferDuration = new BufferedWriter(new FileWriter(outFileDuration));
            //blanks out the n random positions
            //and writes them to the file
            for(int i=0; i<numLines; i++){

                String[] valPitch = linesPitch[i].split(" ");
                String[] valDuration = linesDuration[i].split(" ");
                outputBufferPitch.write(valPitch[0] + " ");
                outputBufferDuration.write(valDuration[0] + " ");
                //find the actual notes and its length
                String[] valuesPitch = valPitch[1].split(",");
                String[] valuesDuration = valDuration[1].split(",");
                for(int j=0; j<numRandom; j++){
                    Random r = new Random();
                    int x = r.nextInt(64);
                    if(valuesPitch[x].equals("x")){
                        //force it to generate an extra number if this one has
                        //already been generated.
                        j--;
                    }
                    else{
                        valuesPitch[x] = "x";
                        valuesDuration[x] = "x";
                    }
                }
                for(int j=0; j<63; j++){
                    outputBufferPitch.write(valuesPitch[j]+",");
                    outputBufferDuration.write(valuesDuration[j]+",");
                }
                outputBufferPitch.write(valuesPitch[63]+'\n');
                outputBufferDuration.write(valuesDuration[63]+'\n');
            }
            outputBufferPitch.close();
            outputBufferDuration.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


     public static void main(String[] args){
        InputRandomizer randomizer = new InputRandomizer(args[0],args[1],args[2], args[3],Integer.parseInt(args[4]));
        randomizer.randomizeFile();
    }
        

}
