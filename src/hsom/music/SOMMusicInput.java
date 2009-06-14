
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

    private Vector<boolean[]> mask;
    // The range of our input values for the Music SOM 
    private float range = 1;
    private float max = 0;
    private float min = 0;

    private BufferedReader inputBuffer;
    private BufferedWriter outputBuffer;
    
    /**
      * The one argument constructor
      *
      * @param inFile 	the name of the input file
      */
    public SOMMusicInput(String inFile){

        super();
        findRange(inFile);
        setInput(parseFile(inFile));

    }

    public SOMMusicInput(String srcFile, String predFile){
        super();
        findRange(srcFile);
        setInput(parseFile(predFile));
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
        float mx=-999, mn=999;
        
        try{
            String preLineRead = inputBuffer.readLine();
            String lineRead;
            String delim = " \t\n\r\f,";
            
            while(preLineRead != null){
                lineRead = preLineRead.split(" ")[1];
                StringTokenizer tokenizer = new StringTokenizer(lineRead, delim);
                //tokenizer.nextToken();
                while(tokenizer.hasMoreTokens()){
                    float nextVal = Float.parseFloat(tokenizer.nextToken());
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

    /** Converts the vector inside the SOM back into its original input format
     *
     * @param in  The vector containint the SOM weights
     */
    @SuppressWarnings("unchecked")
    public void revert(Vector in, String outFile){
        try{
            FileWriter file = new FileWriter(outFile, true);
            outputBuffer = new BufferedWriter(file);

            for(int i=0; i<in.size(); i++){
                SOMVector<Float> curNodes = (SOMVector<Float>)in.elementAt(i);
                MusicVector<Float> output = new MusicVector<Float>();
                for(int j=0; j<curNodes.size(); j++){
                    float value = curNodes.elementAt(j)*range + min;
                    //doing some manual rounding of the values predicted
                    double leftSide = java.lang.Math.floor((double)value);
                    double rightSide = value - leftSide;
                    if(rightSide <= 0.1){
                        rightSide = 0;
                    }
                    else if(rightSide <= 0.3){
                        rightSide = 0.2;
                    }
                    else if(rightSide <= 0.5){
                        rightSide = 0.4;
                    }
                    else if(rightSide >= 0.9){
                        rightSide = 1;
                    }
                    else if(rightSide >= 0.7){
                        rightSide = 2;
                    }
                    else if(rightSide > 0.5){
                        rightSide = 0.6;
                    }
                    value = (float)(leftSide + rightSide);
                    output.addElement(value);
                }
                outputBuffer.write("Prediction " + i + ": " + output.toString() + '\n');

            }
            outputBuffer.close();
        }catch (IOException e){
            System.out.println("Problem opening the file: \""	+ outFile + "\" for writing.");
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
                    String token = tokenizer.nextToken();
                    float weight;
                    try{
                        weight = (Float.parseFloat(token) - (float)min)/range;
                    }catch(NumberFormatException e){
                        weight = Float.NaN;
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

    /**
     * Sets the mask according to the input values that have been read.
     * True if the value is available, false if the value is blank.
     */
    @SuppressWarnings("unchecked")
    private void setMask(){
        mask = new Vector();
        Vector input = super.getInput();
        for(int i=0; i<input.size(); i++){
            SOMVector<Float> curInput = (SOMVector<Float>)input.elementAt(i);
            boolean[] m = new boolean[curInput.size()];
            for(int j=0; j<curInput.size(); j++){
                if(curInput.elementAt(j).isNaN()){
                    m[j] = false;
                }
                else{
                    m[j] = true;
                }
            }
            mask.addElement(m);
        }
    }

    /**
     * Returns the mask for the partial matching
     * @return  The mask used for partial matching
     */
    public Vector<boolean[]> getMask(){
        return mask;
    }

    /**
     * Overrides the getAdjustedInput method in the SOMInput class
     * @param outputLength  The length of the adjusted input
     * @return  A vector of inputs adjusted to the specified length
     */
    @Override
    public Vector getAdjustedInput(int outputLength){
        Vector adjustedInput = super.getAdjustedInput(outputLength);
        Vector temp = super.getInput();
        setInput(adjustedInput);
        setMask();
        setInput(temp);
        return adjustedInput;
    }

 }