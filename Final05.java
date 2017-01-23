import tester.*;

public class Final05 {

}

interface Iterator<T> {
    int toNumber(int i);
}

class Interator implements Iterator<Integer> {;
    int i = 0;

    // There are always more Fibonacci numbers
    boolean hasNext() {
        return true;
    }

    // Compute the next Fibonacci number
    Integer next() {
        int answer = this.i;
        this.i++;
        return answer;
    }

    public void remove() {
        throw new UnsupportedOperationException("Don't do this!");
    }

    public int toNumber(int i) {
        for (int n = 0; n < i; n++) {
            this.next();
        }
        return this.i;
    }
    
    public int evenNumber(int i) {
        int ans = 0;
        for (int n = 0; n < i; n++) {
            this.next();
            ans = this.i;
            this.next();
        }
        return ans;
    }
}


class example{
    boolean test1(Tester t){

        return t.checkExpect(new Interator().evenNumber(3), 2);
    }
}