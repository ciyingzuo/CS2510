import java.util.HashSet;

// IList classes with full suite of utility methods
interface IList<T> {
    boolean isSorted(IComparator<T> fun);
    boolean isSortedHelp(IComparator<T> fun, T first);
    IList<T> merge(IList<T> l, IComparator<T> fun);
    IList<T> mergeWithCons(Cons<T> l, IComparator<T> fun);
    IList<T> mergeWithMt(Empty<T> l, IComparator<T> fun);
    IList<T> sort(IComparator<T> fun);
    boolean sameList(IList<T> l);
    boolean sameEmpty(Empty<T> l);
    boolean sameCons(Cons<T> l);
    IList<T> append(IList<T> that);
    IList<T> copyList();
    <R> IList<R> map(IFunc<T, R> func);
    IList<T> filter(IFunc<T, Boolean> func);
    boolean contains(T t);
    <R> R accept(IListVisitor<T, R> func);
}
class Empty<T> implements IList<T> {
    Empty() {
        // Its a empty list of T
    }
    public boolean isSorted(IComparator<T> fun) {
        return true;
    }
    public boolean isSortedHelp(IComparator<T> fun, T s1) {
        return true;
    }
    public IList<T> sort(IComparator<T> fun) {
        return this;
    }
    public IList<T> merge(IList<T> l, IComparator<T> fun) {
        return l.mergeWithMt(this, fun);
    }

    public boolean sameList(IList<T> l) {
        return l.sameEmpty(this);
    }

    public boolean sameEmpty(Empty<T> l) {
        return true;
    }
    public boolean sameCons(Cons<T> l) {
        return false;
    }
    public IList<T> mergeWithCons(Cons<T> l, IComparator<T> fun) {
        return l;
    }
    public IList<T> mergeWithMt(Empty<T> l, IComparator<T> fun) {
        return this;
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
    public boolean contains(T t) {
        return false;
    }
}
class Cons<T> implements IList<T> {
    T first;
    IList<T> rest;

    Cons(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }
    public boolean isSorted(IComparator<T> fun) {
        return this.rest.isSortedHelp(fun, this.first);
    }
    public boolean isSortedHelp(IComparator<T> fun, T s1) {
        return fun.compare(s1, this.first) <= 0 &&
                this.rest.isSortedHelp(fun, this.first);
    }
    public IList<T> sort(IComparator<T> fun) {
        return new Cons<T>(this.first, new Empty<T>()).merge(this.rest.sort(fun), fun);
    }

    //merge using double dispatch
    public IList<T> merge(IList<T> l, IComparator<T> fun) {
        return l.mergeWithCons(this, fun);
    }
    public IList<T> mergeWithCons(Cons<T> l, IComparator<T> fun) {
        if (fun.compare(l.first, this.first) < 0) {
            return new Cons<T>(l.first, this.merge(l.rest, fun));
        }
        else {
            return new Cons<T>(this.first, l.merge(this.rest, fun));
        }
    }
    public IList<T> mergeWithMt(Empty<T> l, IComparator<T> fun) {
        return this;
    }

    //sameness using double dispatch
    public boolean sameList(IList<T> l) {
        return l.sameCons(this);
    }
    public boolean sameEmpty(Empty<T> l) {
        return false;
    }
    public boolean sameCons(Cons<T> l) {
        return this.first.equals(l.first) &&
                this.rest.sameList(l.rest);
    }
    public IList<T> append(IList<T> that) {
        return new Cons<T>(this.first, this.rest.append(that));
    }
    public IList<T> copyList() {
        return new Cons<T>(this.first, this.rest.copyList());
    }
    public <R> IList<R> map(IFunc<T, R> func) {
        return new Cons<R>(func.apply(this.first), this.rest.map(func));
    }
    public IList<T> filter(IFunc<T, Boolean> func) {
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
    public boolean contains(T t) {
        return t.equals(this.first) || this.rest.contains(t);
    }
}
// IComparator and IFunc interfaces
interface IComparator<T> {
    // Returns a negative number if t1 comes before t2 in this ordering
    // Returns zero              if t1 is the same as t2 in this ordering
    // Returns a positive number if t1 comes after t2 in this ordering
    int compare(T t1, T t2);
}
interface IFunc<A1, R> {
    R apply(A1 a1);
}
interface IFunc2<A1, A2, R> {
    R apply(A1 a1, A2 a2);
}
// IListVisitor interface
interface IListVisitor<T, R> {
    R visit(Cons<T> c);
    R visit(Empty<T> e);
}
// Course and instructor classes
class Course {
    String name;
    Instructor instructor;
    IList<Student> students;
    Course(String name, Instructor instructor) {
        this.name = name;
        this.instructor = instructor;
        this.students = new Empty<Student>();
    }
}

class Instructor {
    String name;
    IList<Course> courses;
    Instructor(String name) {
        this.name = name;
        this.courses = new Empty<Course>();
    }
    boolean dejavu(Student s) {
        InstructorStudentVisitor is = new InstructorStudentVisitor(s);
        return this.courses.accept(is);
    }
}

class Student {
    String name;
    int id;
    IList<Course> courses;
    Student(String name, int id) {
        this.name = name;
        this.id = id;
        this.courses = new Empty<Course>();
    }
    void enroll(Course c) {
        this.courses = new Cons<Course>(c, this.courses);
        c.students = new Cons<Student>(this, c.students);
    }
    boolean classmates(Student s) {
        DuplicatesVisitor<Course> dv = new DuplicatesVisitor<Course>(s.courses);
        return courses.accept(dv);
    }
}
// Visitors
class ListLengthVisitor<T> implements IListVisitor<T, Integer> {
    int curlength;
    ListLengthVisitor() {
        super();
        curlength = 0;
    }
    public Integer visit(Cons<T> c) {
        curlength++;
        return c.rest.accept(this);
    }
    public Integer visit(Empty<T> c) {
        return curlength;
    }
}
class DuplicatesVisitor<T> implements IListVisitor<T, Boolean> {
    HashSet<T> set;
    Boolean acc;
    DuplicatesVisitor(IList<T> that) {
        super();
        this.set = new HashSet<T>();
        that.accept(this);
        this.acc = true;
    }
    public Boolean visit(Cons<T> c) {
        acc = acc & set.add(c.first);
        return c.accept(this);
    }
    public Boolean visit(Empty<T> e) {
        return acc;
    }
    public Boolean visit(T t) {
        return acc & set.add(t);
    }
}
class InstructorStudentVisitor implements IListVisitor<Course, Boolean> {
    Student s;
    int acc;
    InstructorStudentVisitor(Student s) {
        this.s = s;
        acc = 0;
    }
    public Boolean visit(Cons<Course> c) {
        if (c.first.students.contains(s)) {
            acc++;
            if (acc > 1) {
                return true;
            }
        }
        return c.rest.accept(this);
    }
    public Boolean visit(Empty<Course> c) {
        return false;
    }
}
