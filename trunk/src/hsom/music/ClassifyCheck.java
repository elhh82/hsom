/**
  * Manages the training of the music SOM
  * @author Edwin Law Hui Hean
  */

package hsom.music;

import hsom.core.*;
import hsom.util.*;
import java.io.*;
import javax.swing.*;

public class ClassifyCheck {

    private SOMMap pitchMap, durationMap, joinMap, topMap;
    private SOMTrainer pitchTrainer, durationTrainer, joinTrainer, topTrainer;
    private SOMOutput pitchOutput, durationOutput, joinOutput, topOutput;
    private SOMLinker joinLinker, topLinker;
    private SOMMusicInput inputPitch, inputDuration;
    private MusicRenderer pitchRenderer, durationRenderer, joinRenderer, topRenderer;
    private JFrame pitchSOM, durationSOM, joinSOM, topSOM;

    /**
     * The sole constructor
     * @param in The name of the input file
     */
    public ClassifyCheck(String pitch, String duration, String maps){
        readMaps(maps);
        //pitchMap = new SOMMap(40,40,8);
        //durationMap = new SOMMap(20,20,8);
        inputPitch = new SOMMusicInput(pitch);
        inputDuration = new SOMMusicInput(duration);
        //pitchTrainer = new SOMTrainer(pitchMap, inputPitch);
        //durationTrainer = new SOMTrainer(durationMap, inputDuration);
        pitchOutput = new SOMOutput(pitchMap, inputPitch);
        durationOutput = new SOMOutput(durationMap, inputDuration);
        //joinMap = new SOMMap(40,40,8);
        joinLinker = new SOMLinker(pitchOutput, durationOutput);
        //joinTrainer = new SOMTrainer(joinMap, joinLinker);
        joinOutput = new SOMOutput(joinMap, joinLinker);
        joinLinker.setHigherSOM(joinOutput);
        //topMap = new SOMMap(4,4,8);
        topLinker = new SOMLinker(joinOutput);
        //topTrainer = new SOMTrainer(topMap, topLinker);
        topOutput = new SOMOutput(topMap, topLinker);
        topLinker.setHigherSOM(topTrainer);

        pitchRenderer = new MusicRenderer(pitchMap);
        durationRenderer = new MusicRenderer(durationMap);
        joinRenderer = new MusicRenderer(joinMap);
        topRenderer = new MusicRenderer(topMap);

    }

