// Assingment 10
// Jiatong Gao
// Hao Zhang

import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.HashMap;
import tester.*;

// Represents a cell in this game
class Node {

    int x;
    int y;
    Wall top;
    Wall bottom;
    Wall left;
    Wall right;
    boolean LRWall;
    boolean TBWall;
    static final int SIZE = 20;
    
    // constructor
    Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.LRWall = true;
        this.TBWall = true;
    }

    // determine if two object are equal
    public boolean equals(Object object) {
        if (object instanceof Node) {
            Node n = (Node) object;
            return this.x == n.x && this.y == n.y;
        }
        return false;
    }
    
    // calculate the hashcode for node
    public int hashCode() {
        return this.x * 1000 + this.y * 10;
    }
    
    // Draws this node to the screen
    WorldScene draw(WorldScene base) {
        WorldImage image = new RectangleImage(SIZE, SIZE, "solid", new Color(150, 150, 150));
        if (this.LRWall) {
            image = new OverlayOffsetImage
                    (new LineImage(new Posn(0, SIZE), 
                            new Color(0, 0, 0)), -SIZE / 2 + 1, 0, image);
        }
        if (this.TBWall) {
            image = new OverlayOffsetImage
                    (new LineImage(new Posn(SIZE, 0), 
                            new Color(0, 0, 0)), 0, -SIZE / 2 + 1, image);
        }

        base.placeImageXY(image, y * SIZE + SIZE / 2, x * SIZE + SIZE / 2);

        return base;
    }
    
}

// Represents wall
class Wall {
    int weight;
    Node n1;
    Node n2;

    // constructor
    Wall(Node n1, Node n2, int weight) {
        this.n1 = n1;
        this.n2 = n2;
        this.weight = weight;
    }

    // Returns the other node in the wall
    Node side(Node cell) {
        if (this.n1.equals(cell)) {
            return this.n2;
        } 
        else{
            return this.n1;
        }
    }
}

// Represents a maze.
class Maze {
    int rows;
    int cols;
    ArrayList<Node> node;

    Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.genMaze();
    }

    // generate a maze
    void genMaze() {
        this.node = new ArrayList<Node>();
        ArrayList<ArrayList<Node>> matrix = new ArrayList<ArrayList<Node>>();

        for (int x = 0; x < this.rows; x++) {
            ArrayList<Node> row = new ArrayList<Node>();
            for (int y = 0; y < this.cols; y++) {
                Node cell = new Node(x, y);
                this.node.add(cell);
                row.add(cell);
            }
            matrix.add(row);
        }

        ArrayList<Wall> walls;

        walls = this.createWall(matrix, 0);
        walls = this.kruskal(this.node, walls);

        for (Wall e : walls) {
            Node c1 = e.n1;
            Node c2 = e.n2;
            if (c1.x != c2.x) {
                if (c1.x > c2.x) {
                    c2.TBWall = false;
                    c2.bottom = e;
                    c1.top = e;
                } 
                else {
                    c1.TBWall = false;
                    c1.bottom = e;
                    c2.top = e;
                }
            } 
            else {
                if (c1.y > c2.y) {
                    c2.LRWall = false;
                    c2.right = e;
                    c1.left = e;
                } 
                else {
                    c1.LRWall = false;
                    c1.right = e;
                    c2.left = e;
                }
            }
        }
    }

    // Returns the node at the given row and column
    Node nodeAt(int x, int y) {
        return this.node.get(x * this.cols + y);
    }

    // generate walls with random weight
    ArrayList<Wall> createWall(ArrayList<ArrayList<Node>> matrix, int horizontalWeight) {
        ArrayList<Wall> walls = new ArrayList<Wall>();
        Random rand = new Random();

        for (int x = 0; x < this.rows; x++) {
            for (int y = 0; y < this.cols; y++) {
                Node cell = matrix.get(x).get(y);
                if (x < this.rows - 1) {
                    walls.add(new Wall(cell, matrix.get(x + 1).get(y), 
                            rand.nextInt(100) + horizontalWeight));
                }
                if (y < this.cols - 1) {
                    walls.add(new Wall(cell, matrix.get(x).get(y + 1), rand.nextInt(100)));
                }
            }
        }

        return walls;
    }

    // break the wall
    ArrayList<Wall> kruskal(ArrayList<Node> node, ArrayList<Wall> walls) {

        HashMap<Integer, Integer> headers = new HashMap<Integer, Integer>();
        for (Node cell : node) {
            headers.put(cell.hashCode(), cell.hashCode());
        }
        this.wallSort(walls);
        ArrayList<Wall> result = new ArrayList<Wall>();
        while (walls.size() > 0) {
            Wall wall = walls.remove(0);
            int header1 = headers.get(wall.n1.hashCode());
            while (header1 != headers.get(header1)) {
                header1 = headers.get(header1);
            }
            int header2 = headers.get(wall.n2.hashCode());
            while (header2 != headers.get(header2)) {
                header2 = headers.get(header2);
            }
            if (header1 != header2) {
                headers.put(header1, header2);
                result.add(wall);
            }
        }

        return result;
    }

    // Sorts the walls
    void wallSort(ArrayList<Wall> walls) {
        this.mergeHelp(walls, 0, walls.size() - 1);
    }

    // helper function for sort wall
    void mergeHelp(ArrayList<Wall> walls, int from, int to) {
        if (to > from) {
            int middle = (from + to) / 2;
            mergeHelp(walls, from, middle);
            mergeHelp(walls, middle + 1, to);
            int index1 = from;
            int index2 = middle + 1;
            ArrayList<Wall> result = new ArrayList<Wall>();
            while (index1 <= middle && index2 <= to) {
                if (walls.get(index1).weight <= walls.get(index2).weight) {
                    result.add(walls.get(index1));
                    index1++;
                } 
                else {
                    result.add(walls.get(index2));
                    index2++;
                }
            }
            if (index1 <= middle) {
                for (int i = index1; i <= middle; i++) {
                    result.add(walls.get(i));
                }
            } 
            else if (index2 <= to) {
                for (int i = index2; i <= to; i++) {
                    result.add(walls.get(i));
                }
            }
            for (int i = 0; i < result.size(); i++) {
                walls.set(from + i, result.get(i));
            }
        }
    }

    // draw the maze to another scene
    WorldScene draw(WorldScene base) {
        for (Node y : this.node) {
            base = y.draw(base);
        }
        return base;
    }
}

