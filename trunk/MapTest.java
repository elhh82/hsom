import java.util.*;

@SuppressWarnings("unchecked")
public class MapTest{
	
    public static void main(String[] args){

        Map <String, Integer> m = new HashMap<String, Integer>();
        
        // Initialize frequency table from command line
        for (String a : args) {
                Integer freq = m.get(a);
                m.put(a, (freq == null) ? 1 : freq + 1);
        }
        
        ArrayList myArrayList=new ArrayList(m.entrySet());
        Collections.sort(myArrayList, new MyComparator());
        
        System.out.println(myArrayList.size() + " distinct words:");
        System.out.println(myArrayList);		

    }
	
}


class MyComparator implements Comparator{

    public int compare(Object obj1, Object obj2){

        int result=0; 
        Map.Entry e1 = (Map.Entry)obj1 ;
        Map.Entry e2 = (Map.Entry)obj2 ;
        
        //Sort based on values.
        Integer value1 = (Integer)e1.getValue();
        Integer value2 = (Integer)e2.getValue();

        if(value1.compareTo(value2)==0){

            String word1=(String)e1.getKey();
            String word2=(String)e2.getKey();

            //Sort String in an alphabetical order
            result=word1.compareToIgnoreCase(word2);

        } 
        else{
            //Sort values in a descending order
            result=value2.compareTo( value1 );
        }

        return result;
    }
}
    


