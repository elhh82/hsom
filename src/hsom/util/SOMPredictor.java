/** Makes predictions basd on the contents of the som.
  *
  * @author elhh
  */

package hsom.util;

import java.util.Vector;
import hsom.core.*;

public class SOMPredictor implements Runnable{

    //the map for this trainer
    private SOMMap map;
    
    //the vector containing the inputs and outputs to the SOM
    private SOMInput input;
    private Vector output;

    //the thread instance
    private Thread runner;
    
    /**
      * The constructor for SOMPredictor for bottom or single SOMs.
      * 
      * @param predictionMap	The map that will be used by this predictor
      * @param in		The SOMInput for this trainer. We also pass in
      *				subclasses on the SOMInput.
      */
    public SOMPredictor(SOMMap predictionMap, SOMInput in){
        map = predictionMap;
        input = in;
    }

    /**
      * The constructor for SOMPredictor for higher level SOMs.
      * 
      * @param predictionMap	The map that will be used by this trainer
      * @param in		The SOMLinker for this trainer. 
      */

    public SOMPredictor(SOMMap predictionMap, SOMLinker in){
        map = predictionMap;
        input = in;
    }
    
    /**
      * returns the map that is being trained by this SOMTrainer
      * @return 	the SOM map that is associated with this trainer
      */
    public SOMMap getMap(){
        return map;
    }

    /**
      * returns the series of output that is produced by the inputs
      * @return 	the Vector containing all the outputs for the inputs given
      */
    public Vector getOutput(){
        return output;
    }
    
    /**
      * controls the running of the training 
      */
    public void start(){     
        runner = new Thread(this);
        runner.setPriority(Thread.MIN_PRIORITY);
        runner.start();
        try{
                runner.join();
        }catch(Exception e){}       
    }
    
    /**
     * A thread is issues here to start the prediction.
     * For each input, we first have to filter out the missing values and create
     * a mask for it. This mask is then sent to the Partial BMU finder.
     * Each partial BMU's coordinates is then stored as an output.
     * The outputs can be accessed using the getOutput() function.
     */
    @SuppressWarnings("unchecked")
    public void run(){
        int vectorLength = map.getNode(0,0).getVector().size();
        Vector inputVector = input.getAdjustedInput(vectorLength);
        int splitParts =  inputVector.size() / input.getInput().size();
        SOMNode bmu = null;
        SOMVector<Float> curInput = null;
        SOMVector<Float> curOutput = new SOMVector<Float>();
        
        for(int i=0; i<inputVector.size(); i++){
            
            curInput = (SOMVector<Float>)inputVector.elementAt(i);
            //build the mask for this input
            boolean[] mask = getMask(curInput);
            //get the partialBMU
            bmu = map.getPartialBMU(curInput, mask); 
            
            //store the output for this bmu
            curOutput.addElement(new Float((float)bmu.getX()/(float)map.getWidth()));
            curOutput.addElement(new Float((float)bmu.getY()/(float)map.getHeight()));
            
            //Once the outputs for all the inputs in one input (before adjustment)
            //has been found, we add them into the main output vector and start
            //creating a new output.
            if(i % splitParts == splitParts - 1){
                output.addElement(curOutput);
                curOutput = new SOMVector<Float>();
            }
        }       
    }
    
    /**
     * Scans the vector and creates a mask to filter out the missing values.
     * An input of -999 denotes an blank value, make sure your input corresponds
     * to this for the predictor to work.
     * @param input the input vector to scan
     * @return a boolean array that is the mask.
     */
    public boolean[] getMask(SOMVector<Float> input){
        boolean[] mask = new boolean[input.size()];
        for(int i=0; i<input.size(); i++){
            if(input.elementAt(i) == -999){
                mask[i] = false;
            }
            else mask[i] = true;
        }
        return mask;
    }
    
    
}
