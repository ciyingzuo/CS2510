import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;

// Represents an individual tile
public class Tile {
    // The number on the tile. Use 0 to represent the hole
    int value;

    Tile(int value) {
        this.value = value;
    }

    // Draws this tile onto the background at the specified logical coordinates
    WorldImage drawAt(int col, int row) {
        if (this.value == 0) {
            return new RectangleImage(col * 100, row * 100, "solid", new Color(255, 0, 0));
        } else
            return new RectangleImage(col * 100, row * 100, "outline", new Color(0, 255, 0));
    }
}

class FifteenGame extends World {
    // represents the rows of tiles
    ArrayList<ArrayList<Tile>> tiles;
    Posn p;

    FifteenGame() {
        this.tiles = new ArrayList<ArrayList<Tile>>();
        for (int i = 0; i < 4; i++) {
            ArrayList<Tile> a = new ArrayList<Tile>();
            for (int j = 0; j < 4; j++) {
                a.add(new Tile(j + i * 4));
            }
            this.tiles.add(a);
        }
        this.p = new Posn(0, 0);
    }

    // draws the game
    // public WorldImage makeImage() { ... }
    // handles keystrokes
    public void onKeyEvent(String k) {
        // needs to handle up, down, left, right to move the hole
        // extra: handle "u" to undo moves
        if (k == "up") {
            if (this.p.y == 3) {
            } else {
                Tile t = this.tiles.get(p.x).get(p.y + 1);
                this.tiles.get(this.p.y).set(this.p.x, t);
                this.p.y = this.p.y - 1;
                this.tiles.get(this.p.x).set(this.p.y, new Tile(0));
            }
        } else if (k == "down") {
            if (this.p.y == 0) {
            } else {
                Tile t = this.tiles.get(p.x).get(p.y - 1);
                this.tiles.get(this.p.y).set(this.p.x, t);
                this.p.y = this.p.y + 1;
                this.tiles.get(this.p.y).set(this.p.y, new Tile(0));
            }
        } else if (k == "left") {
            if (this.p.x == 1) {
            } else {
                Tile t = this.tiles.get(p.x + 1).get(p.y);
                this.tiles.get(this.p.y).set(this.p.x, t);
                this.p.x = this.p.x + 1;
                this.tiles.get(this.p.x).set(this.p.y, new Tile(0));
            }
        } else if (k == "right") {
            if (this.p.x == 3) {
            } else {
                Tile t = this.tiles.get(p.x - 1).get(p.y);
                this.tiles.get(this.p.y).set(this.p.x, t);
                this.p.x = this.p.x - 1;
                this.tiles.get(this.p.x).set(this.p.y, new Tile(0));
            }
        } else if (k == "u") {

        } else {
        }
    }

    @Override
    public WorldScene makeScene() {
        WorldScene background = new WorldScene(400, 400);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile t = this.tiles.get(j).get(i);
                background.placeImageXY(t.drawAt(j, i),  j * 100-50, i * 100-50);
            }
        }
        return background;
    }
}

class ExampleFifteenGame {
    void testGame(Tester t) {
        FifteenGame g = new FifteenGame();
        g.bigBang(400, 400);
    }
}
