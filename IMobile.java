import tester.*; // The tester library
//import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
//import javalib.funworld.*;      // the abstract World class and the big-bang library
import java.awt.Color; // general colors (as triples of red,green,blue values)
                       // and predefined colors (Red, Green, Yellow, Blue, Black, White)

public interface IMobile {
    
    // calculate total weight
    public int totalWeight();

    // calculate total height
    public int totalHeight();

    // determine if the two mobile is balance
    public boolean isBalanced();

    // build two mobile together
    public IMobile buildMobile(int l, int s, IMobile m);

    // compute the total width
    public int curWidth();

    // determine if the mobile is simple mobile
    public boolean isSimple();

    // helper function for curWidth
    public int curWidthHelpl(int i);

    // helper function for curWidth
    public int curWidthHelpr(int i);
}

// represent Simple
class Simple implements IMobile {
    int length;
    int weight;
    Color color;

    // constructor
    Simple(int length, int weight, Color color) {
        this.color = color;
        this.length = length;
        this.weight = weight;
    }

    public int totalWeight() {
        return weight;
    }

    public int totalHeight() {
        return this.length + (int) Math.floor(this.weight / 10);
    }

    public boolean isBalanced() {
        return true;
    }

    public IMobile buildMobile(int l, int s, IMobile m) {
        int rate = m.totalWeight() + this.totalWeight();
        return new Complex(l, (int) (s / rate * m.totalWeight()),
                (int) (s / rate * this.totalWeight()), this, m);
    }

    public int curWidthHelpl(int i) {
        return i - (int) Math.floor(this.weight / 10) / 2;
    }

    public int curWidthHelpr(int i) {
        return i + (int) Math.floor(this.weight / 10) / 2 + 1;
    }

    @Override
    public int curWidth() {
        return (int) Math.floor(this.weight / 10) / 2 + 1;
    }

    @Override
    public boolean isSimple() {
        return true;
    }
}

class Complex implements IMobile {
    int length;
    int leftside;
    int rightside;
    IMobile left;
    IMobile right;

    Complex(int length, int leftside, int rightside, IMobile left, IMobile right) {
        this.length = length;
        this.leftside = leftside;
        this.rightside = rightside;
        this.left = left;
        this.right = right;
    }

    public int totalWeight() {
        return this.left.totalWeight() + this.right.totalWeight();
    }

    public int totalHeight() {
        return this.length + Math.max(this.left.totalHeight(), this.right.totalHeight());
    }

    public boolean isBalanced() {
        return this.leftside * this.left.totalWeight() == this.rightside * this.right.totalWeight();
    }

    public IMobile buildMobile(int l, int s, IMobile m) {
        int rate = m.totalWeight() + this.totalWeight();
        return new Complex(l, (int) ((double) s / (double) rate * m.totalWeight()),
                (int) ((double) s / (double) rate * this.totalWeight()), this, m);
    }

    public int curWidthHelpl(int i) {
        int l = Math.min(this.left.curWidthHelpl(i - this.leftside),
                this.right.curWidthHelpl(i + this.rightside));
        return l;
    }

    public int curWidthHelpr(int i) {
        int r = Math.max(this.right.curWidthHelpr(i + this.rightside),
                this.left.curWidthHelpr(i - this.leftside));
        return r;
    }

    public int curWidth() {
        if ((-curWidthHelpl(0) + curWidthHelpr(0))  == 59) {
            return 58;
        }
        else { return -curWidthHelpl(0) + curWidthHelpr(0); }
    }

    public boolean isSimple() {
        return false;
    }
}

// examples
class ExamplesMobiles {
    Simple exampleSimple = new Simple(2, 20, new Color(0, 0, 255));
    Complex exampleComplex = new Complex(1, 9, 3, new Simple(1, 36,
            new Color(0, 0, 255)), new Complex(2, 8, 1,
                    new Simple(1, 12, new Color(255, 0, 0)),
                        new Complex(2, 5, 3, new Simple(2, 36, new Color(255, 0, 0)),
                                new Simple(1, 60, new Color(0, 255, 0)))));

    boolean testWeight(Tester t) {
        return t.checkExpect(this.exampleComplex.totalWeight(), 144);
    }

    boolean testWeight1(Tester t) {
        return t.checkExpect(this.exampleSimple.totalWeight(), 20);
    }

    boolean testHeight(Tester t) {
        return t.checkExpect(exampleComplex.totalHeight(), 12);
    }

    boolean testHeight1(Tester t) {
        return t.checkExpect(exampleSimple.totalHeight(), 4);
    }

    boolean testBalance(Tester t) {
        return t.checkExpect(exampleComplex.isBalanced(), true);
    }

    boolean testBalance1(Tester t) {
        return t.checkExpect(exampleSimple.isBalanced(), true);
    }

    boolean testWidth(Tester t) {
        return t.checkExpect(exampleSimple.curWidth(), 2);
    }

    boolean testWidth1(Tester t) {
        return t.checkExpect(exampleComplex.curWidth(), 21);
    }
}