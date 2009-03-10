/**
  * Converts scores from HSOM output format to the ABC format
  * @author Edwin Law Hui Hean
  */

package hsom.music;

import java.io.*;

public class ABCConverter {
    
    BufferedReader predictionReader;
    
    /**
     * Constructor for the ABC Converter, reads the two input files
     * @param pitch the pitch file
     * @param duration the duration file
     */
    public ABCConverter(String predicted){
        try{
            predictionReader = new BufferedReader(new FileReader(predicted));
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    /**
     * Goes through the input files and extract the notes
     */
    private void parseNotes(){
        try{     
            String line;
            while((line = predictionReader.readLine()) != null){
                String[] pitch = line.split(" ");
                String[] duration = predictionReader.readLine().split(" ");
                
                //find the actual notes and its length
                String[] pitchValues = pitch[2].substring(1,pitch[2].length()-1).split(",");
                String[] durationValues = duration[2].substring(1,duration[2].length()-1).split(",");
                
                
                String melody = "";
                for(int i=0; i<pitchValues.length; ){                
                    //find the length of the note
                    int noteLength = 1;
                    for(int j=i/2+1; j<durationValues.length; j++){
                        if(Float.parseFloat(durationValues[j]) == 1.0) break;
                        else noteLength++;
                    }
                    //find the pitch of the note
                    String note = getPitch(Float.parseFloat(pitchValues[i]));               

                    switch(noteLength){
                        case 1: note = note + "1/2"; break;
                        case 2: break;
                        case 3: note = note + "3/2"; break;
                        case 4: note = note + "2"; break;
                        case 6: note = note + "3"; break;
                        case 8: note = note + "4"; break;
                        default: System.out.println("Note length error");
                    }               
                    melody = melody + " " + note;
                    i += noteLength;  
                    //add a bar line
                    if(i % 16 == 0) melody = melody + " |";
                }
                //adds a double barline
                melody = melody + "|";
                System.out.println(melody);
                 
            }
        }
        catch(Exception e){
            System.out.println(e);
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
        if(decimalValue == 0.2){
            intPitch = new Float(pitch).intValue();
            octave = 1;            
        }
        else if(decimalValue == 0.8){
            intPitch = new Float(pitch).intValue() + 1;
            octave = -1;
        }
        else if(decimalValue == 0.0){
            intPitch = new Float(pitch).intValue();
            octave = 0;
        }
        else{
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
          } 
        }
        
        return output;
    }
    
    /**
     * The main function
     * @param args 3 arguments, the 2 input files and the output directory
     */
    public static void main(String[] args){
        ABCConverter converter = new ABCConverter(args[0]);
       converter.parseNotes();
    }
}
