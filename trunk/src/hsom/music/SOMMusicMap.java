/**
 * will be added into the SOMMap later
 * @author elhh
 */

package hsom.music;

import hsom.core.*;
import java.util.Vector;

public class SOMMusicMap {
    
    SOMMap map;
    
    public SOMMusicMap(SOMMap m){
        map = m;
    }
    
    public void getPrediction(Vector predictorOutputs, int range){
         for(int i=0; i<predictorOutputs.size(); i++){
            //first we find the nodes
            int numNodes = ((SOMVector<Float>)predictorOutputs.get(i)).size()/2;
            System.out.println((SOMVector<Float>)predictorOutputs.get(i));
            for(int j=0; j<numNodes; j++){
                int x = new Float(((SOMVector<Float>)predictorOutputs.get(i)).get(j*2)*map.getWidth()).intValue();
                int y = new Float(((SOMVector<Float>)predictorOutputs.get(i)).get(j*2+1)*map.getHeight()).intValue();
                String[] links = map.getNode(x,y).getLinks(false);
                //find the best node from the link
                SOMNode[] bestNodes = getBestLink(links);
                
                //prints out the value of the node
                bestNodes[0].print(range);
                bestNodes[1].print(range);
            }
            
        }
    }
    
    public SOMNode[] getBestLink(String[] links){
        
        return null;
    }

}
