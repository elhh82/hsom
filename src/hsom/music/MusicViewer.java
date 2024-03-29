
/** NumbersViewer is the tool to view the SOM.
   *
   * @author Edwin Law Hui Hean
   */
   
package hsom.music;

import javax.swing.*;
import hsom.core.*;
import java.io.*;

public class MusicViewer extends JFrame{
	
    private MusicRenderer topRenderer, midRenderer, pitchRenderer, durationRenderer;
    private SOMMap topMap, midMap, pitchMap, durationMap;
    private JFrame topSOM, midSOM, pitchSOM, durationSOM;

    /** The constructor for ColorsViewer
       * @param inFile	The name of the input file
       */
    public MusicViewer(String inFile){

        readMap(inFile);
        topRenderer = new MusicRenderer(topMap,4);
        midRenderer = new MusicRenderer(midMap,8);
        pitchRenderer = new MusicRenderer(pitchMap, 8);
        durationRenderer = new MusicRenderer(durationMap, 4);

    }

    /** The method that reads the SOMMap from the file
       * @param	inFile	The name of the input file
       */
    private void readMap(String inFile){
        try{
            FileInputStream fis = new FileInputStream(inFile);
            BufferedInputStream bis =  new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            pitchMap = (SOMMap)ois.readObject();
            durationMap = (SOMMap)ois.readObject();
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
        
        pitchSOM = new JFrame("The pitch SOM");
        pitchSOM.setResizable(false);
        pitchSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pitchSOM.add(pitchRenderer.getPanel());
        pitchSOM.pack();		
        pitchSOM.setSize(new java.awt.Dimension(800, 800));			
        pitchSOM.setVisible(true);
        
        durationSOM = new JFrame("The duration SOM");
        durationSOM.setResizable(false);
        durationSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        durationSOM.add(durationRenderer.getPanel());
        durationSOM.pack();		
        durationSOM.setSize(new java.awt.Dimension(400, 400));			
        durationSOM.setVisible(true);

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