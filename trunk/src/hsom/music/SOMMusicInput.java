
/** SOMMusicInput parses the melodies file for the SOM Music project.
  *
  * @author Edwin Law Hui Hean
  */
  
 package hsom.music;
 
 import hsom.util.SOMInput;
 import hsom.core.SOMVector;
 import java.util.*;
 import java.io.*;
 
 
 public class SOMMusicInput extends SOMInput{
	 
    // The range of our input values for the Music SOM is 
    // always from 0-35. This is 3 octaves.
    private static final int RANGE = 35; 

    private BufferedReader inputBuffer;

    /**
      * The one argument constructor
      *
      * @param inFile 	the name of the input file
      */
    public SOMMusicInput(String inFile){

        super();
        setInput(parseFile(inFile));

    }

    /** We read the whole file and wrap it around a BufferedReader
      *
      * @param inFile 	the name of the input file
      */
    private void readFile(String inFile){

        try{
            FileReader file = new FileReader(inFile);
            inputBuffer = new BufferedReader(file);
        }catch (FileNotFoundException e){
            System.out.println("The file: \""	+ inFile + "\" could not be found");
        }   		

    }

    /** Here we parse the input file and put it into the vectors
      *
      * @param inFile 	the name of the input file
      * @return 		A vector containing the parse inputs from the file  
      */
    @SuppressWarnings("unchecked")
    private Vector parseFile(String inFile){

        Vector in = new Vector();

        readFile(inFile);

        try{
            String lineRead = inputBuffer.readLine();
            String delim = " \t\n\r\f,-";

            while(lineRead != null){ 

                if(lineRead.length() == 0) break; //in case theres a blank line in the text file
                SOMVector inputLine = new SOMVector();
                StringTokenizer tokenizer = new StringTokenizer(lineRead, delim);

                //skip the label of the line of input
                tokenizer.nextToken();	

                //add each element in the line into a SOMVector
                while(tokenizer.hasMoreTokens()){	
                    float weight = Float.parseFloat(tokenizer.nextToken())/RANGE;										
                    inputLine.addElement(weight);
                }
                //add each SOMVcetor into the main input Vector				
                in.addElement(inputLine);
                lineRead = inputBuffer.readLine();					
            }
        }catch(IOException e){
                System.out.println("There is a problem with the parsing of the input file");
        }	

        return in;	
    }

 }