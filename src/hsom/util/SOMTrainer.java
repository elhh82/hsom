
/** SOMTrainer trains the SOM
  *
  * @author Edwin Law Hui Hean
  */
 
package hsom.util;

import java.util.Vector;
import hsom.core.*;

public class SOMTrainer implements Runnable{
	
    //Learning rate that can be adjusted to tweak the learning algorithm.
    private double startLearningRate = 0.1;

    //Number of iteration can be changed to suit needs.
    private int	maxIterations = 1000;

    //These 2 are non adjustable parameters that depends on the SOM map size.
    private double MAP_RADIUS;
    private double TIME_CONSTANT;

    //the map for this trainer
    private SOMMap map;

    //iteration counter
    private int iteration;

    //the vector containing the inputs and outputs to the SOM
    private SOMInput input;
    private Vector output;

    //the thread instance
    private Thread runner;

    /**
      * The constructor for SOMTrainer for bottom or single SOMs.
      * 
      * @param mapToTrain	The map that will be used by this trainer
      * @param in			The SOMInput for this trainer. We also pass in
      *				subclasses on the SOMInput.
      */
    public SOMTrainer(SOMMap mapToTrain, SOMInput in){
        map = mapToTrain;
        input = in;
        iteration = 0;
    }

    /**
      * The constructor for SOMTrainer for higher level SOMs.
      * 
      * @param mapToTrain	The map that will be used by this trainer
      * @param in			The SOMInput for this trainer. We also pass in
      *				subclasses on the SOMInput.
      */

    public SOMTrainer(SOMMap mapToTrain, SOMLinker in){
        map = mapToTrain;
        input = in;
        iteration = 0;
    }	

    /** 
      * returns the neighbourhood radius based on the size of the map
      * @param iteration 	the number of iterations the SOM has been trained
      * @return			the size of the neighbourhood affected
      */
    private double getNeighborhoodRadius(double iteration) {
        return MAP_RADIUS * Math.exp(-iteration/TIME_CONSTANT);
    }

    /** 
      * returns the distance fall off rate based on the size of the map
      * @param distSq 		the squared distance 
      * @param radius		the radius of the neighbourhood affected
      * @return			the rate at which the effect of training 
      *				decreases as distance increases
      */
    private double getDistanceFalloff(double distSq, double radius) {
        double radiusSq = radius * radius;
        return Math.exp(-(distSq)/(2 * radiusSq));
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
      * controls the running of the training by specifying how many iterations 
      * it will be run.
      * @param runs	The number of times the training will be run
      */
    public void start(int runs){

        for(int i=0; i<runs; i++){
            runner = new Thread(this);
            runner.setPriority(Thread.MIN_PRIORITY);
            runner.start();
            try{
                    runner.join();
            }catch(Exception e){}
        }
    }

    /**
    * A thread is issued here to do the training of the SOM.
    * The training is done according to the SOM algorithm.
    * Each input sequence also produces one output of 2 values.
    * This output value can be accessed using the getOutput() function.
    * The output is the X and Ycoordinates of the BMU that corresponds to 
    * each input value.
    */
    @SuppressWarnings("unchecked")
    public void run(){

        //the paramaters that will be used during the training.
        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();
        int xStart, yStart, xEnd, yEnd;
        int vectorLength = map.getNode(0,0).getVector().size();
        double dist, dFalloff;
        Vector inputVector;

        //the constants that will be used for training
        MAP_RADIUS = Math.max(mapWidth, mapHeight)/2;
        TIME_CONSTANT = maxIterations / Math.log(MAP_RADIUS);

        double nbhRadius = getNeighborhoodRadius(iteration);
        SOMNode bmu=null, temp=null;
        inputVector = input.getAdjustedInput(vectorLength);
        int splitParts =  inputVector.size() / input.getInput().size();
        SOMNode[][] inputNodes = input.getNodes(inputVector);
        SOMVector<Float> curInput = null;
        SOMVector<Float> curOutput = new SOMVector<Float>();
        double learningRate = startLearningRate * 
                              Math.exp(-(double)iteration/maxIterations);        
        output = new Vector();
        for(int i=0; i<inputVector.size(); i++){
            //get the first part of the input and find its bmu
            curInput = (SOMVector<Float>)inputVector.elementAt(i);			
            bmu = map.getBMU(curInput);
            if(inputNodes != null) setDownLinks(bmu, inputNodes[i]);
            //store the output for this bmu
            curOutput.addElement(new Float((float)bmu.getX()/(float)map.getWidth()));
            curOutput.addElement(new Float((float)bmu.getY()/(float)map.getHeight()));
            //Once we get the bmu, now we find its neighbourhood
            xStart = (int)(bmu.getX() - nbhRadius - 1);
            yStart = (int)(bmu.getY() - nbhRadius - 1);
            xEnd = (int)(xStart + (nbhRadius * 2) + 1);
            yEnd = (int)(yStart + (nbhRadius * 2) + 1);
            if(xStart < 0) xStart = 0;
            if(yStart < 0) yStart = 0;
            if(xEnd > mapWidth) xEnd = mapWidth;
            if(yEnd > mapHeight) yEnd = mapHeight;

            //adjust all the weights of nodes that fall within the neighbourhood
            for (int x=xStart; x<xEnd; x++) {
                for (int y=yStart; y<yEnd; y++) {
                    temp = map.getNode(x,y);
                    dist = bmu.distance(temp);
                    if (dist <= (nbhRadius * nbhRadius)) {
                        dFalloff = getDistanceFalloff(dist, nbhRadius);
                        temp.adjustWeights(curInput, learningRate, dFalloff);
                    }
                }
            }

            //Once the outputs for all the inputs in one input (before adjustment)
            //has been found, we add them into the main output vector and start
            //creating a new output.
            if(i % splitParts == splitParts - 1){
                output.addElement(curOutput);
                curOutput = new SOMVector<Float>();
            }
        }

        iteration++;
        if(iteration%50 == 0) System.out.println("Iteration: " + iteration); 
    }	
    
    /**
     * This function sets the links from the current bmu to the nodes that are
     * its source.
     * @param bmu   The node where the links are to be set
     * @param nodesToLink The nodes to link to
     */
    private void setDownLinks(SOMNode bmu, SOMNode[] nodesToLink){
        
        
    }
	
	
		
}