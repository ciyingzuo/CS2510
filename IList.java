import tester.*;
public interface IList<T>{
    <R> R accept(IListVisitor<T, R> v);
}

class Cons<T> implements IList<T>{
    T first;
    IList<T> rest;
    
    Cons(T first, IList<T> rest){
        this.first = first;
        this.rest = rest;
    }

    public <R> R accept(IListVisitor<T, R> v) {
        return v.visit(this);
    }
}

class Empty<T> implements IList<T>{

    public <R> R accept(IListVisitor<T, R> v) {
        return v.visit(this);
    }
    
}

interface IListVisitor<T, R>{
    R visit(Cons<T> l);
    R visit(Empty<T> l);
}

class MapVisitor<T, R> implements IListVisitor<T, IList<R>>{
    IFunc<T, R> func;
    
    MapVisitor(IFunc<T, R> f){
        this.func = f;
    }
    public IList<R> visit(Cons<T> l) {
        return new Cons<R>(this.func.apply(l.first), l.rest.accept(this));
    }

    public IList<R> visit(Empty<T> l) {
        return new Empty<R>();
    }
    
}

//interface IPred<String> {
//
//}

interface IFunc<T, R> {

    public R apply(T first);
}

class doublePrice implements IFunc<Book, Book>{

    public Book apply(Book first) {
        return new Book(first.author, first.price*2);
    }
    
}

//interface Comparator<Double> {
//
//}

class Book {
    String author;
    int year;
    int price;
    
    Book(String author, int price){
        this.author = author;
        this.price = price;
    }
}

class Circle {
    int radius;
    
    Circle(int radius){
        this.radius = radius;
    }
}

class example {
    boolean test1(Tester t){
        IList<Book> a = new Cons<Book>(new Book("A", 10), new Cons<Book>(
                new Book("B", 20), new Empty<Book>()));
        IList<Book> b = new Cons<Book>(new Book("A", 20), new Cons<Book>(
                new Book("B", 40), new Empty<Book>()));
        return t.checkExpect(a.accept(new MapVisitor(new doublePrice())), b);
    }
}