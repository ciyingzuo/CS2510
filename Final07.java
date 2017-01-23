import tester.*;

public class Final07 {

}

class obj {
    int a;
    int b;
    int c;
    String d;
    String e;

    obj(int a, int b, int c, String d, String e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }
    
    public int hashCode(){
        return a * a + b * c + d.length() * e.length();
    }
    
    public boolean equals(Object o){
        return this.hashCode() == o.hashCode();
    }
}

class example{
    obj a = new obj(1532, 46389, 22, "ABS", "SYWH");
    obj b = new obj(1532, 46389, 22, "ABS", "SYWH");
    obj c = new obj(1532, 46389, 22, "ABSD", "SYWH");
    
    boolean test1(Tester t){
        return t.checkExpect(a.hashCode(), b.hashCode()) &&
                t.checkExpect(b.hashCode(), c.hashCode());
    }
}