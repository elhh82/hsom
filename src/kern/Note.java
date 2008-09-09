/**
  * Describes a note, contains an integer and a string
  * @author Edwin Law Hui Hean
  */

package kern;


public class Note {
    
    private float duration;
    private String pitch;
    
    /**
     * Constructor to create a note with a duration and pitch
     * @param d An integer for the duration
     * @param p An string for the pitch
     */
    public Note(float d, String p){
        duration = d;
        pitch = p;        
    }
    
    /**
     * Returns the duration of the note
     * @return  the duration of the note
     */
    public float getDuration(){
        return duration;        
    }
    
    /**
     * Returns the pitch of the note
     * @return  the pitch of the note
     */
    public String getPitch(){
        return pitch;
    }
    
    /**
     * Returns the pitch of the note in midi (60 is middle C etc.)
     * @return  the pitch of the note in midi form
     */
    private int getPitchMidi(){
        int midiPitch = 0;
        String tempPitch = pitch;
        while(tempPitch.charAt(tempPitch.length()-1) == '#' ||
              tempPitch.charAt(tempPitch.length()-1) == '-' ||
              tempPitch.charAt(tempPitch.length()-1) == 'n')
        {
            tempPitch = tempPitch.substring(0, tempPitch.length()-1);
        }
        int octaveShift = Character.isUpperCase(pitch.charAt(0)) ? 0 - tempPitch.length() :
                          tempPitch.length() - 1;
        switch(Character.toLowerCase(tempPitch.charAt(0))){
            case 'c' : midiPitch = 60; break;
            case 'd' : midiPitch = 62; break;
            case 'e' : midiPitch = 64; break;
            case 'f' : midiPitch = 65; break;
            case 'g' : midiPitch = 67; break;
            case 'a' : midiPitch = 69; break;
            case 'b' : midiPitch = 71; break;
            case 'r' : midiPitch = 0; break; 
            default : System.out.println("Error in pitch conversion"); break;
        }
        midiPitch += (12 * octaveShift);
        if(pitch.charAt(pitch.length()-1) == '#'){
            midiPitch += (pitch.length() - tempPitch.length());
        }
        else if(pitch.charAt(pitch.length()-1) == '-'){
            midiPitch -= (pitch.length() - tempPitch.length());
        } 
        
        return midiPitch;
    }
    
    /**
     * Returns the length of this note, in terms of the unit specified
     * ie: if unit is 16 (1/16), and the note is of duration 4 (1/4),
     * the note is of length 4 (16 / 4).
     * @param unit the unit of conversion 
     * @return  the length of the note in terms of the unit of conversion
     */
    public int getLength(int unit){
        return (int)(unit/duration);        
    }
    
    /**
     * Returns the string representation of the note
     * @return the string representation of the note
     */
    @Override
    public String toString(){
        return "<" + duration + "," + pitch + ">";
    }
    
    /**
     * Converts the note into its HSOM Pitch representation
     * @param key the key of the score where this note resides
     * @param minDuration the duration of the shortest note in the score
     * @return a string which contains the HSOM representation of this note
     */
    public String toHSOMPitchNotation(int key, int minDuration){
        String convertedNote = "";
        int length = getLength(minDuration);
        convertedNote = "" + ((getPitchMidi() == 0) ? 0 : getPitchMidi() - key);
        for(int i=1; i<length; i++){
            convertedNote = convertedNote + "," + ((getPitchMidi() == 0) ? 0 : getPitchMidi() - key);
        }
        
        return convertedNote;
    }
    
}