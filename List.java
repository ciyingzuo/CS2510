import tester.Tester;


interface IFunc<T, U> {
    U apply(T a1);
}
interface IFunc2<A1, A2, R> {
    R apply(A1 a1, A2 a2);
}

class AddPrice implements IFunc<Book, Double> {
    // add the price of the book in the list by 5.0
    public Double apply(Book b) {
        return b.price + 5.0;
    }
}

class LessThan21 implements IFunc<Book, Boolean> {
    // return true if the price of the book is less than 21
    public Boolean apply(Book b) {
        return b.price < 21;
    }
}
interface IList<T> {
    IList<T> append(IList<T> that);
    IList<T> copyList();
    <R> IList<R> map(IFunc<T, R> func);
    IList<T> filter(IFunc<T, Boolean> func);
    <R> R accept(IListVisitor<T, R> func);
}
class Empty<T> implements IList<T> {
    Empty() {
        // Its a empty list of T
    }
    public IList<T> append(IList<T> that) {
        return that.copyList();
    }
    public IList<T> copyList() {
        return new Empty<T>();
    }
    public <R> IList<R> map(IFunc<T, R> func) {
        return new Empty<R>();
    }
    public IList<T> filter(IFunc<T, Boolean> func) {
        return new Empty<T>();
    }
    public <R> R accept(IListVisitor<T, R> func) {
        return func.visit(this);
    }
}



class Cons<T> implements IList<T> {
    // represent a list of T
    T first;
    IList<T> rest;

    Cons(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }
    public IList<T> append(IList<T> that) {
        // combine two lists together
        return new Cons<T>(this.first, this.rest.append(that));
    }
    public IList<T> copyList() {
        // return a new list with the same elements
        return new Cons<T>(this.first, this.rest.copyList());
    }
    public <R> IList<R> map(IFunc<T, R> func) {
        // map the function to every object in the list
        return new Cons<R>(func.apply(this.first), this.rest.map(func));
    }
    public IList<T> filter(IFunc<T, Boolean> func) {
        // return the list that contains the object that pass by the filter
        if (func.apply(this.first)) {
            return new Cons<T>(this.first, this.rest.filter(func));
        }
        else {
            return this.rest.filter(func);
        }
    }
    public <R> R accept(IListVisitor<T, R> func) {
        return func.visit(this);
    }
}

interface IListVisitor<T, R> {
    R visit(Cons<T> c);
    R visit(Empty<T> e);
}
class MapVisitor<T, R> implements IListVisitor<T, IList<R>> {
    // map the function to the list
    IFunc<T, R> func;
    MapVisitor(IFunc<T, R> func) {
        this.func = func;
    }
    public IList<R> visit(Cons<T> c) {
        return new Cons<R>(func.apply(c.first), c.rest.accept(this));
    }

    public IList<R> visit(Empty<T> e) {
        return new Empty<R>();
    }
}
class FilterVisitor<T> implements IListVisitor<T, IList<T>> {
    //return a new list that contains the object that pass the filter
    IFunc<T, Boolean> func;
    FilterVisitor(IFunc<T, Boolean> func) {
        this.func = func;
    }
    public IList<T> visit(Cons<T> c) {
        if (func.apply(c.first)) {
            return new Cons<T>(c.first, c.rest.accept(this));
        }
        else {
            return c.rest.accept(this);
        }
    }

    public IList<T> visit(Empty<T> e) {
        return new Empty<T>();
    }
}

class BookTitle implements IFunc<Book, String> {
    // return the title of the book;
    public String apply(Book b) {
        return b.title;
    }
}

class Book {
    String title;
    String author;
    int price;
    
    Book(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
}

class CircleToDouble implements IFunc<Circle, Double> {
    // return the area of circle
    public Double apply(Circle c) {
        return (double)3 * c.radius * c.radius;
    }
}

// represent circle
class Circle {
    int radius;
    
    Circle(int r) {
        this.radius = r;
    }
}

class ExampleAndTests {
    
    Book b1 = new Book("A", "B", 15);
    Book b2 = new Book("C", "D", 20);
    Book b3 = new Book("E", "F", 25);
    Circle c11 = new Circle(1);
    Circle c21 = new Circle(2);
    Circle c31 = new Circle(3);
    
    IList<Book> l1 = new Cons<Book>(b3, new Empty<Book>());
    IList<Book> l2 = new Cons<Book>(b1, new Cons<Book>(b2, new Empty<Book>()));
    IList<String> l5 = new Cons<String>("A", new Cons<String>("C", new Empty<String>()));
    IList<Book> l3 = new Cons<Book>(b1, new Cons<Book>(b2, new Cons<Book>(b3, new Empty<Book>())));
    IList<Double> l4 = new Cons<Double>(20.0, new Cons<Double>(25.0, new Empty<Double>()));
    IList<Circle> c1 = new Cons<Circle>(c31, new Empty<Circle>());
    IList<Circle> c2 = new Cons<Circle>(c11, new Cons<Circle>(c21, new Empty<Circle>()));
    IList<Circle> c3 = new Cons<Circle>(c11, new Cons<Circle>(c21,
            new Cons<Circle>(c31, new Empty<Circle>())));
    IList<Double> c4 = new Cons<Double>(3.0, new Cons<Double>(12.0, new Empty<Double>()));
    
    boolean testAppend(Tester t) {
        return t.checkExpect(l2.append(l1), l3);
    }
    
    boolean testMap(Tester t) {
        return t.checkExpect(l2.map(new AddPrice()), l4) &&
                t.checkExpect(c2.map(new CircleToDouble()), c4);
    }
    
    boolean testFilter(Tester t) {
        return t.checkExpect(l3.filter(new LessThan21()), l2);
    }
    
    boolean testMapVisitor(Tester t) {
        return t.checkExpect(l2.accept(new MapVisitor<Book, String>(new BookTitle())), l5);
    }
    
    boolean testFilterVisitor(Tester t) {
        return t.checkExpect(l3.accept(new FilterVisitor<Book>(new LessThan21())), l2);
    }
}