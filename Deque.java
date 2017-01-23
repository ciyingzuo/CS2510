import tester.Tester;

// represents a dequeue
class Deque<T> {
    Sentinel<T> header;

    // constructor
    Deque() {
        this.header = new Sentinel<T>();
    }

    // convenience constructor
    Deque(Sentinel<T> t) {
        this.header = t;
    }

    // counts the number of nodes in a list Deque (no including the header)
    int size() {
        return header.next.size();
    }

    // inserts given value at the front of the list
    void addAtHead(T t) {
        header.addAtHead(t);
    }

    // inserts given value at the end of the list
    void addAtTail(T t) {
        header.addAtTail(t);
    }

    // removes the first node from this deque
    T removeFromHead() {
        return header.removeFromHead();
    }

    // removes the last node from this deque
    T removeFromTail() {
        return header.removeFromTail();
    }

    // produces the first node in this deque for which the given predicate
    // returns true
    ANode<T> find(IPred<T> pred) {
        return header.find(pred);
    }

    // removes given node from this deque
    void removeNode(ANode<T> node) {
        if (!node.isNode()) {
            return;
        }
        else
            if (node.isNode()) {
                node.next.setPrev(node.prev);
                node.prev.setNext(node.next);
            }
    }
}

// represents a node
abstract class ANode<T> {
    ANode<T> next;
    ANode<T> prev;

    // error if given a null value
    public void throwIfNull(T t) {
        if (t == null) {
            throw new IllegalArgumentException("given value can't be null");
        }
    }

    // error if removing from an empty
    public void throwIfEmpty() {
        throw new RuntimeException("Cannot remove from an empty!");
    }

    // retrieves the previous node
    public ANode<T> getPrev() {
        return this.prev;
    }

    // retrieves the next node
    public ANode<T> getNext() {
        return this.next;
    }

    // updates the previous node with the given value
    public ANode<T> setPrev(ANode<T> newValue) {
        return this.prev = newValue;
    }

    // updates the next node with the given value
    public ANode<T> setNext(ANode<T> newValue) {
        return this.next = newValue;
    }

    // determines the size - number of nodes
    abstract int size();

    // determines if it is a node
    abstract boolean isNode();

    // determines if the deque is empty
    boolean isEmpty() {
        return this.size() == 0;
    }

    // gets the data value of the node
    abstract T getValue();

    // helper method for find
    abstract ANode<T> findHelp(IPred<T> pred);

}

// represents the dummy
class Sentinel<T> extends ANode<T> {

    // constructor
    public Sentinel() {
        super.next = this;
        super.prev = this;
    }

    // the size of a sentinel is 0
    public int size() {
        return 0;
    }

    // a sentinel is not a node
    public boolean isNode() {
        return false;
    }

    // given value is added behind the sentinel (become's sentinel's next)
    public void addAtHead(T t) {
        throwIfNull(t);

        new Node<T>(t, this.next, this);
    }

    // given value is added in front of the sentinel (become's sentinel's prev)
    public void addAtTail(T t) {
        throwIfNull(t);

        new Node<T>(t, this, this.prev);
    }

    // returns the value of a sentinel
    public T getValue() {
        throw new RuntimeException("Cannot remove from an empty!");
    }

    // removes the first node
    T removeFromHead() {

        T removedData = next.getValue();

        this.next = this.next.getNext();
        this.next.setPrev(this);

        return removedData;
    }

    // removes the last node
    T removeFromTail() {
        T removedData = prev.getValue();

        this.prev = this.prev.getPrev();
        this.prev.setNext(this);

        return removedData;
    }

    // searches if the pred applies to any value to the right of the sentinel
    public ANode<T> find(IPred<T> pred) {
        return next.findHelp(pred);
    }

    // returns sentinel if predicate never returns true for any value
    public ANode<T> findHelp(IPred<T> pred) {
        return this;
    }

}

// represents a node
class Node<T> extends ANode<T> {
    T data;

    // main constructor
    Node(T data) {
        this.data = data;
        super.next = next;
        super.prev = prev;
    }

    // convenience constructor
    Node(T data, ANode<T> next, ANode<T> prev) {
        if (next == null || prev == null) {
            throw new IllegalArgumentException("node is null");
        }
        else {
            // initializes the data field to given value
            this.data = data;

            // initializes next and prev field
            super.next = next;
            super.prev = prev;

            // updates the given nodes to refer back to this node
            prev.next = this;
            next.prev = this;
        }
    }

    // determines the size of the deque
    public int size() {
        if (this.next.isNode()) {
            return 1 + next.size();
        }
        else {
            return 1;
        }
    }

    // a node is a node
    public boolean isNode() {
        return true;
    }

