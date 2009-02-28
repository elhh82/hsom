/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hsom.util;

import hsom.core.*;
import java.util.Vector;

/**
 *
 * @author elhh
 */
public class SOMOutput extends SOMTrainer {

    private boolean ranonce;

    public SOMOutput(SOMMap m, SOMInput i){
        super(m, i);
        ranonce = false;
    }

    /**
      * controls the running of the training by specifying how many iterations
      * it will be run.
      * @param runs	The number of times the training will be run
      */
    @Override
    public void start(int n){
        
        if(!ranonce){
            runner = new Thread(this);
            runner.setPriority(Thread.MIN_PRIORITY);
            runner.start();
            try{
                    runner.join();
            }catch(Exception e){}
        }
    }

    /**
     * Runs it once
     */
    public void start(){
        start(1);
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
        SOMNode bmu=null;
        inputVector = input.getAdjustedInput(vectorLength);
        int splitParts =  inputVector.size() / input.getInput().size();
        SOMVector<Float> curInput = null;
        SOMVector<Float> curOutput = new SOMVector<Float>();
        output = new Vector();
        for(int i=0; i<inputVector.size(); i++){
            //get the first part of the input and find its bmu
            curInput = (SOMVector<Float>)inputVector.elementAt(i);
            bmu = map.getBMU(curInput);
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