    /**
     * Read the input maps
     * @param pitch     The pitch map file
     * @param duration  The duration map file
     */
    private void readMaps(String maps){
        try{
            FileInputStream fis = new FileInputStream(maps);
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


    /** The functions that starts the training
       * @param iterations      the number of times to train
       */
    public void start(){
        //pitchTrainer.start(iterations);
        //durationTrainer.start(iterations);
        //joinTrainer.start(iterations);
        //topTrainer.start(iterations);
        topOutput.start();
        /*
        for(int i=0; i<durationMap.getWidth(); i++){
            for(int j=0; j<durationMap.getHeight(); j++){
                System.out.println(i + "," + j + " - ");
                String[] links = durationMap.getNode(i,j).getLinks(true);
                for(int k=0; k<links.length; k++){
                    System.out.print("<" + links[k] + "> ");
                }
                System.out.println("");
            }
        }
        */
    }

   

    /**
     * Prints out the output values
     */
    @SuppressWarnings("unchecked")
    public void printOutputs(){
        SOMVector<Float> curOutput;
        java.util.Vector top = topOutput.getOutput();
        java.util.Vector join = joinOutput.getOutput();
        java.util.Vector pitch = pitchOutput.getOutput();
        java.util.Vector duration = durationOutput.getOutput();
        //System.out.println(output.size());
        for(int i=0; i<top.size(); i++){
            java.awt.Color c;
            String s;
            if(i<10){
                c = java.awt.Color.RED;
                s = "0";
            }
            else if(i < 20){
                c = java.awt.Color.GREEN;
                s = "@";
            }
            else{
                c = java.awt.Color.BLUE;
                s = "~";
            }
            String temp = "";
            System.out.println("Output " + (i+1) + ":");
            curOutput = (SOMVector<Float>)pitch.elementAt(i);
            System.out.print("Pitch : [");
            for(int j=0; j<curOutput.size(); j+=2){
                float x = (float)curOutput.elementAt(j) * pitchMap.getWidth();
                float y = (float) curOutput.elementAt(j+1) * pitchMap.getHeight();
                temp = temp + x + "," + y + ",";
                pitchRenderer.setNodeText((int)x,(int)y, s);
               /* if(pitchRenderer.getNodeColored((int)x,(int)y) == java.awt.Color.WHITE ||
                   pitchRenderer.getNodeColored((int)x,(int)y) == c){
                    pitchRenderer.setNodeColor((int)x,(int)y,c);                    
                }
                else
                    pitchRenderer.setNodeColor((int)x, (int)y, java.awt.Color.BLACK);*/
            }
            System.out.println(temp.substring(0, temp.length()-1) + "]");
            temp = "";
            curOutput = (SOMVector<Float>)duration.elementAt(i);
            System.out.print("Duration : [");
            for(int j=0; j<curOutput.size(); j+=2){
                float x = (float)curOutput.elementAt(j) * durationMap.getWidth();
                float y = (float) curOutput.elementAt(j+1) * durationMap.getHeight();
                temp = temp + x + "," + y + ",";
                durationRenderer.setNodeText((int)x,(int)y, s);
                /*if(durationRenderer.getNodeColored((int)x,(int)y) == java.awt.Color.WHITE ||
                   durationRenderer.getNodeColored((int)x,(int)y) == c)
                    durationRenderer.setNodeColor((int)x,(int)y,c);
                else
                    durationRenderer.setNodeColor((int)x, (int)y, java.awt.Color.BLACK); */           }
            System.out.println(temp.substring(0, temp.length()-1) + "]");
            temp = "";
            curOutput = (SOMVector<Float>)join.elementAt(i);
            System.out.print("Join : [");
            for(int j=0; j<curOutput.size(); j+=2){
                float x = (float)curOutput.elementAt(j) * joinMap.getWidth();
                float y = (float) curOutput.elementAt(j+1) * joinMap.getHeight();
                temp = temp + x + "," + y + ",";
                joinRenderer.setNodeText((int)x,(int)y, s);
                /*if(joinRenderer.getNodeColored((int)x,(int)y) == java.awt.Color.WHITE ||
                   joinRenderer.getNodeColored((int)x,(int)y) == c)
                    joinRenderer.setNodeColor((int)x,(int)y,c);
                else
                    joinRenderer.setNodeColor((int)x, (int)y, java.awt.Color.BLACK);*/
            }
            System.out.println(temp.substring(0, temp.length()-1) + "]");
            curOutput = (SOMVector<Float>)top.elementAt(i);
            float x = (float)curOutput.elementAt(0) * topMap.getWidth();
            float y = (float)curOutput.elementAt(1) * topMap.getHeight();
            System.out.println("Top : [" + x + "," + y + "]");
            System.out.println("");
            topRenderer.setNodeText((int)x,(int)y, s);
            /*if(topRenderer.getNodeColored((int)x,(int)y) == java.awt.Color.WHITE ||
                   topRenderer.getNodeColored((int)x,(int)y) == c)
                    topRenderer.setNodeColor((int)x,(int)y,c);
                else
                    topRenderer.setNodeColor((int)x, (int)y, java.awt.Color.BLACK);*/
        }
    }

        /** The paint method that draws everything
       */
    private void paint(){
        pitchSOM = new JFrame("The pitch SOM");
        pitchSOM.setResizable(true);
        pitchSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pitchSOM.add(pitchRenderer.getPanel());
        pitchSOM.pack();
        pitchSOM.setSize(new java.awt.Dimension(400, 400));
        pitchSOM.setVisible(true);

        durationSOM = new JFrame("The duration SOM");
        durationSOM.setResizable(true);
        durationSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        durationSOM.add(durationRenderer.getPanel());
        durationSOM.pack();
        durationSOM.setSize(new java.awt.Dimension(400, 400));
        durationSOM.setVisible(true);

        joinSOM = new JFrame("The join SOM");
        joinSOM.setResizable(true);
        joinSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        joinSOM.add(joinRenderer.getPanel());
        joinSOM.pack();
        joinSOM.setSize(new java.awt.Dimension(400, 400));
        joinSOM.setVisible(true);

        topSOM = new JFrame("The top SOM");
        topSOM.setResizable(true);
        topSOM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        topSOM.add(topRenderer.getPanel());
        topSOM.pack();
        topSOM.setSize(new java.awt.Dimension(400, 400));
        topSOM.setVisible(true);

    }

    /**
     * The main function
     * @param args The arguments, two file names.
     */
    public static void main(String args[]){
        final ClassifyCheck app = new ClassifyCheck(args[0], args[1], args[2]);
        //app.printInputs();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run(){
                    app.start();
                    app.printOutputs();
                    app.paint();
                }
        });
        //if(args[3] == null) args[3] = "defaultoutput.txt";
        //app.writeMap(args[3]);
    }


}

