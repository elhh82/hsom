
/** ColorsViewer is the tool to view the SOM.
   *
   * @author Edwin Law Hui Hean
   */
   
package hsom.numbers;

import javax.swing.*;
import hsom.core.*;
import java.io.*;

public class NumbersViewer extends JFrame{
	
    private NumbersRenderer topRenderer, bottomRenderer;
    private SOMMap topMap, bottomMap;
    private JFrame topSOM, bottomSOM;

    /** The constructor for ColorsViewer
       * @param inFile	The name of the input file
       */
    public NumbersViewer(String inFile){

        readMap(inFile);
        topRenderer = new NumbersRenderer(topMap,false);
        bottomRenderer = new NumbersRenderer(bottomMap,true);

    }

    /** The method that reads the SOMMap from the file
       * @param	inFile	The name of the input file
       */
    private void readMap(String inFile){
        try{
            FileInputStream fis = new FileInputStream(inFile);
            BufferedInputStream bis =  new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            bottomMap = (SOMMap)ois.readObject();
            topMap = (SOMMap)ois.readObject();
            ois.close();			
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /** The paint method that draws everything
       */
    private void paint(){
        
        topSOM = new JFrame("The Top SOM");
        topSOM.setResizable(false);
        topSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        topSOM.add(topRenderer.getPanel());
        topSOM.pack();		
        topSOM.setSize(new java.awt.Dimension(400, 400));			
        topSOM.setVisible(true);
        
        bottomSOM = new JFrame("The Bottom SOM");
        bottomSOM.setResizable(false);
        bottomSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bottomSOM.add(bottomRenderer.getPanel());
        bottomSOM.pack();		
        bottomSOM.setSize(new java.awt.Dimension(400, 400));			
        bottomSOM.setVisible(true);

    }
    
    /** Prints the connections in the top SOM
     * 
     */
    private void printSOM(Boolean up){
        int iLimit = (up) ? topMap.getWidth():bottomMap.getWidth();
        int jLimit = (up) ? topMap.getHeight():bottomMap.getHeight();
        for(int i = 0; i < iLimit; i++){
            for(int j=0; j< jLimit; j++){
                SOMNode[] Links = (up) ? topMap.getNode(i,j).getLinks(false) :
                                    bottomMap.getNode(i,j).getLinks(true);
                System.out.print("Node "+i+","+j+": ");
                for(SOMNode node: Links){
                    System.out.print(" "+node.toString());
                }
                System.out.println(".");
            }
        }
        
    }


    /** The main method
       * @param	args		the arguments to the main method
       */
    public static void main(String args[]) {

        final NumbersViewer viewer = new NumbersViewer(args[0]);
        /*the event dispatcher thread for thread safety*/	
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run(){
                        viewer.paint();
                        viewer.printSOM(true);
                        viewer.printSOM(false);
                }
        });		
    }
	
	
}