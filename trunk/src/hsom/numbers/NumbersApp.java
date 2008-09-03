
/** NumberssApp is a demo file to show how the HSOM works.
   *
   * @author Edwin Law Hui Hean
   */
  
package hsom.numbers;

import hsom.core.*;
import hsom.util.*;
import java.io.*;

public class NumbersApp{
	
    private SOMMap bottomMap, topMap;
    private SOMTrainer bottomTrainer, topTrainer;
    private NumbersInput input;
    private SOMLinker linker;
    /** The sole constructor
       */

    public NumbersApp(){
        bottomMap = new SOMMap(40,40,3);
        topMap = new SOMMap(20,20,4);
        input = new NumbersInput();        
        bottomTrainer = new SOMTrainer(bottomMap, input);
        linker = new SOMLinker(bottomTrainer);
        topTrainer = new SOMTrainer(topMap, linker);
        linker.setHigherSOM(topTrainer);        
    }


    /** The functions that starts the training
       * @param	iterations	the number of times to train
       */
    public void start(int iterations){

        topTrainer.start(iterations);

    }

    /** Prints out the map
       */
    public void printMap(){		

        System.out.println("==========The Bottom Map===========");
        for(int i=0; i<40; i++){
            for(int j=0; j<40; j++){
                System.out.print("Node " + i + "," + j + " : ");
                System.out.println(bottomMap.getNode(i,j).getVector().toString());
            }
        }
        System.out.println("==========The Top Map=========");
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                System.out.print("Node " + i + "," + j + " : ");
                System.out.println(topMap.getNode(i,j).getVector().toString());
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

            oos.writeObject(bottomMap);
            oos.writeObject(topMap);
            oos.close();

        }catch(Exception e){};
    }

    /** The main method.
       * @param	args		The arguments for the main method
       */
    public static void main(String args[]){

        final NumbersApp app = new NumbersApp();
        app.writeMap(args[0]);
        app.start(1000);
        app.writeMap(args[1]);
        
    }	
}