import tester.*;
public class Final11 {

}

class Node<T>{
    T data;
    Node<T> prev;
    Node<T> next;
    
    Node(T data, Node<T> prev, Node<T> next){
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}

class CircularList<T>{
    Node<T> ht;
    
    CircularList(){
        this.ht = new Node(null, null, null);
        this.ht.prev = this.ht;
        this.ht.next = this.ht;
    }
    
    public boolean isEmpty(){
        return this.ht.next == this.ht && this.ht.prev == this.ht;
    }
    
    public void addAtFront(T t){
        Node<T> n = new Node<T>(t, null, null);
        this.ht.next.prev = n;
        n.next = this.ht.next;
        this.ht.next = n;
        n.prev = this.ht;
    }
    
    public void removeFromTail(){
        this.ht.prev.prev.next = this.ht;
        this.ht.prev = this.ht.prev.prev;
    }
}

class example{
    CircularList<Integer> d = new CircularList<Integer>();
    Node<Integer> a = new Node<Integer>(1, null, null);
    Node<Integer> b = new Node<Integer>(2, null, null);
    Node<Integer> c = new Node<Integer>(2, null, null);
    
    void ini(){
        d.addAtFront(1);
        d.addAtFront(2);
        d.addAtFront(3);
    }
    
    boolean test(Tester t){
        ini();
        return t.checkExpect(d.ht.next.data, 3);
    }
    boolean test1(Tester t){
        ini();
        d.removeFromTail();
        d.removeFromTail();
        return t.checkExpect(d.ht.next.data, 3);
    }
}