    // returns the data value of the node
    T getValue() {
        return this.data;
    }

    // finds the first node that returns true for the given predicate
    public ANode<T> findHelp(IPred<T> pred) {
        if (pred.apply(this.data)) {
            return this;
        }
        return next.findHelp(pred);
    }
}

// Represents a boolean-valued question over values of type T
interface IPred<T> {
    boolean apply(T t);
}

// determines if the length of the string is greater than 2
class Length2 implements IPred<String> {
    public boolean apply(String s) {
        return s.length() > 2;
    }
}

// examples and tests for deque
class ExamplesDeque {
    // Deque examples
    Deque<String> deque1;
    Deque<String> deque2;
    Deque<String> deque3;
    Deque<String> deque4;
    Deque<String> deque5;

    // ANode examples
    ANode<String> node1;
    ANode<String> node2;
    ANode<String> node3;
    ANode<String> node4;

    ANode<String> nodev;
    ANode<String> nodew;
    ANode<String> nodex;
    ANode<String> nodey;
    ANode<String> nodez;

    ANode<String> nodea;
    ANode<String> nodeb;
    ANode<String> noded;
    ANode<String> sentNode;

    // Sentinel examples
    Sentinel<String> sent;
    Sentinel<String> sent2;
    Sentinel<String> sent3;
    Sentinel<String> sent4;

    // initializes Deque list
    void initDeque() {
        sent = new Sentinel<String>();
        node1 = new Node<String>("abc", sent, sent);
        node2 = new Node<String>("bcd", sent, node1);
        node3 = new Node<String>("cde", sent, node2);
        node4 = new Node<String>("def", sent, node3);

        sent2 = new Sentinel<String>();
        nodex = new Node<String>("x", sent2, sent2);
        nodey = new Node<String>("yoo", sent2, nodex);
        nodew = new Node<String>("w", sent2, nodey);
        nodez = new Node<String>("z", sent2, nodew);
        nodev = new Node<String>("v", sent2, nodez);

        // extra sentinel and node examples
        sent3 = new Sentinel<String>();
        nodea = new Node<String>("a", sent3, sent3);

        sent4 = new Sentinel<String>();
        nodeb = new Node<String>("b", sent4, sent4);
        noded = new Node<String>("d", sent4, nodeb);

        sentNode = new Sentinel<String>();

        // empty
        deque1 = new Deque<String>();
        // ordered lexicographically
        deque2 = new Deque<String>(this.sent);
        // not ordered lexicographically
        deque3 = new Deque<String>(this.sent2);

        // extra deque examples
        deque4 = new Deque<String>();
        deque5 = new Deque<String>(this.sent4);
    }

    // tests for size and addAtHead
    void testSize(Tester t) {
        initDeque();
        this.deque4.addAtHead("a");
        this.deque5.addAtHead("c");

        // tests for the construction of links
        t.checkExpect(this.deque2.header, this.sent);
        t.checkExpect(this.deque3.header, this.sent2);
        t.checkExpect(this.sent.prev, this.node4);
        t.checkExpect(this.node1.next, this.node2);
        t.checkExpect(this.node2.prev, this.node1);
        t.checkExpect(this.node2.next, this.node3);
        t.checkExpect(this.node3.prev, this.node2);
        t.checkExpect(this.node3.next, this.node4);
        t.checkExpect(this.node4.prev, this.node3);
        t.checkExpect(this.node4.next, this.sent);
        t.checkExpect(this.sent.next, this.node1);

        // tests for size
        t.checkExpect(deque1.size(), 0);
        t.checkExpect(deque2.size(), 4);
        t.checkExpect(deque3.size(), 5);

        // tests for addAtHead
        t.checkExpect(this.deque4.header, this.sent3);
        t.checkExpect(this.sent3.prev, this.nodea);
        t.checkExpect(this.nodea.next, this.sent3);
        t.checkExpect(this.deque5.header, this.sent4);
        t.checkExpect(this.sent4.prev, this.noded);
        t.checkExpect(this.sent4.next, new Node<String>("c", nodeb, sent4));
        t.checkExpect(new Node<String>("c", nodeb, sent4).prev, this.sent4);
        t.checkExpect(new Node<String>("c", nodeb, sent4).next, this.nodeb);
        t.checkExpect(this.nodeb.prev, new Node<String>("c", nodeb, sent4));
        t.checkExpect(this.nodeb.next, this.noded);
        t.checkExpect(this.noded.prev, this.nodeb);
        t.checkExpect(this.noded.next, this.sent4);
        // tests for abstract addAtHead
        t.checkExpect(this.node2.prev, this.node1);
        t.checkExpect(new Node<String>("hi", node1, sent).next, node1);
    }

