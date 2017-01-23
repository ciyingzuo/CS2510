import java.util.ArrayList;
import java.util.Arrays;

import tester.*;

public class Final10 {

}

class Bead{
    int size;
    String color;
    
    Bead(int s, String c){
        this.size = s;
        this.color = c;
    }
    
}

class Algorithms{
     public boolean hasSequence(ArrayList<Bead> b){
         String s = "RGB";
         for(int i = 0; i < b.size() - 1; i++){
             for(int n = 0; n < s.length(); n++){
                 if(b.get(n+i).color.compareTo(s.substring(n, n+1)) != 0){
                     break;
                 }
                 if(n == 2 && b.get(n+i).color.compareTo(s.substring(n, n+1)) == 0){
                     return true;
                 }
             }
         }
         return false;
     }
}

class example{
    Bead b5r = new Bead(5, "R");
    Bead b3g = new Bead(3, "G");
    Bead b2b = new Bead(2, "B");
    ArrayList<Bead> a = new ArrayList<Bead>(Arrays.asList(b5r, b3g, b2b));
    
    void ini(){
        a.add(b5r);
        a.add(b3g);
        a.add(b2b);
    }
    
    boolean test1(Tester t){
//        ini();
        return t.checkExpect(new Algorithms().hasSequence(a), true);
    }
}