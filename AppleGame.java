import java.awt.Color;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldcanvas.*;
import javalib.worldimages.*;
import tester.*;

// Posn class to represent positions
class Posn {
    // by convention (0,0) is at the top left
    // x represents horizontal displacement, y represents vertical displacement
    private int x;
    private int y;
    // constructor
    Posn(int x, int y) {
        this.setPosn(x, y);
    }
    // getters and setters
    int getX() { return x; }
    int getY() { return y; }
    void setX(int x) { this.x = x; }
    void setY(int y) { this.y = y; }
    Posn setPosn(int x, int y) {
        this.setX(x);
        this.setY(y);
        return this;
    }
    boolean samePosition(Posn p) {
        return this.x == p.x && this.y == p.y;
    }  
}
// Tuple class to staple two ints together
class Tuple {
    int a;
    int b;
    Tuple(int a, int b){
        this.a = a;
        this.b = b;
    }
}
// WorldObjects are classes that are in the world
abstract class IWorldObject {
    Posn position;
    IWorldObject(int x, int y) {
        this.position = new Posn(x, y);
    }
    int getXPos() { return position.getX(); }
    int getYPos() { return position.getY(); }
    Posn getPosn() { return position; }
    void setPosn(int x, int y) { position.setPosn(x, y); }
    void setX(int x) { position.setX(x); }
    void setY(int y) { position.setY(y); }
}

// Apple objects represent Apples in the game
class Apple extends IWorldObject {
    // constant to represent the amount fallen per tick
    private final static int DROPDIST = -20;
    // constructor to pass args to superclass constructor
    Apple(int x, int y) {
        super(x, y);
    }
    // checks if apple is on the ground
    boolean onTheGround() {
        return this.position.getY() >= 400;
    }
    // 
    Apple fall() {
        this.position.setY(this.getYPos() - DROPDIST);
        return this;
    }
    // to be called after the apple is on the ground or caught
    Apple resetApple() {
        this.position.setPosn((int)(341 * Math.random()) + 30, 0);
        return this;
    }
    WorldScene drawApple(WorldScene sc, WorldImage appleImg) {
        return sc.placeImageXY(appleImg, this.getXPos(), this.getYPos());
    }
}
// List interface
interface ILoApple {
    // drawing the apples onto the scene is passed to the Apple list to do
    WorldScene drawApples(WorldScene sc, WorldImage appleImg);
    void fall();
    Tuple caught(int basketx, Tuple acc);
}
// Cons thing
class ConsLoApple implements ILoApple {
    // data
    Apple first;
    // rest of the list
    ILoApple rest;
    ConsLoApple(Apple first, ILoApple rest) {
        this.first = first;
        this.rest = rest;
    }
    // draws the apples onto the scene
    public WorldScene drawApples(WorldScene sc, WorldImage appleImg) {
        return this.rest.drawApples(this.first.drawApple(sc, appleImg), appleImg);
    }
    // drops the apples in the list
    public void fall() {
        this.first.fall();
        this.rest.fall();
    }
    // counts the number of caught and missed apples in the list
    public Tuple caught(int basketx, Tuple acc) {
        if(this.first.onTheGround()) {
            int dx = this.first.getXPos() - basketx;
            if(dx < 24 && dx > -24) {
                acc.a++;
            }
            else {
                acc.b++;
            }
            this.first.resetApple();
        }
        return this.rest.caught(basketx, acc);
    }
}
// Empty class
class MtLoApple implements ILoApple {
    // empty constructor
    MtLoApple(){}
    // base cases
    public WorldScene drawApples(WorldScene sc, WorldImage appleImage){ return sc; }
    public void fall(){ return; }
    public Tuple caught(int basketx, Tuple acc) { return acc; }
}



class Basket extends IWorldObject {
    // constructor to pass stuff to superclass constructor
    public Basket(int x, int y) {
        super(x, y);
    }
    // on left moves the basket left by 10, on right moves the basket right by 10
    Basket moveOnKey(String ke) {
        if (ke.equals("left") && (this.getXPos() <= 34))
        { this.setX(24); }
        else if (ke.equals("left"))
        { this.setX(this.getXPos() - 10); }
        
        if (ke.equals("right") && (this.getXPos() >= 380))
        { this.setX(380); }
        else if (ke.equals("right"))
        { this.setX(this.getXPos() + 10); }
        
        return this;
    }
}

class AppleGame extends World {
    private int caught = 0;
    private int missed = 0;
    private final int width = 400;
    private final int height = 400;
    private WorldCanvas bg;
    private WorldImage bgImage;
    private Basket basket;
    private WorldImage basketImage;
    private ILoApple apples;
    private WorldImage appleImage;
    

    /** The constructor */
    public AppleGame(WorldImage basketImage, WorldImage AppleImage, WorldImage bgImg) {
        super();
        this.bg = new WorldCanvas(400, 400);
        this.basketImage = basketImage;
        this.appleImage = AppleImage;
        this.bgImage = bgImg;
        apples = new ConsLoApple(new Apple(200, 0),
                new ConsLoApple(new Apple((int) (Math.random() * 341 + 30), 100),
                        new ConsLoApple(new Apple((int)(Math.random() * 341 + 30), 200),
                                new MtLoApple())));
        
        this.basket = new Basket(200, 374);
    }
    
