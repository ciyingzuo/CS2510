import tester.Tester;

interface IComparator<T> {
    // Returns a negative number if t1 comes before t2 in this ordering
    // Returns zero              if t1 is the same as t2 in this ordering
    // Returns a positive number if t1 comes after t2 in this ordering
    int compare(T t1, T t2);
}
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
}

class StringLexCompGen implements IComparator<String> {

    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }

}

class StringLengthCompGen implements IComparator<String> {
    public int compare(String s1, String s2) {
        return s1.length() - s2.length();
    }

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
}




class ExamplesStringsGen {
    IComparator<String> lex = new StringLexCompGen();
    IComparator<String> len = new StringLengthCompGen();

    boolean testLexCompare(Tester t) {
        return t.checkExpect(lex.compare("a", "b") != 0, true) &&
                t.checkExpect(lex.compare("b", "a") != 0, true) &&
                t.checkExpect(lex.compare("a", "a") == 0, true) &&
                t.checkExpect(lex.compare("asdfs", "asdfasdf") != 0, true);
    }
    boolean testLengthCompare(Tester t) {
        return t.checkExpect(len.compare("a", "ab") != 0, true) &&
                t.checkExpect(len.compare("ab", "a") != 0, true) &&
                t.checkExpect(len.compare("a", "a") == 0, true) &&
                t.checkExpect(len.compare("asdfs", "asdfasdf") != 0, true);
    }
    boolean testIsSorted(Tester t) {
        IList<String> l1 = new Empty<String>();
        IList<String> l2 = new Cons<String>("Zeta", l1);
        IList<String> l3 = new Cons<String>("Zeta", l2);
        IList<String> l4 = new Cons<String>("Bazooka", l3);
        IList<String> l5 = new Cons<String>("Cheese", l4);
        return t.checkExpect(l1.isSorted(lex), true) &&
                t.checkExpect(l2.isSorted(lex), true) &&
                t.checkExpect(l3.isSorted(lex), true) &&
                t.checkExpect(l4.isSorted(lex), true) &&
                t.checkExpect(l5.isSorted(lex), false);
    }
    boolean testSort(Tester t) {
        IList<String> l1 = new Empty<String>();
        IList<String> l2 = new Cons<String>("Zeta", l1);
        IList<String> l3 = new Cons<String>("Zeta", l2);
        IList<String> l4 = new Cons<String>("Bazooka", l3);
        IList<String> l5 = new Cons<String>("Cheese", l4);

        IList<String> l6 = new Empty<String>();
        IList<String> l7 = new Cons<String>("Bazooka", l6);
        IList<String> l8 = new Cons<String>("Cheese", l7);
        IList<String> l9 = new Cons<String>("Zeta", l8);
        IList<String> l10 = new Cons<String>("Zeta", l9);
        return t.checkExpect(l5.sort(len), l10) &&
                t.checkExpect(new Empty<String>().sort(len), new Empty<String>());
    }
    boolean testSameEmpty(Tester t) {
        Empty<String> l1 = new Empty<String>();
        Empty<String> l2 = new Empty<String>();
        Cons<String> l3 = new Cons<String>("", l1);
        return t.checkExpect(l1.sameEmpty(l1), true) &&
                t.checkExpect(l2.sameEmpty(l1), true) &&
                t.checkExpect(l3.sameEmpty(l1), false);
    }
    boolean testSameCons(Tester t) {
        IList<String> l1 = new Empty<String>();
        Cons<String> l2 = new Cons<String>("Zeta", l1);
        Cons<String> l3 = new Cons<String>("Zeta", l2);
        Cons<String> l4 = new Cons<String>("Bazooka", l3);
        Cons<String> l5 = new Cons<String>("Bazooka", l3);

        return t.checkExpect(l2.sameCons(l2), true) &&
                t.checkExpect(l3.sameCons(l2), false) &&
                t.checkExpect(l4.sameCons(l2), false) &&
                t.checkExpect(l5.sameCons(l4), true);
    }
    boolean testSameList(Tester t) {
        IList<String> l1 = new Empty<String>();
        IList<String> l2 = new Cons<String>("Zeta", l1);
        IList<String> l3 = new Cons<String>("Zeta", l2);
        IList<String> l4 = new Cons<String>("Bazooka", l3);
        IList<String> l5 = new Cons<String>("Cheese", l4);

        IList<String> l6 = new Empty<String>();
        IList<String> l7 = new Cons<String>("Zeta", l1);
        IList<String> l8 = new Cons<String>("Zeta", l2);
        IList<String> l9 = new Cons<String>("Bazooka", l3);
        IList<String> l10 = new Cons<String>("Cheese", l4);

        return t.checkExpect(l1.sameList(l6), true) &&
                t.checkExpect(l1.sameList(l7), false) &&
                t.checkExpect(l2.sameList(l7), true) &&
                t.checkExpect(l3.sameList(l2), false) &&
                t.checkExpect(l5.sameList(l10), true);
    }
    boolean testMerge(Tester t) {
        IList<String> l1 = new Empty<String>();
        IList<String> l2 = new Cons<String>("Zeta", l1);
        IList<String> l3 = new Cons<String>("Zeta", l2);
        IList<String> l4 = new Cons<String>("Cheese", l3);
        IList<String> l5 = new Cons<String>("Bazooka", l4);

        IList<String> l6 = new Empty<String>();
        IList<String> l7 = new Cons<String>("Zeta", l1);
        IList<String> l8 = new Cons<String>("Zeta", l2);
        IList<String> l9 = new Cons<String>("Cheese", l3);
        IList<String> l10 = new Cons<String>("Bazooka", l4);

        IList<String> l11 = new Cons<String>("Cheese",
                new Cons<String>("Zeta",
                        new Cons<String>("Zeta",
                                new Cons<String>("Zeta",
                                        new Empty<String>()))));

        IList<String> l12 = new Cons<String>("Cheese", new Empty<String>());

        IList<String> l13 = new Cons<String>("Zeta",
                new Cons<String>("Zeta",
                        new Cons<String>("Zeta",
                                new Cons<String>("Cheese",
                                        new Empty<String>()))));
        return t.checkExpect(l9.merge(l2, lex), l11) &&
                t.checkExpect(l9.merge(l1, lex), l9) &&
                t.checkExpect(l8.merge(l2, len).merge(l12, len), l13) &&
                t.checkExpect(new Empty<String>().merge(l11, len), l11) &&
                t.checkExpect(new Empty<String>().merge(new Empty<String>(), len)
                        , new Empty<String>());
    }

}




