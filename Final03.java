import tester.*;

public class Final03 {

}

interface IPred<T> {
    boolean apply(T t);
}

class Equals implements IPred<Integer>{
    int i;
    
    Equals(int i){
        this.i = i;
    }

    @Override
    public boolean apply(Integer n) {
        return this.i == n;
    }
}

class Deque<T> {
    Sentinel<T> header;

    Deque() {
        this.header = new Sentinel<T>();
    }

    Deque(Sentinel<T> s) {
        this.header = s;
    }

    public int size() {
        return this.header.size();
    }

    public void addAtHead(T t) {
        Node<T> n = new Node<T>(t);
        n.next = this.header.next;
        this.header.next.prev = n;
        this.header.next = n;
        n.prev = this.header;
    }

    public T removeFromTail() {
        T t = ((Node<T>) this.header.prev).data;
        this.header.prev.prev.next = this.header;
        this.header.prev = this.header.prev.prev;
        return t;
    }
    
    ANode<T> find(IPred<T> i){
        return this.header.find(i);
    }
}

abstract class ANode<T> {
    ANode<T> next;
    ANode<T> prev;

    public abstract int size();

    public abstract ANode<T> find(IPred<T> i);
}

class Node<T> extends ANode<T> {
    T data;

    Node(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    Node(T data, ANode<T> n1, ANode<T> n2) {
        if (n1 == null || n2 == null) {
            throw new IllegalArgumentException("Invalid input");
        } else {
            this.data = data;
            this.next = n1;
            this.prev = n2;
            n1.prev = this;
            n2.next = this;
        }
    }

    public int size() {
        if (next instanceof Sentinel) {
            return 1;
        } else
            return 1 + next.size();

    }

    @Override
    public ANode<T> find(IPred<T> i) {
        if (i.apply(this.data)) {
            return this;
        } else if(next instanceof Sentinel) {
            return next;
        }
        else return next.find(i);
    }
}

class Sentinel<T> extends ANode<T> {
    Sentinel() {
        this.next = this;
        this.prev = this;
    }

    public ANode<T> find(IPred<T> i) {
        if (next instanceof Sentinel) {
            return this;
        } else
            return next.find(i);
    }

    public int size() {
        if (next instanceof Sentinel) {
            return 0;
        } else
            return next.size();
    }
}

class example {
    Deque<String> deque1 = new Deque<String>();
    Sentinel<String> h = new Sentinel<String>();
    Node<String> n1 = new Node<String>("abc", h, h);
    Node<String> n2 = new Node<String>("bcd", h, n1);
    Node<String> n3 = new Node<String>("cde", h, n2);
    Node<String> n4 = new Node<String>("def", h, n3);
    Deque<String> deque2 = new Deque<String>(h);
    Sentinel<Integer> h2 = new Sentinel<Integer>();
    Node<Integer> n11 = new Node<Integer>(1, h2, h2);
    Node<Integer> n22 = new Node<Integer>(2, h2, n11);
    Node<Integer> n33 = new Node<Integer>(3, h2, n22);
    Node<Integer> n44 = new Node<Integer>(4, h2, n33);
    Deque<Integer> deque3 = new Deque<Integer>(h2);

    boolean test1(Tester t) {
        return t.checkExpect(deque2.size(), 4) && t.checkExpect(deque2.removeFromTail(), "def")
                && t.checkExpect(deque2.size(), 3);
    }
    
    boolean test5(Tester t) {
        return t.checkExpect(deque3.find(new Equals(5)), h2);
    }
}