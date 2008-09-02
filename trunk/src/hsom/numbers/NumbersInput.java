
/** ColorsInput provides the colors for the Colors demo
  *
  * @author Edwin Law Hui Hean
  */
  
 package hsom.numbers;
 
 import hsom.util.SOMInput;
 import hsom.core.SOMVector;
 import java.util.Vector;
 
 
 public class NumbersInput extends SOMInput{
	 
     /** The sole constructor
        */
     public NumbersInput(){
         super();
         setInput(getInputs());
     }

     /** Produces the input colors, set your colors here
        * @return 	colors		The Vector containing all the colors.
        */
     @SuppressWarnings("unchecked")
     public Vector getInputs(){

         Vector numbers = new Vector();
         SOMVector<Float> temp;

         //03
         temp = new SOMVector<Float>();
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(0.0));
         numbers.addElement(temp);
         
         //04
         temp = new SOMVector<Float>();
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(0.0));
         numbers.addElement(temp);
         
         //15
         temp = new SOMVector<Float>();
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(1.0));
         numbers.addElement(temp);
         
         //27
         temp = new SOMVector<Float>();
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(1.0));
         numbers.addElement(temp);
         
         //36
         temp = new SOMVector<Float>();
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(0.0));
         numbers.addElement(temp);
         
         //62
         temp = new SOMVector<Float>();
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(0.0));
         numbers.addElement(temp);
         
         //74
         temp = new SOMVector<Float>();
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(0.0));
         numbers.addElement(temp);
        
         //51
         temp = new SOMVector<Float>();
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(1.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(0.0));
         temp.addElement(new Float(1.0));
         numbers.addElement(temp);

         return numbers;
     } 
	 
	 
 }