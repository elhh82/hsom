/**
  * Converts scores from Kern to a HSOM readable format.
  * @author Edwin Law Hui Hean
  */

package kern;

import java.util.*;
import java.io.*;


public class KernConverter {

    private List<Note> noteList = new ArrayList<Note>();
    private String inFile;
    private String outFile;
    private String keySignature;
    private String convertedNoteList;
    
    BufferedReader inputStream = null;
    BufferedWriter outputStream = null;
    
    /**
     * The constructor that takes the inFile and outFile name
     * @param in the name of the input file
     * @param out the name of the output file
     */
    public KernConverter(String in, String out){
        inFile = in;
        outFile = out;
    };
    
    /**
     * Parses the file and produces the output
     */
    private void parseFile(){
        try{
            inputStream = new BufferedReader(new FileReader(inFile));
            //outputStream = new BufferedWriter(new FileWriter(outFile));
            
            String s;
            while((s = inputStream.readLine()) != null ){
                //extract the key
                if(!s.isEmpty() && s.charAt(0) == '*' && s.charAt(1) == 'k')
                {
                    keySignature = s.substring(3, s.length()-1);
                }
                //each string is passed into a tokenizer and checked
                if(!s.isEmpty() && s.charAt(0) != '!' && s.charAt(0) != '*' 
                   && s.charAt(0) != '='){
                   //clean up the string to remove the '{' and '}' signs
                   if(s.charAt(0) == '{') 
                       s = s.substring(1);
                   if(s.charAt(s.length()-1) == '}') 
                       s = s.substring(0,s.length()-1);
                   addNote(s);
                }    
               
            }
        }
        catch(Exception e){
            System.out.println(e);
        }        
    }
    
    /**
     * adds a note to the list of notes
     * @param input
     */
    private void addNote(String input){
        String pitch = "";
        String duration = "";
        float durationFloat = 0;
        
        for(char c: input.toCharArray()){
            if(Character.isLetter(c) || c == '#' || c == '-'){
                pitch = pitch + c;                
            }
            else{
                duration = duration + c;                
            }  
        }
               
        if(duration.charAt(duration.length()-1) == '.'){
            durationFloat = Float.parseFloat(duration.substring(0, duration.length()-1));
            durationFloat /= 1.5;
        }
        else{
            durationFloat = Float.parseFloat(duration);            
        }
        
        noteList.add(new Note(durationFloat, pitch));
    }
    
    /**
     * Converts the note list into the HSOM representation
     */
    private void convert(){
        //convert the keysignature
        int key = findKey();
        
        //find the shortest note
        float minDuration = 0;
        for(Iterator<?> it = noteList.iterator(); it.hasNext();){
            float currDuration = ((Note)it.next()).getDuration();
            minDuration = (currDuration > minDuration) ? currDuration : minDuration;
        }
        
        //convert each note 
        String pitchOutput = "";
        for(Iterator<?> it = noteList.iterator(); it.hasNext();){
            String note = ((Note)it.next()).toHSOMPitchNotation(key, (int)minDuration);
            if(!pitchOutput.isEmpty()) pitchOutput = pitchOutput + "," + note;
            else pitchOutput = note;
        }
        
        System.out.println(pitchOutput);
        
    }
    
    /**
     * Finds the key based on the key signature
     * @return The note in midi that represents the key
     */
    private int findKey(){
        int numAcc = keySignature.length()/2;
        boolean sharp = keySignature.charAt(1) == '#';
        int key = 0;
        
        if(sharp){
            switch (numAcc){ 
                case 0: key = 60; break;
                case 1: key = 67; break;
                case 2: key = 62; break;
                case 3: key = 69; break;
                case 4: key = 64; break;
                case 5: key = 71; break;
                case 6: key = 66; break;
                case 7: key = 61; break;
                default: key = 0; break;
            }                
        }
        else{
            switch (numAcc){ 
                case 0: key = 60; break;
                case 1: key = 65; break;
                case 2: key = 70; break;
                case 3: key = 63; break;
                case 4: key = 68; break;
                case 5: key = 61; break;
                case 6: key = 66; break;
                case 7: key = 71; break;
                default: key = 0; break;
            }
        }
        System.out.println(key);
        return key;
    }
        
    /**
     * Prints out the keySignature and the corresponsing notes
     */
    private void print(){
        System.out.println("Key Signature: " + keySignature);
        for(Iterator<?> it = noteList.iterator(); it.hasNext();){
            Note temp = (Note)it.next();
            System.out.println(temp.toString());
        }
        
    }
    
    /**
     * Read in 2 file names for the input and output files respectively.
     * @param args The file names
     */
    public static void main(String args[]){
        KernConverter kc = new KernConverter(args[0], args[1]);
        kc.parseFile();
        kc.convert();
        kc.print();
        //outFile = args[1]; 
        
    }
        
}
