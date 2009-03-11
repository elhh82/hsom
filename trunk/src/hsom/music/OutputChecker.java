/**
 * Compares the inputs and the outputs and measures the similarities
 * @author Edwin Law Hui Hean
 */

package hsom.music;

import java.io.*;

public class OutputChecker {

    BufferedReader pitchReader, durationReader, outReader;
    String pitchFile, durationFile, outFile;
    String[] pitch, duration, output;

    /**
     * The 2 element constructor
     * @param pitch         The name of the pitch file
     * @param duration      The name of the duration file
     * @param outputFile    The name of the output file
     */
    public OutputChecker(String pitch, String duration, String output){
        pitchFile = pitch;
        durationFile = duration;
        outFile = output;
        try{
            pitchReader = new BufferedReader(new FileReader(pitchFile));
            durationReader = new BufferedReader(new FileReader(durationFile));
            outReader = new BufferedReader(new FileReader(outFile));
        }catch(FileNotFoundException e){
            System.out.println(e);
        }
        readFiles();
    }

    /**
     * Read in all the inputfiles and stores them line by line in string arrays
     */
    private void readFiles(){
        int numLines = 0;
        try{
            while(pitchReader.readLine() != null){
                numLines++;
            }
            pitchReader.close();
            pitchReader = new BufferedReader(new FileReader(pitchFile));
            pitch = new String[numLines];
            for(int i=0; i<numLines; i++){
                pitch[i] = pitchReader.readLine();
            }
            pitchReader.close();
            numLines = 0;
            while(durationReader.readLine() != null){
                numLines++;
            }
            durationReader.close();
            durationReader = new BufferedReader(new FileReader(durationFile));
            duration = new String[numLines];
            for(int i=0; i<numLines; i++){
                duration[i] = durationReader.readLine();
            }
            durationReader.close();
            numLines = 0;
            while(outReader.readLine() != null){
                numLines++;
            }
            outReader.close();
            outReader = new BufferedReader(new FileReader(outFile));
            output = new String[numLines];
            for(int i=0; i<numLines; i++){
                output[i] = outReader.readLine();
            }
            outReader.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }

    /**
     * Compares each output values with every single input value
     */
    public void fullCheck(){
        String pitchIn[][] = new String[pitch.length][];
        String durationIn[][] = new String[duration.length][];
        String pitchOut[];
        String durationOut[];
        int mostSimilar = 0;
        float score = 0, maxScore = 0, avgScore = 0;
        for(int i=0; i<pitch.length; i++){
            pitchIn[i] = pitch[i].split(" ")[1].split(",");
            durationIn[i] = duration[i].split(" ")[1].split(",");
        }
        for(int i=0; i<output.length/2; i++){
            String temp = output[i].split(" ")[2];
            pitchOut = temp.substring(1,temp.length()-1).split(",");
            temp = output[i+output.length/2].split(" ")[2];
            durationOut = temp.substring(1,temp.length()-1).split(",");
            for(int j=0; j<pitchIn.length; j++){
                for(int k=0; k<pitchOut.length; k++){
                    if(new Float(pitchIn[j][k]).compareTo(new Float(pitchOut[k])) == 0){
                        score = score + 1;
                    }
                    if(new Float(durationIn[j][k]).compareTo(new Float(durationOut[k])) == 0){
                        score = score + 1;
                    }
                }
                score = score / (pitchOut.length * 2);
                if(score > maxScore){
                    maxScore = score;
                    mostSimilar = j+1;
                }
                avgScore += score;
            }
            avgScore = avgScore/pitchIn.length;
            int max = new Float(maxScore*100).intValue();
            int avg = new Float(avgScore*100).intValue();
            System.out.println("Output " + (i+1) + ": Max Score: " + 
                                max + "% Avg Score: " + avg + "%");

            System.out.println("Most Similar: Input " + mostSimilar + "\n");
            score = maxScore = avgScore = 0;
        }
    }

    /**
     * Same as full check but only checks for the pitch values.
     */
    public void fullCheckPitch(){
        String pitchIn[][] = new String[pitch.length][];
        String durationIn[][] = new String[duration.length][];
        String pitchOut[];
        int mostSimilar = 0;
        float score = 0, maxScore = 0, avgScore = 0;
        for(int i=0; i<pitch.length; i++){
            pitchIn[i] = pitch[i].split(" ")[1].split(",");
            durationIn[i] = duration[i].split(" ")[1].split(",");
        }
        for(int i=0; i<output.length/2; i++){
            String temp = output[i].split(" ")[2];
            pitchOut = temp.substring(1,temp.length()-1).split(",");
            temp = output[i+output.length/2].split(" ")[2];
            for(int j=0; j<pitchIn.length; j++){
                for(int k=0; k<pitchOut.length; k++){
                    if(new Float(pitchIn[j][k]).compareTo(new Float(pitchOut[k])) == 0){
                        score = score + 1;
                    }
                }
                score = score / pitchOut.length;
                if(score > maxScore){
                    maxScore = score;
                    mostSimilar = j+1;
                }
                avgScore += score;
            }
            avgScore = avgScore/pitchIn.length;
            int max = new Float(maxScore*100).intValue();
            int avg = new Float(avgScore*100).intValue();
            System.out.println("Output " + (i+1) + ": Max Score: " +
                                max + "% Avg Score: " + avg + "%");
            System.out.println("Most Similar: Input " + mostSimilar + "\n");
            score = maxScore = avgScore = 0;
        }
    }

    /**
     * Compares each output with its corresponding input value
     */
    public void lineByLineCheck(){
        String pitchIn[][] = new String[pitch.length][];
        String durationIn[][] = new String[duration.length][];
        String pitchOut[];
        String durationOut[];
        float score = 0;
        for(int i=0; i<pitch.length; i++){
            pitchIn[i] = pitch[i].split(" ")[1].split(",");
            durationIn[i] = duration[i].split(" ")[1].split(",");
        }
        for(int i=0; i<output.length/2; i++){
            String temp = output[i].split(" ")[2];
            pitchOut = temp.substring(1,temp.length()-1).split(",");
            temp = output[i+output.length/2].split(" ")[2];
            durationOut = temp.substring(1,temp.length()-1).split(",");
            for(int k=0; k<pitchOut.length; k++){
                if(new Float(pitchIn[i][k]).compareTo(new Float(pitchOut[k])) == 0){
                    score = score + 1;
                }
                if(new Float(durationIn[i][k]).compareTo(new Float(durationOut[k])) == 0){
                    score = score + 1;
                }
            }
            score = score / (pitchOut.length * 2);
            int scr = new Float(score * 100).intValue();
            System.out.println("Output " + (i+1) + ": Score: " + scr + "%");
            score = 0;
        }
    }

    /**
     * The main function
     * @param args 3 arguments, the 2 input files and the output directory
     */
    public static void main(String[] args){
        OutputChecker checker = new OutputChecker(args[0], args[1], args[2]);
        checker.fullCheck();
        //checker.lineByLineCheck();
        //checker.fullCheckPitch();
    }
}
