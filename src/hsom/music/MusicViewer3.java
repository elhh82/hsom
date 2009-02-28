
/** NumbersViewer is the tool to view the SOM.
   *
   * @author Edwin Law Hui Hean
   */
   
package hsom.music;

import javax.swing.*;
import java.io.*;
import hsom.core.*;

public class MusicViewer3 extends JFrame{
	
    private MusicUMatrixRenderer durationRenderer;
    private SOMMap durationMap;
    private SOMMusicMap durationMusicMap;
    private JFrame durationSOM;

    /** The constructor for ColorsViewer
       * @param inFile	The name of the input file
       */
    public MusicViewer3(String inFile){

        readMap(inFile);
        durationMusicMap = new SOMMusicMap(durationMap);
        durationMusicMap.calcUMatrix();
        durationRenderer = new MusicUMatrixRenderer(durationMusicMap, (float)0.95);

    }

    /** The method that reads the SOMMap from the file
       * @param	inFile	The name of the input file
       */
    private void readMap(String inFile){
        try{
            FileInputStream fis = new FileInputStream(inFile);
            BufferedInputStream bis =  new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            durationMap = (SOMMap)ois.readObject();
            ois.close();			
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /** The paint method that draws everything
       */
    private void paint(){
                
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

        final MusicViewer3 viewer = new MusicViewer3(args[0]);
        /*the event dispatcher thread for thread safety*/	
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run(){
                        viewer.paint();
                }
        });
    }
	
	
}