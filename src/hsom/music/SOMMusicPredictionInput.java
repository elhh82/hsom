
/** SOMMusicPredictionInput parses the partial melodies for prediction.
  *
  * @author Edwin Law Hui Hean
  */
  
 package hsom.music;
 
 import hsom.util.SOMInput;
 import hsom.core.SOMVector;
 import java.util.*;
 import java.io.*;
 
public class SOMMusicPredictionInput extends SOMInput{
	 
    // The range of our input values for the Music SOM 
    private int range = 1; 
    private int max = 0;
    private int min = 0;

    private BufferedReader inputBuffer;

    /**
      * The one argument constructor
      *
      * @param inFile 	the name of the input file
      */
    public SOMMusicPredictionInput(String predictionFile, String sourceFile){

        super();
        findRange(sourceFile);
        setInput(parseFile(predictionFile));

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
    
    /**
     * Finds and sets the maximum value, minimum value and the range of the input notes
     * @param inFile the file containing the input
     */
    private void findRange(String inFile){
        readFile(inFile);
        int mx=-999, mn=999;
        
        try{
            String preLineRead = inputBuffer.readLine();
            String lineRead;
            String delim = " \t\n\r\f,";
            
            while(preLineRead != null){
                lineRead = preLineRead.split(" ")[1];
                StringTokenizer tokenizer = new StringTokenizer(lineRead, delim);
                //tokenizer.nextToken();
                while(tokenizer.hasMoreTokens()){
                    int nextVal = Integer.parseInt(tokenizer.nextToken());
                   
                    if(nextVal > mx) mx = nextVal;
                    if(nextVal < mn) mn = nextVal;
                }
                preLineRead = inputBuffer.readLine();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        max = mx;
        min = mn;
        range = mx - mn;
        
    }
    
    /** Returns the range of the original input
     * 
     * @return The range of the original input file
     */
    public int getRange(){
        
        return range;
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
            String preLineRead = inputBuffer.readLine();
            String lineRead;
            String delim = " \t\n\r\f,-";

            while(preLineRead != null){ 
                
                lineRead = preLineRead.split(" ")[1];
                if(lineRead.length() == 0) break; //in case theres a blank line in the text file
                SOMVector inputLine = new SOMVector();
                StringTokenizer tokenizer = new StringTokenizer(lineRead, delim);

                //skip the label of the line of input
                //tokenizer.nextToken();	

                //add each element in the line into a SOMVector
                while(tokenizer.hasMoreTokens()){
                    float weight = 0;
                    
                    try{
                        weight = (Float.parseFloat(tokenizer.nextToken()) - (float)min)/range;										
                    }
                    catch(NumberFormatException e){
                        weight = -999;
                    }
                    inputLine.addElement(weight);
                }
                //add each SOMVcetor into the main input Vector				
                in.addElement(inputLine);
                preLineRead = inputBuffer.readLine();					
            }
        }catch(IOException e){
                System.out.println("There is a problem with the parsing of the input file");
        }	

        return in;	
    }

 }
