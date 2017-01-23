import tester.Tester;



// represent a list of string
interface ILoString {
    boolean isSorted(IStringCompare fun);
    boolean isSortedHelp(IStringCompare fun, String first);
    ILoString merge(ILoString l, IStringCompare fun);
    ILoString mergeWithCons(ConsLoString l, IStringCompare fun);
    ILoString mergeWithMt(MtLoString l, IStringCompare fun);
    ILoString sort(IStringCompare fun);
    boolean sameList(ILoString l);
    boolean sameMtLoString(MtLoString l);
    boolean sameConsLoString(ConsLoString l);
}



// represent compare for string
interface IStringCompare {
    boolean comesBefore(String s1, String s2);
}



// compare two string lexicographically
class StringLexComp implements IStringCompare {

    public boolean comesBefore(String s1, String s2) {
        return s1.compareTo(s2) < 0;
    }

}



// compare string by length
class StringLengthComp implements IStringCompare {
    public boolean comesBefore(String s1, String s2) {
        return s1.length() - s2.length() < 0;
    }

}



// represent empty list of string
class MtLoString implements ILoString {
    MtLoString() {
        // Its a empty list of string
    }
    public boolean isSorted(IStringCompare fun) {
        return true;
    }
    public boolean isSortedHelp(IStringCompare fun, String s1) {
        return true;
    }
    public ILoString sort(IStringCompare fun) {
        return this;
    }
    // use method mergeWithMt when the list is empty
    public ILoString merge(ILoString l, IStringCompare fun) {
        return l.mergeWithMt(this, fun);
    }

    public boolean sameList(ILoString l) {
        return l instanceof MtLoString;
    }

    public boolean sameMtLoString(MtLoString l) {
        return true;
    }
    public boolean sameConsLoString(ConsLoString l) {
        return false;
    }
    public ILoString mergeWithCons(ConsLoString l, IStringCompare fun) {
        return l;
    }
    public ILoString mergeWithMt(MtLoString l, IStringCompare fun) {
        return this;
    }
}



// represent a list of string
class ConsLoString implements ILoString {
    String first;
    ILoString rest;

    // constructor
    ConsLoString(String first, ILoString rest) {
        this.first = first;
        this.rest = rest;
    }
    // check if the given list is sorted by the given method
    public boolean isSorted(IStringCompare fun) {
        return this.rest.isSortedHelp(fun, this.first);
    }
    public boolean isSortedHelp(IStringCompare fun, String s1) {
        return (fun.comesBefore(s1, this.first)) &&
                this.rest.isSortedHelp(fun, this.first);
    }
    // sort the given list by the given method
    public ILoString sort(IStringCompare fun) {
        return new ConsLoString(this.first, new MtLoString()).merge(this.rest.sort(fun), fun);
    }

    //merge using double dispatch
    public ILoString merge(ILoString l, IStringCompare fun) {
        return l.mergeWithCons(this, fun);
    }
    // combine two list
    public ILoString mergeWithCons(ConsLoString l, IStringCompare fun) {
        if (fun.comesBefore(l.first, this.first)) {
            return new ConsLoString(l.first, this.merge(l.rest, fun));
        }
        else {
            return new ConsLoString(this.first, l.merge(this.rest, fun));
        }
    }
    public ILoString mergeWithMt(MtLoString l, IStringCompare fun) {
        return this;
    }

    //sameness using double dispatch
    public boolean sameList(ILoString l) {
        return l.sameConsLoString(this);
    }
    public boolean sameMtLoString(MtLoString l) {
        return false;
    }
    public boolean sameConsLoString(ConsLoString l) {
        return this.first.equals(l.first) &&
                this.rest.sameList(l.rest);
    }
}



// example and tests
class ExamplesStrings {
    IStringCompare lex = new StringLexComp();
    IStringCompare len = new StringLengthComp();

