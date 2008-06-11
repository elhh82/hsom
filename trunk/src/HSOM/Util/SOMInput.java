
/** SOMInput is the basic class for inputs to the SOM.
  *
  * @author Edwin Law Hui Hean
  */
  
package HSOM.Util;

import java.util.Vector;
import HSOM.Core.SOMVector;
  
public class SOMInput{
	
	private Vector input;
	
	/**
	  * The default constructor for the SOMInput class.
	  * The input here is set to null
	  */
	public SOMInput(){ 
		input = null;
	}
	
	/**
	  * Here we set the input vector.
	  *
	  * @param 	in 	The input vector
	  */
	public void setInput(Vector in){		
		input = in;		
	}
	
	/**
	  * Returns a Vector that is to be used as input to the
	  * linked SOM.
	  * @return 		The Input Vector
	  */
	public Vector getInput(){
	
		return input;	
		
	}
	
	/**
	  * Returns the adjusted inputs. The adjustment is done 
	  * so that the inputs are broken into smaller sections
	  * that are of the same size as the SOM's weights
	  * @param outputLength		the size of each small section 
	  * 				 	that we want to cut to.
	  * @return 	the vector broken in the parts that are of correct size
	  */
	@SuppressWarnings("unchecked")
	public Vector getAdjustedInput(int outputLength){
		
		Vector output = new Vector();
		SOMVector tempSOMVector;
		SOMVector curInput;
		
		int inputLength = ((SOMVector)input.elementAt(0)).size();
		
		//check first if the lengths are actually the same
		if(inputLength == outputLength) return input;
		
		if(inputLength % outputLength == 0){
			for (int i=0; i<input.size(); i++) {
				curInput = (SOMVector)input.elementAt(i);
				for(int j=0; j<inputLength; j+=outputLength){
					tempSOMVector = new SOMVector();
					for(int k=0; k<outputLength; k++){
						tempSOMVector.addElement(curInput.elementAt(j+k));
					}
					output.addElement(tempSOMVector);					
				}				
			}	
		}
		else{
			return null;
		}		
		
		return output;
			
	}
	
}
	 