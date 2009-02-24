
/** SOMRenderer builds a graphicsl representation of the SOM.
   * using a JTextArea.
   *
   * @author Edwin Law Hui Hean
   */
   
package hsom.util;

import hsom.core.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;


public class SOMRenderer{
	
    protected int width, height;	
    protected SOMMap map;
    protected JTextArea[][] SOMArea;
    protected double[][] UMatrix;
    private JPanel SOMPanel;

    /** The constructor for the SOMRenderer
       * @param m		the SOMMap associated to this renderer
       */
    public SOMRenderer(SOMMap m){

        width = m.getWidth();
        height = m.getHeight();
        map = m;
        SOMPanel = new JPanel(new SpringLayout());
        buildMap();

    }

    /** Calculates the UMatrix for the map
     * 
     */
    /*public void calcUMatrix(){
        double max = 0;
        UMatrix = new double[width][height];
        for(int x=0; x < width; x++){
            for(int y=0; y< height; y++){
                if(x==0 && y==0){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> rightNode = map.getNode(x+1,y).getVector();
                    SOMVector<Float> bottomNode = map.getNode(x,y+1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(rightNode)+
                                    centerNode.euclideanDistance(bottomNode))/2;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else if(x==width-1 && y==height-1){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> leftNode = map.getNode(x-1,y).getVector();
                    SOMVector<Float> topNode = map.getNode(x,y-1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(leftNode)+
                                    centerNode.euclideanDistance(topNode))/2;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else if(x==0 && y==height-1){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> rightNode = map.getNode(x+1,y).getVector();
                    SOMVector<Float> topNode = map.getNode(x,y-1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(rightNode)+
                                    centerNode.euclideanDistance(topNode))/2;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else if(x==width-1 && y==0){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> leftNode = map.getNode(x-1,y).getVector();
                    SOMVector<Float> bottomNode = map.getNode(x,y+1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(leftNode)+
                                    centerNode.euclideanDistance(bottomNode))/2;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else if(x==0){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> rightNode = map.getNode(x+1,y).getVector();
                    SOMVector<Float> topNode = map.getNode(x,y-1).getVector();
                    SOMVector<Float> bottomNode = map.getNode(x,y+1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(rightNode)+
                                    centerNode.euclideanDistance(topNode)+
                                    centerNode.euclideanDistance(bottomNode))/3;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else if(y==0){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> leftNode = map.getNode(x-1,y).getVector();
                    SOMVector<Float> rightNode = map.getNode(x+1,y).getVector();
                    SOMVector<Float> bottomNode = map.getNode(x,y+1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(leftNode)+
                                    centerNode.euclideanDistance(rightNode)+
                                    centerNode.euclideanDistance(bottomNode))/3;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else if(x==width-1){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> leftNode = map.getNode(x-1,y).getVector();
                    SOMVector<Float> topNode = map.getNode(x,y-1).getVector();
                    SOMVector<Float> bottomNode = map.getNode(x,y+1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(leftNode)+
                                    centerNode.euclideanDistance(topNode)+
                                    centerNode.euclideanDistance(bottomNode))/3;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else if(y==height-1){
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> leftNode = map.getNode(x-1,y).getVector();
                    SOMVector<Float> rightNode = map.getNode(x+1,y).getVector();
                    SOMVector<Float> topNode = map.getNode(x,y-1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(leftNode)+
                                    centerNode.euclideanDistance(rightNode)+
                                    centerNode.euclideanDistance(topNode))/3;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }
                else{
                    SOMVector<Float> centerNode = map.getNode(x,y).getVector();
                    SOMVector<Float> leftNode = map.getNode(x-1,y).getVector();
                    SOMVector<Float> rightNode = map.getNode(x+1,y).getVector();
                    SOMVector<Float> topNode = map.getNode(x,y-1).getVector();
                    SOMVector<Float> bottomNode = map.getNode(x,y+1).getVector();
                    UMatrix[x][y] = (centerNode.euclideanDistance(leftNode)+
                                    centerNode.euclideanDistance(rightNode)+
                                    centerNode.euclideanDistance(topNode)+
                                    centerNode.euclideanDistance(bottomNode))/4;
                    if(UMatrix[x][y] > max) max = UMatrix[x][y];
                }

            }
        }
        for(int x=0; x < width; x++){
            for(int y=0; y< height; y++){
                UMatrix[x][y] = UMatrix[x][y] / max;
            }
        }

    }*/
    /** Builds the JTextArea using the SpringUtilities class
       */
    public void buildMap(){

        Border border = BorderFactory.createLineBorder(Color.black);

        SOMArea = new JTextArea[width][height]; 
        //SOMPanel.setMinimumSize(new java.awt.Dimension(600,600));
        for(int i=0; i< width; i++) {
            for(int j=0; j< height; j++){
                SOMArea[i][j] = new JTextArea(2,2);
                SOMArea[i][j].setEditable(false);
                SOMArea[i][j].setBorder(border);   
                SOMPanel.add(SOMArea[i][j]);  
            }          
        }
        SpringUtilities.makeCompactGrid(SOMPanel, width, height, 0, 0, 0, 0); 

    }

    /** Returns the panel containing the JTextArea map
       * @return 	the JPanel containing the visualized SOM
       */
    public JPanel getPanel(){
        return SOMPanel;
    }

    /** Returns the whole visualized SOM in the form of a
       * 2 dimensional JTextArea array.
       * @return 	a 2 dimensional JTextArea array
       */
    public JTextArea[][] getMap(){
        return SOMArea;
    }

    /** Returns the UMatrix (2-dim double array) for this map
     *
     * @return a 2 dimensional double array which is the UMatrix
     */
    /*public double[][] getUMatrix(){
        return UMatrix;
    }*/

    /** Returns the UMatrix for a specific node
     *
     * @param   x The x coodinate of the node
     * @param   y The y coordinate of the node
     * @return  The Umatrix of the node
     */
    /*public double getUMatrix(int x, int y){
        return UMatrix[x][y];
    }*/

}