    // tests for addAtTail
    void testAddAtTail(Tester t) {
        initDeque();
        this.deque4.addAtTail("a");
        this.deque5.addAtTail("c");

        // tests for addAtTail
        t.checkExpect(this.deque4.header, this.sent3);
        t.checkExpect(this.sent3.next, this.nodea);
        t.checkExpect(this.nodea.prev, this.sent3);
        t.checkExpect(this.deque5.header, this.sent4);
        t.checkExpect(this.sent4.prev, new Node<String>("c", sent4, noded));
        t.checkExpect(this.sent4.next, this.nodeb);
        t.checkExpect(this.nodeb.prev, sent4);
        t.checkExpect(this.nodeb.next, this.noded);
        t.checkExpect(this.noded.prev, this.nodeb);
        t.checkExpect(this.noded.next, new Node<String>("c", sent4, noded));
        t.checkExpect(new Node<String>("c", sent4, noded).prev, this.noded);
        t.checkExpect(new Node<String>("c", sent4, noded).next, this.sent4);
        // tests for abstract addAtTail
        t.checkExpect(new Node<String>("bye", sent, node2).prev, node2);
    }

    // tests for remove for remove from head or tail
    void testRemoveFromHead(Tester t) {
        initDeque();

        // tests for removeFromHead
        t.checkExpect(this.deque2.removeFromHead(), "abc");
        t.checkExpect(this.deque5.removeFromHead(), "b");

        // tests for removeFromTail
        t.checkExpect(this.deque2.removeFromTail(), "def");
        t.checkExpect(this.deque3.removeFromTail(), "v");
    }

    // tests for find
    void testFind(Tester t) {
        initDeque();
        IPred<String> pred1 = new Length2();

        t.checkExpect(this.deque5.find(pred1), sent4);
        t.checkExpect(this.deque3.find(pred1), nodey);
        t.checkExpect(this.deque2.find(pred1), node1);
    }

    // tests for removeNode
    void testRemoveNode(Tester t) {
        initDeque();
        this.deque2.removeNode(sent);
        this.deque3.removeNode(nodey);

        // tests for case given node is a sentinel
        t.checkExpect(this.deque2.header, this.sent);
        t.checkExpect(this.sent.prev, this.node4);
        t.checkExpect(this.node1.next, this.node2);
        t.checkExpect(this.node2.prev, this.node1);
        t.checkExpect(this.node2.next, this.node3);
        t.checkExpect(this.node3.prev, this.node2);
        t.checkExpect(this.node3.next, this.node4);
        t.checkExpect(this.node4.prev, this.node3);
        t.checkExpect(this.node4.next, this.sent);
        t.checkExpect(this.sent.next, this.node1);

        // tests for given node is not a sentinel
        t.checkExpect(this.deque3.header, this.sent2);
        t.checkExpect(this.sent2.prev, this.nodev);
        t.checkExpect(this.nodex.prev, this.sent2);
        t.checkExpect(this.nodex.next, this.nodew);
        t.checkExpect(this.nodew.prev, this.nodex);
        t.checkExpect(this.nodew.next, this.nodez);
        t.checkExpect(this.nodez.prev, this.nodew);
        t.checkExpect(this.nodez.next, this.nodev);
        t.checkExpect(this.nodev.prev, this.nodez);
        t.checkExpect(this.nodev.next, this.sent2);
        t.checkExpect(this.nodey.prev, this.nodey.prev); // next and prev values
                                                         // don't change
        t.checkExpect(this.nodey.next, this.nodey.next); // but nodey becomes
                                                         // inaccessible
    }

    // tests for helper methods in the abstract class
    void testHelp(Tester t) {
        initDeque();

        // tests for isEmpty
        t.checkExpect(this.sentNode.isEmpty(), true);
        t.checkExpect(this.node3.isEmpty(), false);
        // tests for getNext
        t.checkExpect(this.node2.getNext(), this.node3);
        // tests for getPrev
        t.checkExpect(this.node1.getPrev(), this.sent);
    }

    // examples of null nodes
    ANode<String> nullNode1() {
        return new Node<String>("bye", null, new Node<String>("felicia"));
    }

    ANode<String> nullNode2() {
        return new Node<String>("bye", new Node<String>("felicia"), null);
    }

    // tests the null case
    void testNullNodes(Tester t) {
        initDeque();

        t.checkException(new IllegalArgumentException("node is null"), this,
                "nullNode1");
        t.checkException(new IllegalArgumentException("node is null"), this,
                "nullNode2");
        t.checkException(new RuntimeException("Cannot remove from an empty!"),
                sent2, "throwIfEmpty");
        t.checkException(new RuntimeException("Cannot remove from an empty!"),
                sent2, "getValue");
    }

}