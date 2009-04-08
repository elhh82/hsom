/**
  * Converts scores from Kern to a ABC format.
  * @author Edwin Law Hui Hean
  */

package kern;

import java.io.*;

public class KernToABC {
    KernConverter kc;
    String melody;

    /**
     * The constructor
     * @param inFile    The input file
     * @param outFile   The output file
     */
    KernToABC(String inFile, String outFile){
        kc = new KernConverter(inFile, outFile);
        kc.start();
    }

    private void convert(){
        String pitchValues[] = kc.getPitch().split(",");
        String durationValues[] = kc.getDuration().split(",");
       
        
        melody = "";
        for(int j=0; j<pitchValues.length; ){
            //find the length of the note
            int noteLength = 1;
            if(new Float(durationValues[j]).intValue() == 1){
                for(int k=j+1; k<durationValues.length; k++){
                    float temp = new Float(durationValues[k]).intValue();
                    if(temp == 1 || temp == -1) break;
                    else noteLength++;
                }
            }
            else{
                for(int k=j+1; k<durationValues.length; k++){
                    float temp = new Float(durationValues[k]).intValue();
                    if(temp == 1) break;
                    else noteLength++;
                }
            }
            //find the pitch of the note
            String note = getPitch(Float.parseFloat(pitchValues[j]));

            switch(noteLength){
                case 1: note = note + "1/2"; break;
                case 2: break;
                case 3: note = note + "3/2"; break;
                case 4: note = note + "2"; break;
                case 6: note = note + "3"; break;
                case 8: note = note + "4"; break;
                default: System.out.println("Note length error " + noteLength);
            }
            melody = melody + " " + note;
            j += noteLength;
            //add a bar line
            if(j % 8 == 0) melody = melody + " |";
            //adds a carriage return
            if(j % 16 == 0 && j < pitchValues.length) melody = melody + '\n';
        }      
    }

    /**
     * Converts from the HSOM Spiral Array to an ABC pitch
     * @param pitch
     * @param octave
     * @return the pitch in ABC format
     */
    private String getPitch(float pitch){

        String output = "";
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

        if(octave == 0){
            switch(intPitch){
                case 0: output = "^C"; break;
                case 1: output = "^G"; break;
                case 2: output = "^D"; break;
                case 3: output = "^A"; break;
                case 4: output = "F"; break;
                case 5: output = "C"; break;
                case 6: output = "G"; break;
                case 7: output = "D"; break;
                case 8: output = "A"; break;
                case 9: output = "E"; break;
                case 10: output = "B"; break;
                case 11: output = "^F"; break;
                case 99: output = "z"; break;
            }
        }
        else if(octave == 1){
            switch(intPitch){
                case 0: output = "^c"; break;
                case 1: output = "^g"; break;
                case 2: output = "^d"; break;
                case 3: output = "^a"; break;
                case 4: output = "f"; break;
                case 5: output = "c"; break;
                case 6: output = "g"; break;
                case 7: output = "d"; break;
                case 8: output = "a"; break;
                case 9: output = "e"; break;
                case 10: output = "b"; break;
                case 11: output = "^f"; break;
                case 99: output = "z"; break;
            }
        }
        else if(octave == -1){
            switch(intPitch){
                case 0: output = "^C,"; break;
                case 1: output = "^G,"; break;
                case 2: output = "^D,"; break;
                case 3: output = "^A,"; break;
                case 4: output = "F,"; break;
                case 5: output = "C,"; break;
                case 6: output = "G,"; break;
                case 7: output = "D,"; break;
                case 8: output = "A,"; break;
                case 9: output = "E,"; break;
                case 10: output = "B,"; break;
                case 11: output = "^F,"; break;
                case 99: output = "z"; break;
          }
        }
        return output;
    }

    /**
     * Write the output to file
     * @param outFile the output file name
     */
    private void writeOutput(String outFile){
        System.out.println(kc.getPitch());
        String outputHeader =  "% HSOM Predicted Output\n" +
                                "X:1\nT:ABC Output " + "\n" +
                                "Q:1/8=120\n"+
                                "K:c\n";
        melody = melody + "|" + "\n";
        try{
            FileWriter file = new FileWriter(outFile + ".abc");
            BufferedWriter outputBuffer = new BufferedWriter(file);
            outputBuffer.write(outputHeader);
            outputBuffer.write(melody);
            outputBuffer.close();
        }catch(Exception e){}

    }

    public static void main(String args[]){
        KernToABC converter = new KernToABC(args[0], args[1]);
        converter.convert();
        converter.writeOutput(args[1]);
    }
}
