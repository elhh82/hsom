
/** NumbersViewer is the tool to view the SOM.
   *
   * @author Edwin Law Hui Hean
   */
   
package hsom.music;

import javax.swing.*;
import java.io.*;
import hsom.core.*;

public class MusicViewer3 extends JFrame{
	
    private MusicUMatrixRenderer pitchRenderer, durationRenderer, joinRenderer, topRenderer;
    private SOMMap pitchMap, durationMap, joinMap, topMap;
    private SOMMusicMap pitchMusicMap, durationMusicMap, joinMusicMap, topMusicMap;
    private JFrame pitchSOM, durationSOM, joinSOM, topSOM;
    private int option;

    /** The constructor for ColorsViewer
       * @param inFile	The name of the input file
       */
    public MusicViewer3(String inFile ,int option){
        this.option = option;
        switch(option){
            case 1://option for viewing bottom
                readMapBottom(inFile);
                pitchMusicMap = new SOMMusicMap(pitchMap);
                pitchMusicMap.calcUMatrix();
                pitchRenderer = new MusicUMatrixRenderer(pitchMusicMap, (float)0.95);
                durationMusicMap = new SOMMusicMap(durationMap);
                durationMusicMap.calcUMatrix();
                durationRenderer = new MusicUMatrixRenderer(durationMusicMap, (float)0.95);
                break;
            case 2://options for viewing join
                readMapJoin(inFile);
                pitchMusicMap = new SOMMusicMap(pitchMap);
                pitchMusicMap.calcUMatrix();
                pitchRenderer = new MusicUMatrixRenderer(pitchMusicMap, (float)0.95);
                durationMusicMap = new SOMMusicMap(durationMap);
                durationMusicMap.calcUMatrix();
                durationRenderer = new MusicUMatrixRenderer(durationMusicMap, (float)0.95);
                joinMusicMap = new SOMMusicMap(joinMap);
                joinMusicMap.calcUMatrix();
                joinRenderer = new MusicUMatrixRenderer(joinMusicMap, (float)0.95);
                break;
            case 3://option for viewing top
                readMapTop(inFile);
                pitchMusicMap = new SOMMusicMap(pitchMap);
                pitchMusicMap.calcUMatrix();
                pitchRenderer = new MusicUMatrixRenderer(pitchMusicMap, (float)0.95);
                durationMusicMap = new SOMMusicMap(durationMap);
                durationMusicMap.calcUMatrix();
                durationRenderer = new MusicUMatrixRenderer(durationMusicMap, (float)0.95);
                joinMusicMap = new SOMMusicMap(joinMap);
                joinMusicMap.calcUMatrix();
                joinRenderer = new MusicUMatrixRenderer(joinMusicMap, (float)0.95);
                topMusicMap = new SOMMusicMap(topMap);
                topMusicMap.calcUMatrix();
                topRenderer = new MusicUMatrixRenderer(topMusicMap, (float)0.95);
                break;
            default:
                System.out.println("Please choose the correct viewing option 1-3");
        }
        


    }

    /** The method that reads the SOMMap from the file
       * @param	inFile	The name of the input file
       */
    private void readMapTop(String inFile){
        try{
            FileInputStream fis = new FileInputStream(inFile);
            BufferedInputStream bis =  new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            topMap = (SOMMap)ois.readObject();
            joinMap = (SOMMap)ois.readObject();
            pitchMap = (SOMMap)ois.readObject();
            durationMap = (SOMMap)ois.readObject();            
            ois.close();			
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /** The method that reads the SOMMap from the file
       * @param	inFile	The name of the input file
       */
    private void readMapBottom(String inFile){
        try{
            FileInputStream fis = new FileInputStream(inFile);
            BufferedInputStream bis =  new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            pitchMap = (SOMMap)ois.readObject();
            durationMap = (SOMMap)ois.readObject();
            ois.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /** The method that reads the SOMMap from the file
       * @param	inFile	The name of the input file
       */
    private void readMapJoin(String inFile){
        try{
            FileInputStream fis = new FileInputStream(inFile);
            BufferedInputStream bis =  new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            joinMap = (SOMMap)ois.readObject();
            pitchMap = (SOMMap)ois.readObject();
            durationMap = (SOMMap)ois.readObject();
            ois.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /** The paint method that draws everything
       */
    private void paint(){
        switch(option){            
            case 3:
                topSOM = new JFrame("The top SOM");
                topSOM.setResizable(true);
                topSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                topSOM.add(topRenderer.getPanel());
                topSOM.pack();
                topSOM.setSize(new java.awt.Dimension(800, 800));
                topSOM.setVisible(true);
            case 2:
                joinSOM = new JFrame("The join SOM");
                joinSOM.setResizable(true);
                joinSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                joinSOM.add(joinRenderer.getPanel());
                joinSOM.pack();
                joinSOM.setSize(new java.awt.Dimension(800, 800));
                joinSOM.setVisible(true);
            case 1:
                pitchSOM = new JFrame("The pitch SOM");
                pitchSOM.setResizable(true);
                pitchSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                pitchSOM.add(pitchRenderer.getPanel());
                pitchSOM.pack();
                pitchSOM.setSize(new java.awt.Dimension(800, 800));
                pitchSOM.setVisible(true);
                
                durationSOM = new JFrame("The duration SOM");
                durationSOM.setResizable(true);
                durationSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                durationSOM.add(durationRenderer.getPanel());
                durationSOM.pack();
                durationSOM.setSize(new java.awt.Dimension(800, 800));
                durationSOM.setVisible(true);
                
                break;
        }
    }


    /** The main method
       * @param	args		the arguments to the main method
       */
    public static void main(String args[]) {
        //use args[1] to choose the correct option between 1-3
        final MusicViewer3 viewer = new MusicViewer3(args[0], Integer.parseInt(args[1]));
        /*the event dispatcher thread for thread safety*/	
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run(){
                        viewer.paint();
                }
        });
    }
	
	
}