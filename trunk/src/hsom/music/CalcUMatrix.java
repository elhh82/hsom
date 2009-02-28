/** CalcUMatrix calculates the UMatrix from a trained map and saves it
  *
  * @author Edwin Law Hui Hean
  */

package hsom.music;

import hsom.core.*;
import java.io.*;

/**
 *
 * @author elhh
 */
public class CalcUMatrix {

     private SOMMusicMap durationMap;

     public CalcUMatrix(String inMap){
         readMap(inMap);
         durationMap.calcUMatrix();
     }

     /** The method that reads the SOMMap from the file
       * @param	inFile	The name of the input file
       */
     private void readMap(String inFile){
        try{
            FileInputStream fis = new FileInputStream(inFile);
            BufferedInputStream bis =  new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            durationMap = new SOMMusicMap((SOMMap)ois.readObject());
            ois.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

     /** Writes the map into a file
       * @param	filename	the name of the output file
       */
    @SuppressWarnings("empty-statement")
    public void writeMap(String filename){
        try{
            FileOutputStream fos = new FileOutputStream(filename);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(durationMap);
            oos.close();

        }catch(Exception e){};
    }

     /** The main method
       * @param	args		the arguments to the main method
       */
    public static void main(String args[]) {

        final CalcUMatrix calc = new CalcUMatrix(args[0]);
        calc.writeMap(args[1]);
    }
}