    boolean testLexCompare(Tester t) {
        return t.checkExpect(lex.comesBefore("a", "b"), true) &&
                t.checkExpect(lex.comesBefore("b", "a"), false) &&
                t.checkExpect(lex.comesBefore("a", "a"), false);
    }
    boolean testLengthCompare(Tester t) {
        return t.checkExpect(len.comesBefore("a", "ab"), true) &&
                t.checkExpect(len.comesBefore("ab", "a"), false) &&
                t.checkExpect(len.comesBefore("a", "a"), false);
    }
    boolean testIsSorted(Tester t) {
        ILoString l1 = new MtLoString();
        ILoString l2 = new ConsLoString("Bazooka", l1);
        ILoString l3 = new ConsLoString("Cheese", l2);
        ILoString l4 = new ConsLoString("Zeta", l3);
        ILoString l5 = new ConsLoString("Zeta", l4);
        return t.checkExpect(l1.isSorted(lex), true) &&
                t.checkExpect(l2.isSorted(lex), true) &&
                t.checkExpect(l3.isSorted(lex), false) &&
                t.checkExpect(l4.isSorted(lex), false) &&
                t.checkExpect(l5.isSorted(lex), false);
    }
    boolean testSort(Tester t) {
        ILoString l1 = new MtLoString();
        ILoString l2 = new ConsLoString("Zeta", l1);
        ILoString l3 = new ConsLoString("Zeta", l2);
        ILoString l4 = new ConsLoString("Bazooka", l3);
        ILoString l5 = new ConsLoString("Cheese", l4);

        ILoString l6 = new MtLoString();
        ILoString l7 = new ConsLoString("Bazooka", l6);
        ILoString l8 = new ConsLoString("Cheese", l7);
        ILoString l9 = new ConsLoString("Zeta", l8);
        ILoString l10 = new ConsLoString("Zeta", l9);
        return t.checkExpect(l5.sort(len), l10) &&
                t.checkExpect(new MtLoString().sort(len), new MtLoString());
    }
    boolean testSameMtLoString(Tester t) {
        MtLoString l1 = new MtLoString();
        MtLoString l2 = new MtLoString();
        ConsLoString l3 = new ConsLoString("", l1);
        return t.checkExpect(l1.sameMtLoString(l1), true) &&
                t.checkExpect(l2.sameMtLoString(l1), true) &&
                t.checkExpect(l3.sameMtLoString(l1), false);
    }
    boolean testSameConsLoString(Tester t) {
        ILoString l1 = new MtLoString();
        ConsLoString l2 = new ConsLoString("Zeta", l1);
        ConsLoString l3 = new ConsLoString("Zeta", l2);
        ConsLoString l4 = new ConsLoString("Bazooka", l3);
        ConsLoString l5 = new ConsLoString("Bazooka", l3);

        return t.checkExpect(l2.sameConsLoString(l2), true) &&
                t.checkExpect(l3.sameConsLoString(l2), false) &&
                t.checkExpect(l4.sameConsLoString(l2), false) &&
                t.checkExpect(l5.sameConsLoString(l4), true);
    }
    boolean testSameList(Tester t) {
        ILoString l1 = new MtLoString();
        ILoString l2 = new ConsLoString("Zeta", l1);
        ILoString l3 = new ConsLoString("Zeta", l2);
        ILoString l4 = new ConsLoString("Bazooka", l3);
        ILoString l5 = new ConsLoString("Cheese", l4);

        ILoString l6 = new MtLoString();
        ILoString l7 = new ConsLoString("Zeta", l1);
        ILoString l8 = new ConsLoString("Zeta", l2);
        ILoString l9 = new ConsLoString("Bazooka", l3);
        ILoString l10 = new ConsLoString("Cheese", l4);

        return t.checkExpect(l1.sameList(l6), true) &&
                t.checkExpect(l1.sameList(l7), false) &&
                t.checkExpect(l2.sameList(l7), true) &&
                t.checkExpect(l3.sameList(l2), false) &&
                t.checkExpect(l5.sameList(l10), true);
    }
    boolean testMerge(Tester t) {
        ILoString l1 = new MtLoString();
        ILoString l2 = new ConsLoString("Zeta", l1);
        ILoString l3 = new ConsLoString("Zeta", l2);
        ILoString l4 = new ConsLoString("Cheese", l3);
        ILoString l5 = new ConsLoString("Bazooka", l4);

        ILoString l6 = new MtLoString();
        ILoString l7 = new ConsLoString("Zeta", l1);
        ILoString l8 = new ConsLoString("Zeta", l2);
        ILoString l9 = new ConsLoString("Cheese", l3);
        ILoString l10 = new ConsLoString("Bazooka", l4);

        ILoString l11 = new ConsLoString("Cheese",
                new ConsLoString("Zeta",
                        new ConsLoString("Zeta",
                                new ConsLoString("Zeta",
                                        new MtLoString()))));

        ILoString l12 = new ConsLoString("Cheese", new MtLoString());
        ILoString l13 = new ConsLoString("Zeta",
                new ConsLoString("Zeta",
                        new ConsLoString("Zeta",
                                new ConsLoString("Cheese",
                                        new MtLoString()))));
        return t.checkExpect(l9.merge(l2, lex), l11) &&
                t.checkExpect(l9.merge(l1, lex), l9) &&
                t.checkExpect(l8.merge(l2, len).merge(l12, len), l13) &&
                t.checkExpect(new MtLoString().merge(l13, len), l13) &&
                t.checkExpect(new MtLoString().merge(new MtLoString(), len), new MtLoString());

    }
}
