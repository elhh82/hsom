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

     private SOMMusicMap topMap, midMap, joinMap, pitch2Map, pitch4Map, durationMap;

     public CalcUMatrix(String inMap){
         readMap(inMap);
         pitch2Map.calcUMatrix();
         pitch4Map.calcUMatrix();
         durationMap.calcUMatrix();
         joinMap.calcUMatrix();
         midMap.calcUMatrix();
         topMap.calcUMatrix();
     }

     /** The method that reads the SOMMap from the file
       * @param	inFile	The name of the input file
       */
     private void readMap(String inFile){
        try{
            FileInputStream fis = new FileInputStream(inFile);
            BufferedInputStream bis =  new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            pitch2Map = new SOMMusicMap((SOMMap)ois.readObject());
            pitch4Map = new SOMMusicMap((SOMMap)ois.readObject());
            durationMap = new SOMMusicMap((SOMMap)ois.readObject());
            joinMap = new SOMMusicMap((SOMMap)ois.readObject());
            midMap = new SOMMusicMap((SOMMap)ois.readObject());
            topMap = new SOMMusicMap((SOMMap)ois.readObject());
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

            oos.writeObject(pitch2Map);
            oos.writeObject(pitch4Map);
            oos.writeObject(durationMap);
            oos.writeObject(joinMap);
            oos.writeObject(midMap);
            oos.writeObject(topMap);
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
