
/** ColorsViewer is the tool to view the SOM.
   *
   * @author Edwin Law Hui Hean
   */
   
package hsom.colors;

import javax.swing.*;
import hsom.core.*;
import java.io.*;

public class ColorsViewer extends JFrame{
	
	private ColorsRenderer renderer;
	private SOMMap map;
	private JFrame colorSOM;
	
	/** The constructor for ColorsViewer
	   * @param inFile	The name of the input file
	   */
	public ColorsViewer(String inFile){
		
		readMap(inFile);
		renderer = new ColorsRenderer(map);
		
	}
	
	/** The method that reads the SOMMap from the file
	   * @param	inFile	The name of the input file
	   */
	private void readMap(String inFile){
		try{
			FileInputStream fis = new FileInputStream(inFile);
			BufferedInputStream bis =  new BufferedInputStream(fis);
			ObjectInputStream ois = new ObjectInputStream(bis);
			map = (SOMMap)ois.readObject();
			ois.close();			
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	/** The paint method that draws everything
	   */
	private void paint(){
		
		colorSOM = new JFrame("The Color SOM");
		colorSOM.setResizable(false);
		colorSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		colorSOM.add(renderer.getPanel());
		colorSOM.pack();		
		colorSOM.setSize(new java.awt.Dimension(400, 400));			
		colorSOM.setVisible(true);	
		
	}
	
	
	/** The main method
	   * @param	args		the arguments to the main method
	   */
	public static void main(String args[]) {
		
		final ColorsViewer viewer = new ColorsViewer(args[0]);
		/*the event dispatcher thread for thread safety*/	
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				viewer.paint();
			}
		});		
	}
	
	
}