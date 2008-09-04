/**
  * Describes a note, contains an integer and a string
  * @author Edwin Law Hui Hean
  */

package kern;


public class Note {
    
    private int duration;
    private String pitch;
    
    /**
     * Constructor to create a note with a duration and pitch
     * @param d An integer for the duration
     * @param p An string for the pitch
     */
    public Note(int d, String p){
        duration = d;
        pitch = p;        
    }
    
    /**
     * Returns the duration of the note
     * @return  the duration of the note
     */
    public int getDuration(){
        return duration;        
    }
    
    /**
     * Returns the pitch of the note
     * @return  the pitch of the note
     */
    public String getPitch(){
        return pitch;
    }
}
