
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
  
package HSOM.Util;

import java.util.Vector;
import java.io.Serializable;
import HSOM.Core.SOMVector;

public class SOMLinker extends SOMInput implements Serializable{
	
	private SOMTrainer[] sourceSOM;
	private SOMTrainer higherSOM;
	//private HashMap linkWeights;
	//private boolean linkTracking = false;
	
	
	/**
	  * The 1-1 constructor
	  * @param lowerA 		the trainer for the first lower SOM
	  * @param higher		the trainer for the higher SOM
	  */	
	public SOMLinker(SOMTrainer lowerA, SOMTrainer higher){
		super();	
		sourceSOM[0] = lowerA;
		higherSOM = higher;
		
	}
	
	/**
	  * The 2-1 constructor
	  * @param lowerA 		the trainer for the first lower SOM
	  * @param lowerB		the trainer for the second lower SOM
	  * @param higher		the trainer for the higher SOM
	  */
	public SOMLinker(SOMTrainer lowerA, SOMTrainer lowerB, SOMTrainer higher){
		super();
		sourceSOM[0] = lowerA;
		sourceSOM[1] = lowerB;
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
	public Vector getAdjustedInput(int outputLength){
		
		Vector in;
		
		trainSources();
		
		//if we have more than one source, join them
		in = (sourceSOM.length > 1) ? joinSources() : sourceSOM[0].getOutput();
		setInput(in);
		
		return super.getAdjustedInput(outputLength);
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
		
		for(int i=0; i<sourceSOM[0].getOutput().size(); i++){
			SOMVector<Float> tempVector = new SOMVector<Float>();	
			for(int j=0; j<((SOMVector<Float>)sourceSOM[0].getOutput().
			      elementAt(i)).size(); j+=2){
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
	
	/**
	  * Gets the output from the trained lower SOMs and creates 
	  * or updates the hashMaps containing the connections to
	  * the higher SOMs.
	  */
	//private void updateLinks(){
		
	//}
		
}