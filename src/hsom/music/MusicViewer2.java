
/** NumbersViewer is the tool to view the SOM.
   *
   * @author Edwin Law Hui Hean
   */
   
package hsom.music;

import javax.swing.*;
import java.io.*;

public class MusicViewer2 extends JFrame{
	
    private MusicUMatrixRenderer topRenderer, midRenderer, joinRenderer, pitch2Renderer, pitch4Renderer, durationRenderer;
    private SOMMusicMap topMap, midMap, joinMap, pitch2Map, pitch4Map, durationMap;
    private JFrame topSOM, midSOM, joinSOM, pitch2SOM, pitch4SOM, durationSOM;

    /** The constructor for ColorsViewer
       * @param inFile	The name of the input file
       */
    public MusicViewer2(String inFile){

        readMap(inFile);
        topRenderer = new MusicUMatrixRenderer(topMap,(float)0.95);
        midRenderer = new MusicUMatrixRenderer(midMap,(float)0.95);
        joinRenderer = new MusicUMatrixRenderer(joinMap, (float)0.95);
        pitch2Renderer = new MusicUMatrixRenderer(pitch2Map, (float)0.95);
        pitch4Renderer = new MusicUMatrixRenderer(pitch4Map, (float)0.95);
        durationRenderer = new MusicUMatrixRenderer(durationMap, (float)0.9);

    }

    /** The method that reads the SOMMap from the file
       * @param	inFile	The name of the input file
       */
    private void readMap(String inFile){
        try{
            FileInputStream fis = new FileInputStream(inFile);
            BufferedInputStream bis =  new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            pitch2Map = (SOMMusicMap)ois.readObject();
            pitch4Map = (SOMMusicMap)ois.readObject();
            durationMap = (SOMMusicMap)ois.readObject();
            joinMap = (SOMMusicMap)ois.readObject();
            midMap = (SOMMusicMap)ois.readObject();
            topMap = (SOMMusicMap)ois.readObject();
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

        joinSOM = new JFrame("The join SOM");
        joinSOM.setResizable(false);
        joinSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        joinSOM.add(joinRenderer.getPanel());
        joinSOM.pack();
        joinSOM.setSize(new java.awt.Dimension(600, 600));
        joinSOM.setVisible(true);

        pitch4SOM = new JFrame("The pitch4 SOM");
        pitch4SOM.setResizable(false);
        pitch4SOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pitch4SOM.add(pitch4Renderer.getPanel());
        pitch4SOM.pack();
        pitch4SOM.setSize(new java.awt.Dimension(800, 800));
        pitch4SOM.setVisible(true);
        
        pitch2SOM = new JFrame("The pitch2 SOM");
        pitch2SOM.setResizable(false);
        pitch2SOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pitch2SOM.add(pitch2Renderer.getPanel());
        pitch2SOM.pack();
        pitch2SOM.setSize(new java.awt.Dimension(800, 800));
        pitch2SOM.setVisible(true);
        
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

        final MusicViewer2 viewer = new MusicViewer2(args[0]);
        /*the event dispatcher thread for thread safety*/	
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run(){
                        viewer.paint();
                }
        });		
    }
	
	
}