    // returns a WorldEnd method with the last scene for when the world ends 
    public WorldEnd worldEnds() {
        if (this.isOver()) {
            return new WorldEnd(true, this.lastScene(""));
        }
        else {
            return new WorldEnd(false, this.makeScene());
        }
    }
    // end condition for the game
    boolean isOver() {
        return this.caught >= 10 || this.missed >= 10;
    }
    //
    public WorldScene makeScene() {
        return this.placeBasket(
                apples.drawApples(
                        this.getEmptyScene().placeImageXY(
                                bgImage,
                                this.width / 2 + 5,
                                this.height / 2 + 5),
                                this.appleImage));
    }
    WorldScene placeBasket(WorldScene sc) {
        return sc.placeImageXY(this.basketImage, basket.getXPos(), basket.getYPos());
    }
    public WorldScene lastScene(String s) {
        String m = "default";
        if (this.caught >= 10)
            m = "You caught 10 apples, you win";
        else if (this.missed >= 10)
            m = "You missed 10 apples, you lose";
        return this.makeScene().placeImageXY(new TextImage(m, Color.red), this.width / 2, this.height / 2);
    }
    public World onKeyEvent(String ke) {
        basket.moveOnKey(ke);
        return this;
    }
    // every tick drop the apples
    // also check if the apples are in the basket or on the ground
    public World onTick(){
        apples.fall();
        Tuple t = apples.caught(basket.getXPos(), new Tuple(0, 0));
        this.caught += t.a;
        this.missed += t.b;
        return this;
    }
}

class AppleGameExamples {
    // Read images from file to WorldImage objects
    static WorldImage appleImage = new FromFileImage("red-apple.png");
    static WorldImage basketImage = new FromFileImage("Basket.png");
    static WorldImage appleTree = new FromFileImage("apple-tree.png");
    
    
    // test code
    
    // Posn tests
    Posn a = new Posn(10, 23);
    Posn b = new Posn(1, 2);
    Posn c = new Posn(10, 23);
    
    boolean testPosn(Tester t) {
        return t.checkExpect(this.a.getX(), 10)
                && t.checkExpect(a.getY(), 23)
                && t.checkExpect(a.samePosition(b), false)
                && t.checkExpect(a.samePosition(c), true)
                && t.checkExpect(c.samePosition(a), true)
                && t.checkExpect(a.setPosn(23,10).samePosition(new Posn(23, 10)), true);
    }
    
    // Apple tests
    Apple apple1 = new Apple(1, 235);
    Apple apple2 = new Apple(52, 400000);
    Apple apple3 = new Apple(1, 235);

    
    // On the floor testing
    boolean testOnTheGround(Tester t) {
        return t.checkExpect(apple1.onTheGround(), false)
                && t.checkExpect(apple2.onTheGround(), true)
                && t.checkExpect(apple3.onTheGround(), false);
    }

    
    // Test reset apple
    boolean testResetApple(Tester t) {
        return t.checkRange(apple1.resetApple().getXPos(), 0, 400)
                && t.checkExpect(apple1.getYPos(), 400);
    }
    
    // Basket tests
    Basket basket1 = new Basket(50, 50);
    Basket basket2 = new Basket(40, 34);
    Basket basket3 = new Basket(50, 50);

    // MoveOnKey() tests
    boolean testMoveOnKey(Tester t) {
	 basket1.moveOnKey("up");
        basket2.moveOnKey("right");
        basket3.moveOnKey("left");
        return t.checkExpect(basket1.getXPos(), 50)
                && t.checkExpect(basket1.getYPos(), 50)
                && t.checkRange(basket2.getXPos(), 40, 59)
                && t.checkExpect(basket2.getYPos(), 34)
                && t.checkRange(basket3.getXPos(), 50, 31)
                && t.checkExpect(basket3.getYPos(), 50);
    }
    
    // WorldObject tests
    IWorldObject worldApple = new Apple(50, 60);
    IWorldObject worldBasket = new Basket(43, 73);
    // Getter tests
    boolean testWorldObject(Tester t) {
        return t.checkExpect(worldApple.getXPos(), 50)
                && t.checkExpect(worldApple.getYPos(), 60)
                && t.checkExpect(worldApple.getPosn().samePosition(new Posn(50, 60)), true)
                && t.checkExpect(worldBasket.getXPos(), 43)
                && t.checkExpect(worldBasket.getYPos(), 73)
                && t.checkExpect(worldBasket.getPosn().samePosition(new Posn(43, 73)), true);
    }
    // Setter tests
    boolean testSet1(Tester t) {
        worldApple.setX(99);
        worldBasket.setX(99);
        worldApple.setY(33);
        worldBasket.setY(23);
        return t.checkExpect(worldApple.getXPos(), 99)
                && t.checkExpect(worldBasket.getXPos(), 99)
                && t.checkExpect(worldApple.getYPos(), 33)
                && t.checkExpect(worldBasket.getYPos(), 23);
    }
    // More setter tests
    boolean testSet2(Tester t) {
        worldApple.setPosn(1, 2);
        worldApple.setPosn(48, 67);
        return t.checkExpect(worldApple.getPosn().samePosition(new Posn(1, 2)), true)
                && t.checkExpect(worldBasket.getPosn().samePosition(new Posn(48, 67)), true);
    }
    
    Tuple aTuple = new Tuple(1, 2);
    // MtLoApple test
    boolean testMtLost(Tester t) {
        return t.checkExpect(new MtLoApple().caught(2, aTuple), aTuple);
    }
    public static void main(String[] args) {
        World AppleGame = new AppleGame(basketImage, appleImage, appleTree);
        AppleGame.bigBang(400, 400, 0.3);
    }
}