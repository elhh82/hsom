/**
  * Checks if the converted file is valid.
 *  It just prints out a bunch of output values for manual inspection.
  * @author Edwin Law Hui Hean
  */

package kern;

import java.io.*;

public class ConversionChecker {
    
    private BufferedReader inputStream = null;
    private BufferedWriter goodOutputStream = null;
    private BufferedWriter badOutputStream = null;
    private String inFile;
    private String cleanOutput;
    private String badOutput;
    
    /**
     * The single argument constructor
     * @param in the name of the file with the converted output
     */
    public ConversionChecker(String in){
        inFile = in;
    }
    
    public void setOutputFiles(String out1, String out2){
        cleanOutput = out1;
        badOutput = out2;
    }
    
    /**
     * Checks and print if the file length is divisible by 4 and 16
     */
    public void check(Boolean cleanup){
        try{
            inputStream = new BufferedReader(new FileReader(inFile));
            if(cleanup){
                goodOutputStream = new BufferedWriter(new FileWriter(cleanOutput, true));
                badOutputStream = new BufferedWriter(new FileWriter(badOutput, true));
            }
        
            String s;
            while((s = inputStream.readLine()) != null ){
                String[] sr = s.split(" ");
                System.out.print(sr[0] + ": ");
                int length = sr[1].split(",").length;
                System.out.print(length + "      ");
                String divBy4 = (length % 4 == 0) ? "Yes" : "No";
                String divBy16 = (length % 16 == 0) ? "Yes" : "No";
                System.out.println('\t' + divBy4 + '\t'+'\t' + divBy16);
                boolean d4 = (length % 4 == 0) ? true : false;
                boolean d16 = (length % 16 == 0) ? true : false;
                if(cleanup){
                    if(d4 && d16){
                        goodOutputStream.write(s);
                        goodOutputStream.write('\n');
                    }
                    else{
                        badOutputStream.write(s);
                        badOutputStream.write('\n');
                    }                    
                }
            }
            if(cleanup){
                goodOutputStream.flush();
                goodOutputStream.close();
                badOutputStream.flush();
                badOutputStream.close();
            }
        }        
        catch(Exception e){
            System.out.println(e);
        } 
        
    }
    
    public void breakInto16(String file){
        try{
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            BufferedReader input = new BufferedReader(new FileReader(cleanOutput));
            String s;
            while((s = input.readLine()) != null ){
                 String[] sr = s.split(" ");
                 String[] notes = sr[1].split(",");
                 System.out.println(notes.length);
                 for(int i=0; i<notes.length; i++){
                     output.write(sr[0] + "-" + (int)(i/16) + " ");
                     output.write(notes[i]);                     
                     for(int j=1; j<16; j++){
                         output.write(",");
                         output.write(notes[i+j]);    
                     }
                     output.write('\n');
                     i+=15;
                     System.out.println(i);
                 }
            }
            output.flush();
            output.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        
    }
    
    
    /**
     * The main method
     * @param args the arguments
     */
    public static void main(String args[]){
        ConversionChecker cc = new ConversionChecker(args[0]);
        if(args.length >= 3){
            cc.setOutputFiles(args[1], args[2]);
            
        }
        cc.check(args.length >= 3);
        if(args.length >= 4){
            cc.breakInto16(args[3]);
        }
        
    }
    
}
