import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;

class Node {
    // In logical coordinates, with the origin at the top-left corner of the
    // screen.
    Posn position;
    // the four adjacent cells to this one
    Node left;
    Node top;
    Node right;
    Node bottom;
    // list of booleans, are the listed directions available as paths to take
    boolean ct;
    boolean cb;
    boolean cr;
    boolean cl;
    // has the player traveled on this node at any point
    boolean checked;
    // is the player on this node
    boolean onPlayer;
    Posn t;
    Posn b;
    Posn r;
    Posn l;

    Node(int x, int y) {
        this.position = new Posn(x, y);
    }

    public void addLine() {
        // if (onPlayer) {
        // img = new RectangleImage(20, 20, "solid", new Color(0, 255, 0));
        // }
        // else if (checked) {
        // img = new RectangleImage(20, 20, "solid", new Color(130, 130, 130));
        // }
        // else {
        // img = new RectangleImage(20, 20, "solid", new Color(255, 255, 130));
        // }
        if (!ct) {
            this.t = new Posn(this.position.x + 10, this.position.y);
        }
        if (!cb) {
            this.b = new Posn(this.position.x + 10, this.position.y + 20);
        }
        if (!cr) {
            this.r = new Posn(this.position.x, this.position.y + 10);
        }
        if (!cl) {
            this.l = new Posn(this.position.x, this.position.y + 10);
        }
    }
}

// to represent the Maze Game World
public class MazeGameWorld extends World {
    int height;
    int width;
    // Define constant
    static final WorldImage VERTICLE_LINE = new LineImage(new Posn(0, 20), new Color(0, 0, 0));
    static final WorldImage HORIZONTAL_LINE = new LineImage(new Posn(20, 0), new Color(0, 0, 0));
    // All the nodes of the game
    ArrayList<ArrayList<Node>> board;

    // the constructor
    MazeGameWorld(int w, int h) {
        this.height = h;
        this.width = w;
        this.board = this.fixNear(this.init(w, h), w, h);
    }

    ArrayList<ArrayList<Node>> init(int w, int h) {
        ArrayList<ArrayList<Node>> l = new ArrayList<ArrayList<Node>>(w + 1);
        // initialize the array list with 0s
        for (int i = 0; i <= w; i++) {
            ArrayList<Node> line = new ArrayList<Node>(h + 1);
            for (int n = 0; n <= h; n++) {
                line.add(new Node(i, n));
            }
            l.add(line);
        }
        return l;
    }

    ArrayList<ArrayList<Node>> fixNear(ArrayList<ArrayList<Node>> l, int w, int h) {
        for (int i = 0; i <= w; i++) {
            for (int n = 0; n <= h; n++) {
                Node c = l.get(i).get(n);
                if (i == 0 && n == 0) {
                    c.left = c;
                    c.top = c;
                    c.right = l.get(i + 1).get(n);
                    c.bottom = l.get(i).get(n + 1);
                } else if (i == w && n == 0) {
                    c.right = c;
                    c.top = c;
                    c.left = l.get(i - 1).get(n);
                    c.bottom = l.get(i).get(n + 1);
                } else if (i == 0 && n == h) {
                    c.left = c;
                    c.top = l.get(i).get(n - 1);
                    c.bottom = c;
                    c.right = l.get(i + 1).get(n);
                } else if (i == w && n == h) {
                    c.right = c;
                    c.bottom = c;
                    c.top = l.get(i).get(n - 1);
                    c.left = l.get(i - 1).get(n);
                } else if (i == 0) {
                    c.left = c;
                    c.top = l.get(i).get(n - 1);
                    c.right = l.get(i + 1).get(n);
                    c.bottom = l.get(i).get(n + 1);
                } else if (n == 0) {
                    c.top = c;
                    c.left = l.get(i - 1).get(n);
                    c.right = l.get(i + 1).get(n);
                    c.bottom = l.get(i).get(n + 1);
                } else if (i == w) {
                    c.right = c;
                    c.top = l.get(i).get(n - 1);
                    c.left = l.get(i - 1).get(n);
                    c.bottom = l.get(i).get(n + 1);
                } else if (n == h) {
                    c.bottom = c;
                    c.top = l.get(i).get(n - 1);
                    c.left = l.get(i - 1).get(n);
                    c.right = l.get(i + 1).get(n);
                } else {
                    c.top = l.get(i).get(n - 1);
                    c.left = l.get(i - 1).get(n);
                    c.right = l.get(i + 1).get(n);
                    c.bottom = l.get(i).get(n + 1);
                }
            }
        }
        return l;
    }

