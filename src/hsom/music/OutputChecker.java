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
     * Converts from the HSOM Spiral Array to a standard pitch
     * @param pitch
     * @param octave
     * @return the pitch in ABC format
     */
    private int getPitch(float pitch){

        int output = -1;
        int octave = 0, intPitch = 0;
        //determine the octave
        float decimalValue = pitch - new Float(pitch).intValue();
        if(decimalValue == 0.0){
            intPitch = new Float(pitch).intValue();
            octave = 0;
        }
        else if(decimalValue <= 0.5){
            intPitch = new Float(pitch).intValue();
            octave = 1;
        }
        else if(decimalValue > 0.5){
            intPitch = new Float(pitch).intValue() + 1;
            octave = -1;
        }
        else{
            System.out.println(decimalValue + " " + pitch);
            System.out.println("Unexpected pitch value in the input set");
        }
        switch(intPitch){
            case 0: output = 1; break;
            case 1: output = 8; break;
            case 2: output = 3; break;
            case 3: output = 10; break;
            case 4: output = 5; break;
            case 5: output = 0; break;
            case 6: output = 7; break;
            case 7: output = 2; break;
            case 8: output = 9; break;
            case 9: output = 4; break;
            case 10: output = 11; break;
            case 11: output = 6; break;
        }
        if(octave == 1){
            output += 12;
        }
        else if(octave == -1){
            output -= 12;
        }
        return output;
    }


    /**
     * Returns the contour of the pitch string.
     * The contour is obtained by subtracting each pitch with the pitch before it.
     * The contour is always one unit shorter than the pitch string.
     * @param values The input pitch values
     * @return       An int array containing the pitch contour
     */
    private int[] buildContour(String[] pitches){

        int pitchArray[] = new int[pitches.length];
        int contour[] = new int[pitches.length-1];
        for(int i=0;i<pitches.length; i++){
            pitchArray[i] = getPitch(new Float(pitches[i]));
        }
        for(int i=1; i<pitches.length; i++){
            contour[i-1] = pitchArray[i] - pitchArray[i-1];
        }        
        return contour;
    }

    /**
     * Builds a histogram (pitch frequency table) of the pitches. The octave of
     * the note is ignored.
     * @param pitches   The input pitch values
     * @return          The histogram containing the frequency of each pitch.
     */
    private int[] buildPitchHistogram(String[] pitches){

        int histogram[] = new int[12];
        for(int i=0; i<12; i++){
            histogram[i] = 0;
        }
        for(int i=0;i<pitches.length; i++){
            int modPitch = getPitch(new Float(pitches[i]))%12;
            histogram[modPitch]++;
        }
        return histogram;
    }

    /**
     * Builds a histogram for each input and output and compares them
     */
    public void histogramCheck(){
        String pitchIn[][] = new String[pitch.length][];
        String pitchOut[];
        int pitchInHistogram[][] = new int[pitch.length][];
        int mostSimilar = 0;
        double score = 0, maxScore = 0, avgScore = 0;
        for(int i=0; i<pitch.length; i++){
            pitchIn[i] = pitch[i].split(" ")[1].split(",");
            pitchInHistogram[i] = buildPitchHistogram(pitchIn[i]);
        }
        for(int i=0; i<output.length/2; i++){
            String temp = output[i].split(" ")[2];
            pitchOut = temp.substring(1,temp.length()-1).split(",");
            //build the contour for the output pitch
            int pitchOutHistogram[] = buildPitchHistogram(pitchOut);

            for(int j=0; j<pitchIn.length; j++){
                for(int k=0; k<pitchOutHistogram.length; k++){
                    int diff = (pitchInHistogram[j][k] - pitchOutHistogram[k]);
                    score += diff * diff;
                }
                score = 1/(java.lang.Math.sqrt(score)+1);
                if(score > maxScore){
                    maxScore = score;
                    mostSimilar = j;
                }
                avgScore += score;
            }
            avgScore = avgScore/pitchIn.length;
            int max = new Float(maxScore*100).intValue();
            int avg = new Float(avgScore*100).intValue();
            System.out.println("Output " + (i+1) + ": Max Score: " +
                                max + "% Avg Score: " + avg + "%");

            System.out.println("Most Similar: Input " + (mostSimilar+1) + "\n");
            score = maxScore = avgScore = 0;
        }
    }

    /**
     * Builds a contour for each input and output and compares them
     */
    public void contourCheck(){
        String pitchIn[][] = new String[pitch.length][];
        String pitchOut[];
        int pitchInContour[][] = new int[pitch.length][];
        int mostSimilar = 0;
        double score = 0, maxScore = 0, avgScore = 0;
        for(int i=0; i<pitch.length; i++){
            pitchIn[i] = pitch[i].split(" ")[1].split(",");
            pitchInContour[i] = buildContour(pitchIn[i]);
        }
        for(int i=0; i<output.length/2; i++){
            String temp = output[i].split(" ")[2];
            pitchOut = temp.substring(1,temp.length()-1).split(",");
            //build the contour for the output pitch
            int pitchOutContour[] = buildContour(pitchOut);

            for(int j=0; j<pitchIn.length; j++){
                for(int k=0; k<pitchOutContour.length; k++){
                    int diff = (pitchInContour[j][k] - pitchOutContour[k]);
                    score += diff * diff;
                }
                score = 1/(java.lang.Math.sqrt(score)+1);
                if(score > maxScore){
                    maxScore = score;
                    mostSimilar = j;
                }
                avgScore += score;
            }
            avgScore = avgScore/pitchIn.length;
            int max = new Float(maxScore*100).intValue();
            int avg = new Float(avgScore*100).intValue();
            System.out.println("Output " + (i+1) + ": Max Score: " +
                                max + "% Avg Score: " + avg + "%");

            System.out.println("Most Similar: Input " + (mostSimilar+1) + "\n");
            score = maxScore = avgScore = 0;
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
                    mostSimilar = j;
                }
                avgScore += score;
            }
            avgScore = avgScore/pitchIn.length;
            int max = new Float(maxScore*100).intValue();
            int avg = new Float(avgScore*100).intValue();
            System.out.println("Output " + (i+1) + ": Max Score: " + 
                                max + "% Avg Score: " + avg + "%");

            System.out.println("Most Similar: Input " +(mostSimilar+1) + "\n");
            score = maxScore = avgScore = 0;
        }
    }

    /**
     * Compares each output values with every single input value
     */
    public void fullCheckEuclidean(){
        String pitchIn[][] = new String[pitch.length][];
        String durationIn[][] = new String[duration.length][];
        String pitchOut[];
        String durationOut[];
        int mostSimilar = 0;
        double score = 0, maxScore = 0, avgScore = 0;
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
                    double diff = new Float(pitchOut[k]) - new Float(pitchIn[j][k]);
                    score += diff*diff;
                    diff = new Float(durationIn[j][k])- new Float(durationOut[k]);
                    score += diff*diff;
                }
                score = 1/ (java.lang.Math.sqrt(score) + 1);
                if(score > maxScore){
                    maxScore = score;
                    mostSimilar = j;
                }
                avgScore += score;
            }
            avgScore = avgScore/pitchIn.length;
            int max = new Float(maxScore*100).intValue();
            int avg = new Float(avgScore*100).intValue();
            System.out.println("Output " + (i+1) + ": Max Score: " +
                                max + "% Avg Score: " + avg + "%");

            System.out.println("Most Similar: Input " +(mostSimilar+1) + "\n");
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
                    mostSimilar = j;
                }
                avgScore += score;
            }
            avgScore = avgScore/pitchIn.length;
            int max = new Float(maxScore*100).intValue();
            int avg = new Float(avgScore*100).intValue();
            System.out.println("Output " + (i+1) + ": Max Score: " +
                                max + "% Avg Score: " + avg + "%");
            System.out.println("Most Similar: Input " + (mostSimilar+1) + "\n");
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
