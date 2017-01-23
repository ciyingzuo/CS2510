
public class Final12 {

}

interface Iterator<T>{
    boolean hasNext(); 
    T next(); 
    void remove(); 

}

interface Iterable<T>{
    Iterator<T> iterator();
}

class Pair<X, Y>{
    X left;
    Y right;
    
    Pair(X left, Y right){
        this.left = left;
        this.right = right;
    }
}

class Zip<X, Y> implements Iterable<Pair<X, Y>>{

    public Iterator<Pair<X, Y>> iterator() {
        return new Iterator<Pair<X, Y>>(this);
    }
    
}

class ZipIterator implements Iterator<Pair<X, Y>>{
    
}








