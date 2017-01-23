//import tester.*;
//
//interface IArith {
//    <R> R accept(IArithVisitor<R> visitor);
//}
//
//
//class Formula implements IArith {
//    IFunc2<Double, Double, Double> fun;
//    String name;
//    IArith left;
//    IArith right;
//    Formula(IFunc2<Double, Double, Double> fun, String name,
//            IArith left, IArith right) {
//        this.fun = fun;
//        this.name = name;
//        this.left = left;
//        this.right = right;
//    }
//    public <R> R accept(IArithVisitor<R> visitor) {
//        return visitor.visitFormula(this);
//    }
//}
//
//
//class Const implements IArith {
//    double num;
//    Const(double num) {
//        this.num = num;
//    }
//    public <R> R accept(IArithVisitor<R> visitor) {
//        return visitor.visitConst(this);
//    }
//}
//
//
//interface IFunc2<A1, A2, R> {
//    R apply(A1 a1, A2 a2);
//}
//
//// Examples of IFuncs to be used for testing
//class AddDouble implements IFunc2<Double, Double, Double> {
//    public Double apply(Double a1, Double a2) {
//        return a1 + a2;
//    }
//}
//class SubtractDouble implements IFunc2<Double, Double, Double> {
//    public Double apply(Double a1, Double a2) {
//        return a1 - a2;
//    }
//}
//class MultiplyDouble implements IFunc2<Double, Double, Double> {
//    public Double apply(Double a1, Double a2) {
//        return a1 * a2;
//    }
//}
//class DivideDouble implements IFunc2<Double, Double, Double> {
//    public Double apply(Double a1, Double a2) {
//        return a1 / a2;
//    }
//}
//class PowerDouble implements IFunc2<Double, Double, Double> {
//    public Double apply(Double a1, Double a2) {
//        return Math.pow(a1, a2);
//    }
//}
//interface IArithVisitor<R> {
//    R visitFormula(Formula f);
//    R visitConst(Const c);
//}
//class EvalVisitor implements IArithVisitor<Double> {
//    public Double visitFormula(Formula f) {
//        return f.fun.apply(f.left.accept(this), f.right.accept(this));
//    }
//    public Double visitConst(Const c) {
//        return c.num;
//    }
//}
//class PrintVisitor implements IArithVisitor<String> {
//    public String visitFormula(Formula f) {
//        String ret = "(" + f.name
//                        + " " + f.left.accept(this)
//                        + " " + f.right.accept(this) + ")";
//        return ret;
//    }
//    public String visitConst(Const c) {
//        return Double.toString(c.num);
//    }
//}
//class DoublerVisitor implements IArithVisitor<IArith> {
//    public IArith visitFormula(Formula f) {
//        return new Formula(f.fun, f.name, f.left.accept(this), f.right.accept(this));
//    }
//    public IArith visitConst(Const c) {
//        return new Const(c.num * 2);
//    }
//}
//
//class AllSmallVisitor implements IArithVisitor<Boolean> {
//    public Boolean visitFormula(Formula f) {
//        return f.left.accept(this) && f.right.accept(this);
//    }
//
//    public Boolean visitConst(Const c) {
//        return c.num < 10;
//    }
//    
//}
//
//class NoDivBy0 implements IArithVisitor<Boolean> {
//    public Boolean visitFormula(Formula f) {
//        if (f.name.equals("div")) {
//            return f.left.accept(this)
//                    && f.right.accept(this)
//                    && (Math.abs(f.right.accept(new EvalVisitor())) > 0.0001); 
//        }
//        else {
//            return f.left.accept(this) && f.right.accept(this);
//        }
//    }
//    public Boolean visitConst(Const c) {
//        return true;
//    }
//}
//
//class ExamplesVisitors {
//    IFunc2<Double, Double, Double> add = new AddDouble();
//    IFunc2<Double, Double, Double> sub = new SubtractDouble();
//    IFunc2<Double, Double, Double> mul = new MultiplyDouble();
//    IFunc2<Double, Double, Double> div = new DivideDouble();
//    IFunc2<Double, Double, Double> pow = new PowerDouble();
//    
//    IArithVisitor<Boolean> asv = new AllSmallVisitor();
//    IArithVisitor<Boolean> ndv = new NoDivBy0();
//    
//    boolean testEvalVisitor(Tester t) {
//        IArithVisitor<Double> ev = new EvalVisitor();
//        IArith c1 = new Const(1.3);
//        IArith c2 = new Const(1.7);
//        IArith c3 = new Const(2.3);
//        IArith c4 = new Const(2.7);
//        IArith f1 = new Formula(add, "f1", c1, c2);
//        IArith f2 = new Formula(add, "f2", c3, c4);
//        IArith f3 = new Formula(mul, "f3", f1, f2);
//        
//        IArith c5 = new Const(0.0);
//        IArith c6 = new Const(1.0);
//        IArith f4 = new Formula(pow, "pow", c5, c6);
//        return t.checkExpect(f3.accept(ev), new Double(15.0)) &&
//                t.checkExpect(f4.accept(ev), 0.0);
//    }
//    
//    boolean testPrintVisitor(Tester t) {
//        IArithVisitor<String> pr = new PrintVisitor();
//        IArith c1 = new Const(1.3);
//        IArith c2 = new Const(1.7);
//        IArith c3 = new Const(2.3);
//        IArith c4 = new Const(2.7);
//        IArith f1 = new Formula(add, "f1", c1, c2);
//        IArith f2 = new Formula(add, "f2", c3, c4);
//        IArith f3 = new Formula(mul, "f3", f1, f2);
//        return t.checkExpect(f3.accept(pr),
//                            "(f3 (f1 1.3 1.7) (f2 2.3 2.7))");
//    }
//    
//    boolean testDoublerVisitor(Tester t) {
//        IArithVisitor<IArith> dv = new DoublerVisitor();
//        IArith c1 = new Const(1.3);
//        IArith c2 = new Const(1.7);
//        IArith c3 = new Const(2.3);
//        IArith c4 = new Const(2.7);
//        IArith f1 = new Formula(add, "f1", c1, c2);
//        IArith f2 = new Formula(mul, "f2", c3, c4);
//        IArith f3 = new Formula(pow, "f3", f1, f2);
//        
//        IArith c5 = new Const(2.6);
//        IArith c6 = new Const(3.4);
//        IArith c7 = new Const(4.6);
//        IArith c8 = new Const(5.4);
//        IArith f4 = new Formula(add, "f1", c5, c6);
//        IArith f5 = new Formula(mul, "f2", c7, c8);
//        IArith f6 = new Formula(pow, "f3", f4, f5);
//        return t.checkExpect(f6, f3.accept(dv));
//    }
//    
//    boolean testAllSmallVisitor(Tester t) {
//        IArithVisitor<Boolean> as = new AllSmallVisitor();
//        IArith c1 = new Const(1.3);
//        IArith c2 = new Const(1.7);
//        IArith c3 = new Const(2.3);
//        IArith c4 = new Const(9.99999999);
//        IArith f1 = new Formula(add, "f1", c1, c2);
//        IArith f2 = new Formula(mul, "f2", c3, c4);
//        IArith f3 = new Formula(pow, "f3", f1, f2);
//        
//        IArith c5 = new Const(2.6);
//        IArith c6 = new Const(3.4);
//        IArith c7 = new Const(4.6);
//        IArith c8 = new Const(10.0000001);
//        IArith f4 = new Formula(add, "f1", c5, c6);
//        IArith f5 = new Formula(mul, "f2", c7, c8);
//        IArith f6 = new Formula(pow, "f3", f4, f5);
//        
//        IArith f7 = new Formula(mul, "mul", c8, c5);
//        return t.checkExpect(f3.accept(as), true) &&
//                t.checkExpect(f6.accept(as), false) &&
//                t.checkExpect(f7.accept(as), false);
//    }
//    
//    boolean testNoDivZero(Tester t) {
//        IArithVisitor<Boolean> ndz = new NoDivBy0();
//        IArith c1 = new Const(1.3);
//        IArith c2 = new Const(1.7);
//        IArith c3 = new Const(2.3);
//        IArith c4 = new Const(2.7);
//        IArith f1 = new Formula(add, "f1", c1, c2);
//        IArith f2 = new Formula(mul, "f2", c3, c4);
//        IArith f3 = new Formula(pow, "f3", f1, f2);
//        
//        IArith c5 = new Const(2.6);
//        IArith c6 = new Const(3.4);
//        IArith c7 = new Const(4.6);
//        IArith c8 = new Const(4.6);
//        IArith f4 = new Formula(add, "f1", c5, c6);
//        IArith f5 = new Formula(sub, "f2", c7, c8);
//        IArith f6 = new Formula(div, "div", f4, f5);
//        
//        IArith c9 = new Const(2.6);
//        IArith c10 = new Const(0);
//        IArith c11 = new Const(4.3);
//        IArith c12 = new Const(4.6);
//        IArith c13 = new Const(2.6);
//        IArith c14 = new Const(3.4);
//        IArith c15 = new Const(4.6);
//        IArith c16 = new Const(4.6);
//        IArith f7 = new Formula(div, "div", c9, c10);
//        IArith f8 = new Formula(pow, "pow", c11, c12);
//        IArith f9 = new Formula(add, "add", c13, c14);
//        IArith f10 = new Formula(sub, "sub", c15, c16);
//        IArith f11 = new Formula(div, "div", f7, f8);
//        IArith f12 = new Formula(div, "div", f9, f10);
//        IArith f13 = new Formula(mul, "mul", f11, f12);
//        
//        IArith c17 = new Const(2.6);
//        IArith c18 = new Const(0);
//        IArith c19 = new Const(3);
//        IArith c20 = new Const(2.3);
//        IArith c21 = new Const(2.6);
//        IArith f14 = new Formula(add, "add", c17, c18);
//        IArith f15 = new Formula(pow, "pow", f14, c19);
//        IArith f16 = new Formula(div, "div", c20, c21);
//        IArith f17 = new Formula(div, "div", f15, f16);
//        
//        return t.checkExpect(f3.accept(ndz), true) &&
//                t.checkExpect(f6.accept(ndz), false) &&
//                t.checkExpect(f12.accept(ndz), false) &&
//                t.checkExpect(f10.accept(ndz), true) &&
//                t.checkExpect(f13.accept(ndz), false) &&
//                t.checkExpect(f17.accept(ndz), true);
//    }
//}

