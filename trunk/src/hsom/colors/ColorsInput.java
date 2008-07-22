
/** ColorsInput provides the colors for the Colors demo
  *
  * @author Edwin Law Hui Hean
  */
  
 package HSOM.Colors;
 
 import HSOM.Util.SOMInput;
 import HSOM.Core.SOMVector;
 import java.util.Vector;
 
 
 public class ColorsInput extends SOMInput{
	 
	 /** The sole constructor
	    */
	 public ColorsInput(){
		 super();
		 setInput(getInputs());
	 }
	 
	 /** Produces the input colors, set your colors here
	    * @return 	colors		The Vector containing all the colors.
	    */
	 @SuppressWarnings("unchecked")
	 public Vector getInputs(){
		 
		 Vector colors = new Vector();
		 SOMVector<Float> temp;
		 
		 //black
		 temp = new SOMVector<Float>();
		 temp.addElement(new Float(0.0));
		 temp.addElement(new Float(0.0));
		 temp.addElement(new Float(0.0));
		 colors.addElement(temp);
		 //red
		 temp = new SOMVector<Float>();
		 temp.addElement(new Float(1.0));
		 temp.addElement(new Float(0.0));
		 temp.addElement(new Float(0.0));
		 colors.addElement(temp);
		 //green
		 temp = new SOMVector<Float>();
		 temp.addElement(new Float(0.0));
		 temp.addElement(new Float(1.0));
		 temp.addElement(new Float(0.0));
		 colors.addElement(temp);
		 //blue
		 temp = new SOMVector<Float>();
		 temp.addElement(new Float(0.0));
		 temp.addElement(new Float(0.0));
		 temp.addElement(new Float(1.0));
		 colors.addElement(temp);
		 //red green
		 temp = new SOMVector<Float>();
		 temp.addElement(new Float(1.0));
		 temp.addElement(new Float(1.0));
		 temp.addElement(new Float(0.0));
		 colors.addElement(temp);
		 //red blue
		 temp = new SOMVector<Float>();
		 temp.addElement(new Float(1.0));
		 temp.addElement(new Float(0.0));
		 temp.addElement(new Float(1.0));
		 colors.addElement(temp);
		 //green blue
		 temp = new SOMVector<Float>();
		 temp.addElement(new Float(0.0));
		 temp.addElement(new Float(1.0));
		 temp.addElement(new Float(1.0));
		 colors.addElement(temp);
		 //white
		 temp = new SOMVector<Float>();
		 temp.addElement(new Float(1.0));
		 temp.addElement(new Float(1.0));
		 temp.addElement(new Float(1.0));
		 colors.addElement(temp);	 
		 
		 return colors;
	 }
	 
	 
	 
 }