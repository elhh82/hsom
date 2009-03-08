/**
  * A benchmark that tests one run of the som, you have to get your own test file.
 *  It runs off code from hsom.music.
  * @author Edwin Law Hui Hean
  */

package hsom.benchmark;

import hsom.music.*;


public class Bench {    
    
    public static void main(String args[]){
        final MusicApp3 bench = new MusicApp3(args[0], args[1], args[2]);
        long before = System.currentTimeMillis() ;
        bench.start(1000);
        long after = System.currentTimeMillis() ;
        long elapsed = after - before ;
        System.out.println("Start Time: " + before);
        System.out.println("End Time: " + after);
        System.out.println("Time Taken: " + elapsed);
        
    }
    
}