interface IArith {
    <R> R accept(IArithVisitor<R> R);
}

class Const implements IArith {
    double num;

    Const(double num) {
        this.num = num;
    }

    @Override
    public <R> R accept(IArithVisitor<R> R) {
        return R.VisitConst(this);
    }
}

class Formula implements IArith {
    IFunc2<Double, Double, Double> fun;
    String name;
    IArith left;
    IArith right;

    Formula(IFunc2<Double, Double, Double> fun, String name, IArith left, IArith right) {
        this.fun = fun;
        this.name = name;
        this.left = left;
        this.right = right;
    }

    @Override
    public <R> R accept(IArithVisitor<R> R) {
        return R.VisitFormula(this);
    }
}

interface IFunc2<A1, A2, R> {
    R apply(A1 a1, A2 a2);
}

interface IArithVisitor<R> {
    R VisitFormula(Formula f);

    R VisitConst(Const c);
}

class Eval implements IArithVisitor<Double> {

    public Double VisitFormula(Formula f) {
        return f.fun.apply(f.left.accept(this), f.right.accept(this));
    }

    public Double VisitConst(Const c) {
        return c.num;
    }
}

class Add implements IFunc2<Double, Double, Double> {
    public Double apply(Double a1, Double a2) {
        return a1 + a2;
    }
}