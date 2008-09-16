
/** NumbersViewer is the tool to view the SOM.
   *
   * @author Edwin Law Hui Hean
   */
   
package hsom.music;

import javax.swing.*;
import hsom.core.*;
import java.io.*;

public class MusicViewer extends JFrame{
	
    private MusicRenderer topRenderer, midRenderer, bottomRenderer;
    private SOMMap topMap, midMap, bottomMap;
    private JFrame topSOM, midSOM, bottomSOM;

    /** The constructor for ColorsViewer
       * @param inFile	The name of the input file
       */
    public MusicViewer(String inFile){

        readMap(inFile);
        topRenderer = new MusicRenderer(topMap);
        midRenderer = new MusicRenderer(midMap);
        bottomRenderer = new MusicRenderer(bottomMap);
        

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
            midMap = (SOMMap)ois.readObject();
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
        
        midSOM = new JFrame("The Mid SOM");
        midSOM.setResizable(false);
        midSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        midSOM.add(midRenderer.getPanel());
        midSOM.pack();		
        midSOM.setSize(new java.awt.Dimension(600, 600));			
        midSOM.setVisible(true);
        
        bottomSOM = new JFrame("The Bottom SOM");
        bottomSOM.setResizable(false);
        bottomSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bottomSOM.add(bottomRenderer.getPanel());
        bottomSOM.pack();		
        bottomSOM.setSize(new java.awt.Dimension(800, 800));			
        bottomSOM.setVisible(true);

    }


    /** The main method
       * @param	args		the arguments to the main method
       */
    public static void main(String args[]) {

        final MusicViewer viewer = new MusicViewer(args[0]);
        /*the event dispatcher thread for thread safety*/	
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run(){
                        viewer.paint();
                }
        });		
    }
	
	
}