
/** ColorsApp is a demo file to show how the SOM works.
   *
   * @author Edwin Law Hui Hean
   */
  
package hsom.colors;

import hsom.core.*;
import hsom.util.*;
import java.util.Vector;
import java.io.*;

public class ColorsApp{
	
    private SOMMap map;
    private SOMTrainer trainer;
    private SOMInput input;

    /** The sole constructor
       */

    public ColorsApp(){
        map = new SOMMap(40,40,3);

        trainer = new SOMTrainer(map, new ColorsInput());
    }


    /** The functions that starts the training
       * @param	iterations	the number of times to train
       */
    public void start(int iterations){

        trainer.start(iterations);

    }

    /** Prints out the map
       */
    public void printMap(){		

        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                System.out.print("Node " + i + "," + j + " : ");
                System.out.println(map.getNode(i,j).getVector().toString());
            }
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

            oos.writeObject(map);
            oos.close();

        }catch(Exception e){};
    }

    /** The main method.
       * @param	args		The arguments for the main method
       */
    public static void main(String args[]){

        final ColorsApp app = new ColorsApp();
        app.writeMap(args[0]);
        app.start(1000);
        app.writeMap(args[1]);
    }	
}