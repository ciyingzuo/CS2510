import tester.Tester;

// IList classes
interface IList<T> {
    IList<T> append(IList<T> that);
    IList<T> copyList();
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
}
class Cons<T> implements IList<T> {
    T first;
    IList<T> rest;

    Cons(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }
    public IList<T> append(IList<T> that) {
        return new Cons<T>(this.first, this.rest.append(that));
    }
    public IList<T> copyList() {
        return new Cons<T>(this.first, this.rest.copyList());
    }
}

// IAT classes
interface IAT {
    <R> R accept(IATVisitor<R> vt);
}
class Unknown implements IAT {
    Unknown() { /* empty constructor */ }
    
    public <R> R accept(IATVisitor<R> vt) {
        return vt.visit(this);
    }
   
}
class Person implements IAT {
    String name;
    int yob;
    boolean isMale;
    IAT mom;
    IAT dad;
    Person(String name, int yob, boolean isMale, IAT mom, IAT dad) {
        this.name = name;
        this.yob = yob;
        this.isMale = isMale;
        this.mom = mom;
        this.dad = dad;
    }
    public <R> R accept(IATVisitor<R> vt) {
        return vt.visit(this);
    }
}

// IATVisitor classes
interface IATVisitor<R> {
    R visit(Unknown u);
    R visit(Person p);
}
// EveryPersonVisitor creates a list of all the names in the IAT
class EveryPersonVisitor implements IATVisitor<IList<String>> {
    // Empty case, end string
    public IList<String> visit(Unknown u) {
        return new Empty<String>();
    }
    // Append the names from the mother's side to the father's side
    public IList<String> visit(Person p) {
        return new Cons<String>(p.name, p.mom.accept(this).append(p.dad.accept(this)));
    }
}
// examples
class ExamplesIATVisitor {
    IATVisitor<IList<String>> epv = new EveryPersonVisitor();
    IAT u = new Unknown();
    IAT p1 = new Person("Bob", 1876, true, u, u);
    IAT p2 = new Person("Delilah", 1902, false, u, p1);
    IAT p3 = new Person("Lucius", 1899, true, u, u);
    IAT p4 = new Person("Arnold", 1942, true, p2, p3);
    IAT p5 = new Person("Meredith", 1899, false, u, u);
    IAT p6 = new Person("Albericht", 1902, true, u, u);
    IAT p7 = new Person("Franz", 1930, false, p5, p6);
    IAT p8 = new Person("Joe", 1920, true, u, p3);
    boolean testEveryPersonVisitor(Tester t) {
        return t.checkExpect(u.accept(epv), new Empty<String>()) &&
                t.checkExpect(p1.accept(epv), new Cons<String>("Bob"
                        , new Empty<String>())) &&
                t.checkExpect(p7.accept(epv), new Cons<String>("Franz"
                        , new Cons<String>("Meredith"
                                , new Cons<String>("Albericht"
                                        , new Empty<String>())))) &&
                t.checkExpect(p4.accept(epv), new Cons<String>("Arnold"
                        , new Cons<String>("Delilah"
                                , new Cons<String>("Bob"
                                        , new Cons<String>("Lucius"
                                                , new Empty<String>())))));
    }
}