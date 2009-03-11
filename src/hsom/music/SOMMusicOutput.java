/*
 * SOM Output for the Music package.
 * Takes in SOMMusicInputs as inputs instead of SOMInput
 * Also allows us to do get outputs from partial inputs
 */

package hsom.music;

import hsom.core.*;
import hsom.util.*;
import java.util.Vector;


public class SOMMusicOutput extends SOMOutput{

    SOMMusicInput musicInput;

    /**
     * The constructor
     * @param m A SOMMap
     * @param i The SOMMusicInput to the SOMMap
     */
    public SOMMusicOutput(SOMMap m, SOMMusicInput i){
        super(m, i);
        musicInput = i;
    }

    /**
     * Similar the the SOMTrainer, but no training is done, we only search for
     * all the outputs
     */
    @SuppressWarnings("unchecked")
    @Override
    public void run(){
        ranonce = true;
        //the paramaters that will be used during the training.
        int vectorLength = map.getNode(0,0).getVector().size();
        Vector inputVector;
        Vector<boolean[]> inputMask;
        SOMNode bmu=null;
        inputVector = input.getAdjustedInput(vectorLength);
        inputMask = musicInput.getMask();
        int splitParts =  inputVector.size() / input.getInput().size();
        SOMVector<Float> curInput = null;
        SOMVector<Float> curOutput = new SOMVector<Float>();
        boolean[] curMask;
        output = new Vector();
        for(int i=0; i<inputVector.size(); i++){
            //get the first part of the input and find its bmu
            curInput = (SOMVector<Float>)inputVector.elementAt(i);
            curMask = inputMask.elementAt(i);
            bmu = map.getPartialBMU(curInput, curMask);
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
}
