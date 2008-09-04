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
                //each string is passed into a tokenizer and checked
                if(!s.isEmpty() && s.charAt(0) != '!' && s.charAt(0) != '*' 
                   && s.charAt(0) != '='){
                   //clean up the string to remove the '{' and '}' signs
                   if(s.charAt(0) == '{') 
                       s = s.substring(1);
                   if(s.charAt(s.length()-1) == '}') 
                       s = s.substring(0,s.length()-1);
                   addNote(s);
                   System.out.println(s);
                }    
               
            }
        }
        catch(Exception e){
            System.out.println(e);
        }        
    }
    
    private void addNote(String input){
        
        
    }
    
    
    /**
     * Read in 2 file names for the input and output files respectively.
     * @param args The file names
     */
    public static void main(String args[]){
        KernConverter kc = new KernConverter(args[0], args[1]);
        kc.parseFile();
        //outFile = args[1];        
    }
        
}
