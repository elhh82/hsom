
/** SOMLinker is used to connect two or more SOMs
  * It takes the ouputs from the trainers of all the lower SOMs,
  * parses them, and produces an input for the higher SOM.
  * We can have one or more lower SOM, but usually only one
  * Higher SOM. We only implement a 1-1 and 2-1 linkage here.
  * <p>
  * SOMLinker subclsses the SOMInput abstract class.
  *
  * @author Edwin Law Hui Hean
  */
  
package hsom.util;

import java.util.Vector;
import java.io.Serializable;
import hsom.core.SOMVector;
import hsom.core.SOMNode;

public class SOMLinker extends SOMInput implements Serializable{
	
    private SOMTrainer[] sourceSOM;
    private SOMTrainer higherSOM;
    //private HashMap linkWeights;
    //private boolean linkTracking = false;


    /**
      * The 1-1 constructor
      * @param lowerA 		the trainer for the first lower SOM
      */	
    public SOMLinker(SOMTrainer lowerA){
        super();
        sourceSOM = new SOMTrainer[1];
        sourceSOM[0] = lowerA;

    }

    /**
      * The 2-1 constructor
      * @param lowerA 		the trainer for the first lower SOM
      * @param lowerB		the trainer for the second lower SOM
      */
    public SOMLinker(SOMTrainer lowerA, SOMTrainer lowerB){
        super();
        sourceSOM = new SOMTrainer[2];
        sourceSOM[0] = lowerA;
        sourceSOM[1] = lowerB;
    }
    
    /**
     * Sets the higher SOM
     * @param higher    the higher SOM
     */
    public void setHigherSOM(SOMTrainer higher){
        higherSOM = higher;        
    }

    /**
      * Switches on or off the tracking of the links between the top and bottom maps.
      * It is off by default.
      * 
      * @param status		true to switch tracking on, false to switch if off
      */
    //public void linkTracking(boolean status){
    //	linkTracking = status;
    //}

    /**
      * Overrides the getAdjustedInput method of the SOMInput class
      *
      * @param outputLength		the size of each small section 
      * 				 	that we want to cut to.
      * @return The vector broken in the parts that are of correct size
      */
    @Override 
    public Vector getAdjustedInput(int outputLength){

        Vector in;

        trainSources();

        //if we have more than one source, join them
        in = (sourceSOM.length > 1) ? joinSources() : sourceSOM[0].getOutput();
        setInput(in);
        return super.getAdjustedInput(outputLength);
    }
    
    /**
     * Returns all the input nodes that correspond to each input set in
     * a 2 dimensional array of nodes
     * @param inputVector   The vector containing all the inputs (the coordinates)
     * @return A 2 dimensional array of nodes
     */
    @SuppressWarnings("unchecked")
    @Override
    public SOMNode[][] getNodes(Vector inputVector){
        
        SOMNode[][] nodes = new SOMNode[inputVector.size()]
                            [((SOMVector<Float>)inputVector.elementAt(0)).size()/2];
        for(int i = 0; i < inputVector.size(); i++){
            SOMVector<Float> tempVector = (SOMVector<Float>)inputVector.elementAt(i);
            int sourceNumber = 0;
            int counter = 0;
            for(int j = 0; j < tempVector.size(); j+=2){
                int x = (int)(tempVector.elementAt(j)*sourceSOM[sourceNumber].getMap().getHeight());
                int y = (int)(tempVector.elementAt(j+1)*sourceSOM[sourceNumber].getMap().getWidth());
                nodes[i][counter] = sourceSOM[sourceNumber].getMap().getNode(x,y);
                counter++;
                if(sourceSOM.length == 2) sourceNumber = (sourceNumber == 0) ? 1 : 0;
            }            
        }        
        return nodes;
    }


    /** 
      * Joins 2 or more source inputs using interleaving.
      * All sources must have the same vector lengths.
      *
      * @return 		one vector that is a result of interleaving
      *         		all the source vectors
      */
    @SuppressWarnings("unchecked")
    private Vector joinSources(){ 
        Vector joinedVectors = new Vector();
        //loops through all the source outputs
        for(int i=0; i<sourceSOM[0].getOutput().size(); i++){
            SOMVector<Float> tempVector = new SOMVector<Float>();	
            for(int j=0; j<((SOMVector<Float>)sourceSOM[0].getOutput().
                elementAt(i)).size(); j+=2){
                //alternate between the sources
                for(int k=0; k< sourceSOM.length; k++){
                        tempVector.addElement(((SOMVector<Float>)sourceSOM[k].
                        getOutput().elementAt(i)).elementAt(j));
                        tempVector.addElement(((SOMVector<Float>)sourceSOM[k].
                        getOutput().elementAt(i)).elementAt(j+1));
                }

            }	
            joinedVectors.addElement(tempVector);
        }
        return joinedVectors;

    }


    /**
      * Trains each one of the lower SOMs exactly once.
      */
    private void trainSources(){
        for(int i=0; i<sourceSOM.length; i++){
                sourceSOM[i].start(1);
        }
    }
		
}