    public void breakEdge(Node n, ArrayList<Node> l, int w, int h) {
        n.checked = true;
        if (n.position.equals(new Posn(w, h))) {
        } else {
            Double i = Math.random();
            if (i <= 0.25 && !n.top.checked && !n.top.equals(n)) {
                l.add(n);
                n.ct = true;
                n.top.cb = true;
                breakEdge(n.top, l, w, h);
            } else if (i <= 0.5 && i > 0.25 && !n.bottom.checked && !n.bottom.equals(n)) {
                l.add(n);
                n.cb = true;
                n.bottom.ct = true;
                breakEdge(n.bottom, l, w, h);
            } else if (i <= 0.75 && i > 0.5 && !n.left.checked && !n.left.equals(n)) {
                l.add(n);
                n.cl = true;
                n.left.cr = true;
                breakEdge(n.left, l, w, h);
            } else if (i <= 1 && i > 0.75 && !n.right.checked && !n.right.equals(n)) {
                l.add(n);
                n.cr = true;
                n.right.cl = true;
                breakEdge(n.right, l, w, h);
            } else if (n.top.checked && n.bottom.checked && n.left.checked && n.right.checked) {
                if (l.size() == 1) {
                    breakEdge(n, l, w, h);
                }
                else { l.remove(l.size() - 1);
                breakEdge(l.get(l.size() - 1), l, w, h); }
            }
            else { breakEdge(n, l, w, h); }
        }
    }
    
    
    public void breakEdges(int w, int h) {
        for (int i = 0; i < w * h / 2; i++) {
            int n = (int)(Math.floor(Math.random() * w));
            int m = (int)(Math.floor(Math.random() * h));
            Double q = Math.random();
            if (q <= 0.25) {
                this.board.get(n).get(m).ct = true;
                this.board.get(n).get(m).top.cb = true;
            }
            else if (q <= 0.5) {
                this.board.get(n).get(m).cb = true;
                this.board.get(n).get(m).top.ct = true;
            }
            else if (q <= 0.75) {
                this.board.get(n).get(m).cr = true;
                this.board.get(n).get(m).top.cl = true;
            }
            else if (q <= 1) {
                this.board.get(n).get(m).cl = true;
                this.board.get(n).get(m).top.cr = true;
            }
        }
    }

    public WorldScene makeScene() {
        WorldScene bg = new WorldScene(this.width * 20 + 20, this.height * 20 + 20);
        for (int i = 0; i < this.board.size(); i++) {
            for (int n = 0; n < this.board.get(i).size(); n++) {
//                bg.placeImageXY(new RectangleImage(20, 20, "solid", new Color(255, 255, 130)),
//                        (this.board.get(i).get(n).position.x * 20) + 10,
//                        (this.board.get(i).get(n).position.y * 20) + 10);
                if (!((this.board.get(i).get(n).r) == null)) {
                    bg.placeImageXY(MazeGameWorld.VERTICLE_LINE,
                            this.board.get(i).get(n).r.x * 20,
                            this.board.get(i).get(n).r.y * 20);
                }
                if (!((this.board.get(i).get(n).l) == null)) {
                    bg.placeImageXY(MazeGameWorld.VERTICLE_LINE,
                            this.board.get(i).get(n).l.x * 20,
                            this.board.get(i).get(n).l.y * 20);
                }
                if (!((this.board.get(i).get(n).t) == null)) {
                    bg.placeImageXY(MazeGameWorld.HORIZONTAL_LINE,
                            this.board.get(i).get(n).t.x * 20,
                            this.board.get(i).get(n).t.y * 20);
                }
                if (!((this.board.get(i).get(n).b) == null)) {
                    bg.placeImageXY(MazeGameWorld.HORIZONTAL_LINE,
                            this.board.get(i).get(n).b.x * 20,
                            this.board.get(i).get(n).b.y * 20);
                }
            }
        }
        return bg;
    }
    
    public void creatEdge(int w, int h) {
        for (int i = 0; i <= w; i++) {
            for (int n = 0; n <= h; n++) {
                this.board.get(i).get(n).addLine();
            }
    }
    }
}

class Example {
    void test(Tester t) {
        MazeGameWorld w2 = new MazeGameWorld(50, 30);
        ArrayList<Node> l = new ArrayList<Node>();
        l.add(w2.board.get(0).get(0));
        w2.breakEdge(w2.board.get(0).get(0), l, 50, 30);
        System.out.println(w2.board.get(0).get(0).ct);
        System.out.println(w2.board.get(0).get(0).cb);
        System.out.println(w2.board.get(0).get(0).cl);
        System.out.println(w2.board.get(0).get(0).cr);
        System.out.println(w2.board.get(1).get(0).ct);
        System.out.println(w2.board.get(1).get(0).cb);
        System.out.println(w2.board.get(1).get(0).cl);
        System.out.println(w2.board.get(1).get(0).cr);
        System.out.println(w2.board.get(0).get(1).ct);
        System.out.println(w2.board.get(0).get(1).cb);
        System.out.println(w2.board.get(0).get(1).cl);
        System.out.println(w2.board.get(0).get(1).cr);
        System.out.println(w2.board.get(1).get(1).ct);
        System.out.println(w2.board.get(1).get(1).cb);
        System.out.println(w2.board.get(1).get(1).cl);
        System.out.println(w2.board.get(1).get(1).cr);
//        w2.breakEdges(50, 30);
        w2.creatEdge(50, 30);
        System.out.println(w2.board.get(0).get(1).t);
        System.out.println(w2.board.get(0).get(1).b);
        System.out.println(w2.board.get(0).get(1).l);
        System.out.println(w2.board.get(0).get(1).r);
        w2.bigBang(1020, 620);
    }
}