// represent maze world
class MazeWorld extends World {
    int row;
    int column;
    Maze maze;

    MazeWorld(int row, int column) {
        this.maze = new Maze(row, column);
    }

    // initialize a new maze
    void iniMaze() {
        this.maze.genMaze();
    }
    
    // draw the world
    public WorldScene makeScene() {
        WorldScene scene = this.maze.draw(this.getEmptyScene());
        return scene;
    }


}


// Examples and tests
class ExamplesMaze {

    Node n1;
    Node n2;
    Node n3;
    Node n4;

    Wall e1;
    Wall e2;
    Wall e3;
    Wall e4;

    // Initializes Wall fields for test purpose
    void initWalls() {
        this.initNodes();
        this.e1 = new Wall(n1, n2, 20);
        this.e2 = new Wall(n2, n4, 10);
        this.e3 = new Wall(n3, n4, 60);
        this.e4 = new Wall(n3, n1, 10);
    }

    // Initializes Node fields for test purpose
    void initNodes() {
        this.n1 = new Node(0, 0);
        this.n2 = new Node(10, 0);
        this.n3 = new Node(0, 10);
        this.n4 = new Node(10, 10);
    }

    // Tests the wallSort method for Mazes.
    void testWallSort(Tester t) {
        Maze m = new Maze(5, 5);
        ArrayList<Wall> walls = new ArrayList<Wall>();
        ArrayList<Wall> sorted = new ArrayList<Wall>();
        this.initWalls();
        m.wallSort(walls);
        t.checkExpect(walls, sorted);
        walls.add(this.e1);
        sorted.add(this.e1);
        m.wallSort(walls);
        t.checkExpect(walls, sorted);
        walls.add(this.e2);
        walls.add(this.e3);
        walls.add(this.e4);
        sorted.clear();
        sorted.add(this.e2);
        sorted.add(this.e4);
        sorted.add(this.e1);
        sorted.add(this.e3);
        m.wallSort(walls);
        t.checkExpect(walls, sorted);
    }

    // Tests the kruskal method for Mazes.
    void testKruskal(Tester t) {
        this.initWalls();
        Maze m = new Maze(5, 5);
        ArrayList<Node> cells = new ArrayList<Node>
        (Arrays.asList(this.n1, this.n2, this.n3, this.n4));
        ArrayList<Wall> walls = new ArrayList<Wall>();
        ArrayList<Wall> result = new ArrayList<Wall>();
        walls.add(this.e1);
        walls.add(this.e2);
        walls.add(this.e3);
        walls.add(this.e4);
        result.add(this.e2);
        result.add(this.e4);
        result.add(this.e1);
        t.checkExpect(m.kruskal(cells, walls), result);
    }

    // Tests the mergeHelp method for Mazes.
    void testMergeHelp(Tester t) {
        Maze m = new Maze(5, 5);
        ArrayList<Wall> walls = new ArrayList<Wall>();
        ArrayList<Wall> result = new ArrayList<Wall>();
        this.initWalls();
        m.mergeHelp(walls, 0, -1);
        t.checkExpect(walls, result);
        walls.add(this.e1);
        result.add(this.e1);
        m.mergeHelp(walls, 0, 0);
        t.checkExpect(walls, result);
    }

    // Tests the side method for Walls.
    void testGetOtherNode(Tester t) {
        this.initWalls();
        t.checkExpect(this.e1.side(this.n1), this.n2);
        t.checkExpect(this.e1.side(this.n2), this.n1);
    }
    
    // launch bigbang
    void Maze(Tester t) {
        int row = 30;
        int column = 40;
        MazeWorld world = new MazeWorld(row, column);
        world.bigBang(column * 20, row * 20);
    }

}
