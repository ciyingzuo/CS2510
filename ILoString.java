//import tester.*;
//
//public interface ILoString {
//    boolean isSorted(IStringsCompare fun);
//
//    ILoString sort();
//
//    ILoString sortHelp();
//
//    ILoString merge(ILoString that, IStringsCompare fun);
//
//    String getRest();
//
//    ILoString getRR();
//}
//
//interface IStringsCompare {
//    boolean comesBefore(String s1, String s2);
//}
//
//class StringLexComp implements IStringsCompare {
//
//    public boolean comesBefore(String s1, String s2) {
//        return s1.compareTo(s2) <= 0;
//    }
//
//}
//
//class ConsLoString implements ILoString {
//    String first;
//    ILoString rest;
//
//    ConsLoString(String first, ILoString rest) {
//        this.first = first;
//        this.rest = rest;
//    }
//
//    public String getRest() {
//        return this.first;
//    }
//
//    public ILoString getRR() {
//        return this.rest;
//    }
//
//    public boolean isSorted(IStringsCompare fun) {
//        return fun.comesBefore(this.first, this.rest.getRest()) && this.rest.isSorted(fun);
//    }
//
//    public ILoString merge(ILoString that, IStringsCompare fun) {
//        if (that instanceof MtLoString) {
//            return this;
//        } else {
//            ILoString l = new ConsLoString(((ConsLoString) that).first, this);
//            if (l.isSorted(fun)) {
//                return l.merge(((ConsLoString) that).rest, fun);
//            } else {
//                return new ConsLoString(this.first, this.rest.merge(that, fun));
//            }
//        }
//        // a c e, b d
//    }
//
//    public ILoString sort() {
//        if (this.first.compareTo(this.rest.getRest()) <= 0) {
//            return new ConsLoString(this.first, this.rest.sort());
//        } else {
//            return new ConsLoString(this.rest.getRest(), new ConsLoString(this.first, this.rest.getRR()));
//        }
//    }
//
//    @Override
//    public ILoString sortHelp() {
//        if (!this.isSorted(new StringLexComp())) {
//            return this.sort().sortHelp();
//        } else {
//            return this;
//        }
//    }
//}
//
//class MtLoString implements ILoString {
//    MtLoString() {
//        // its empty
//    }
//
//    public boolean isSorted(IStringsCompare fun) {
//        return true;
//    }
//
//    public ILoString merge(ILoString that, IStringsCompare fun) {
//        return that;
//    }
//
//    public String getRest() {
//        return "z";
//    }
//
//    public ILoString sort() {
//        return this;
//    }
//
//    @Override
//    public ILoString getRR() {
//        return this;
//    }
//
//    @Override
//    public ILoString sortHelp() {
//        return this;
//    }
//}
//
//class Example {
//    ILoString l1 = new ConsLoString("A", new ConsLoString("C", new ConsLoString("E", new MtLoString())));
//    ILoString l2 = new ConsLoString("B", new ConsLoString("D", new ConsLoString("F", new MtLoString())));
//    ILoString l3 = new ConsLoString("A", new ConsLoString("B", new ConsLoString("C",
//            new ConsLoString("D", new ConsLoString("E", new ConsLoString("F", new MtLoString()))))));
//    ILoString l4 = new ConsLoString("B", new ConsLoString("D",
//            new ConsLoString("F", new ConsLoString("A", new ConsLoString("C", new MtLoString())))));
//    StringLexComp s = new StringLexComp();
//
//    public boolean test1(Tester t) {
//        return t.checkExpect("B".compareTo("A"), 1);
//    }
//
//    public boolean test2(Tester t) {
//        return t.checkExpect(l4.isSorted(s), false);
//    }
//
//    public boolean test(Tester t) {
//        return t.checkExpect(l1.merge(l2, s), new ConsLoString("A", new ConsLoString("B", new ConsLoString("C",
//                new ConsLoString("D", new ConsLoString("E", new ConsLoString("F", new MtLoString())))))));
//    }
//
//    public boolean test3(Tester t) {
//        return t.checkExpect(l4.sortHelp(), new ConsLoString("A", new ConsLoString("B",
//                new ConsLoString("C", new ConsLoString("D", new ConsLoString("F", new MtLoString()))))));
//        
//    }
//
//}