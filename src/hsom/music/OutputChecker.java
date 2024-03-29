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

        int outputValue = -1;
        int octave = 0, intPitch = 0;
        //determine the octave
        int decimalValue = java.lang.Math.round((pitch - new Float(pitch).intValue()) *(float)10);
        if(decimalValue == 0){
            intPitch = new Float(pitch).intValue();
            octave = 0;
        }
        else if(decimalValue == 4){
            intPitch = new Float(pitch).intValue();
            octave = 2;
        }
        else if(decimalValue == 2){
            intPitch = new Float(pitch).intValue();
            octave = 1;
        }
        else if(decimalValue == 6){
            intPitch = new Float(pitch).intValue() + 1;
            octave = -2;
        }
        else if(decimalValue == 8){
            intPitch = new Float(pitch).intValue() + 1;
            octave = -1;
        }
        else{
            System.out.println(decimalValue + " " + pitch);
            System.out.println("Unexpected pitch value in the input set");
        }
        switch(intPitch){
            case 0: outputValue = 1; break;
            case 1: outputValue = 8; break;
            case 2: outputValue = 3; break;
            case 3: outputValue = 10; break;
            case 4: outputValue = 5; break;
            case 5: outputValue = 0; break;
            case 6: outputValue = 7; break;
            case 7: outputValue = 2; break;
            case 8: outputValue = 9; break;
            case 9: outputValue = 4; break;
            case 10: outputValue = 11; break;
            case 11: outputValue = 6; break;
        }
        if(octave == 1){
            outputValue += 12;
        }
        else if(octave == -1){
            outputValue -= 12;
        }
        return outputValue;
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
            int modPitch = (getPitch(new Float(pitches[i]))+12)%12;
            histogram[modPitch]++;
        }
        int pitchTotal = 0;
        for(int i=0;i<12;i++){
            pitchTotal += histogram[i];
        }
        if(pitchTotal != 64){
            System.out.println(pitchTotal + " is not equal to 64.");
        }
        return histogram;
    }

    /**
     * builds a histogram of durations
     */
    public int[] buildDurationHistogram(String[] durations){

        int histogram[] = new int[17];
        for(int i=0; i<17; i++){
            histogram[i] = 0;
        }
        for(int i=0;i<durations.length; i++){
            int count = 0;
            if(java.lang.Math.round(new Double(durations[i])) == 0){
                histogram[0]++;
            }
            if(java.lang.Math.round(new Double(durations[i])) >= 2){
                count++;
                if(i<durations.length-1){
                    i++;
                    while(java.lang.Math.round(new Double(durations[i])) == 1){
                        count++;
                        if(i==durations.length-1){
                            i++;
                            break;
                        }
                        i++;
                    }
                    i--;
                }
                histogram[count]++;
            }
            
        }
        int total=0;
        for(int i=0;i<17;i++){
            if(i!=0){
                total += i*histogram[i];
            }
            else{
                total += histogram[i];
            }
        }
        if(total!=64){
            System.out.println("Error in duration histogram count");
        }
        return histogram;
    }

    /**
     * Prints out the pitch histogram
     * 
     */
    public void getPitchHistogram(){
        String pitchOut[];
        for(int i=0; i<output.length/2; i++){
            String temp = output[i].split(" ")[2];
            pitchOut = temp.substring(1,temp.length()-1).split(",");
            //build the contour for the output pitch
            int pitchOutHistogram[] = buildPitchHistogram(pitchOut);
            System.out.print("Histogram-" + i + ": ");
            for(int j=0; j<12; j++){
                System.out.print(pitchOutHistogram[j]);
                System.out.print(",");
            }
            System.out.println("");
        }

    }

    /**
     * Prints out the duration histogram
     */
       public void getDurationHistogram(){
        String durationOut[];
        System.out.println(output.length);
        for(int i=output.length/2; i<output.length; i++){
            String temp = output[i].split(" ")[2];
            durationOut = temp.substring(1,temp.length()-1).split(",");
            //build the contour for the output pitch
            int durationOutHistogram[] = buildDurationHistogram(durationOut);
            int tempVal=i-output.length/2;
            System.out.print("Histogram-" + tempVal + ": ");
            for(int j=0; j<17; j++){
                System.out.print(durationOutHistogram[j]);
                System.out.print(",");
            }
            System.out.println("");
        }

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
                score = java.lang.Math.sqrt(java.lang.Math.sqrt(score));
                if(score > maxScore){
                    maxScore = score;
                    mostSimilar = j;
                }
                avgScore += score;
            }
            avgScore = avgScore/pitchIn.length;
            System.out.println("Output " + (i+1) + ": Max Score: " +
                                maxScore + "\nAvg Score: " + avgScore);

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
                score = java.lang.Math.sqrt(java.lang.Math.sqrt(score));
                if(score > maxScore){
                    maxScore = score;
                    mostSimilar = j;
                }
                avgScore += score;
            }
            avgScore = avgScore/pitchIn.length;
            System.out.println("Output " + (i+1) + ": Max Score: " +
                                maxScore + "\nAvg Score: " + avgScore);

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
                score = java.lang.Math.sqrt(java.lang.Math.sqrt(score));
                if(score > maxScore){
                    maxScore = score;
                    mostSimilar = j;
                }
                avgScore += score;
            }
            avgScore = avgScore/pitchIn.length;
            System.out.println("Output " + (i+1) + ": Max Score: " +
                                maxScore + "\nAvg Score: " + avgScore);

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
        //checker.getPitchHistogram();
        checker.getDurationHistogram();
        //System.out.println("Euclidean Analysis");
        //checker.fullCheckEuclidean();
        //System.out.println("-------------------------");
        //System.out.println("Contour Analysis");
        //checker.contourCheck();
        //System.out.println("-------------------------");
        //System.out.println("Histogram Analysis");
        //checker.histogramCheck();
        //checker.lineByLineCheck();
        //checker.fullCheckPitch();
    }
}
