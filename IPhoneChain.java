import tester.*;

// represent Phone chain
public interface IPhoneChain {
    int countPlayers();

    int countPlayersCalled();

    boolean isEmpty();

    boolean willCall(String s);

    int longestChain();
}

// represent empty list
class MtLoPhoneChain implements IPhoneChain {
    MtLoPhoneChain() {
        // no item in this list
    }

    // return how many players
    public int countPlayers() {
        return 0;
    }

    // return how many player will be called
    public int countPlayersCalled() {
        return 0;
    }

    // return weather is empty
    public boolean isEmpty() {
        return true;
    }

    // return weather this player will called
    public boolean willCall(String s) {
        return false;
    }

    // help function for longestCHain
    public int longestChain() {
        return 0;
    }

}

// represent list
class ConsLoPhoneChain implements IPhoneChain {
    Player first;
    IPhoneChain rest;
    IPhoneChain rest2;

    ConsLoPhoneChain(Player first, IPhoneChain rest, IPhoneChain rest2) {
        this.first = first;
        this.rest = rest;
        this.rest2 = rest2;
    }

    // return how many players
    public int countPlayers() {
        return 1 + this.rest.countPlayers() + this.rest2.countPlayers();
    }

    // return how many player will be called
    public int countPlayersCalled() {
        if (this.rest.isEmpty() && this.rest2.isEmpty()) 
        { return 0; }
        else if ((this.rest.isEmpty() && !this.rest2.isEmpty()) ||
                (!this.rest.isEmpty() && this.rest2.isEmpty()))
        { return 1; } 
        else { return 2; }
    }

    // return weather is empty
    public boolean isEmpty() {
        return false;
    }

    // return weather this player will called
    public boolean willCall(String s) {
        if (this.first.name == s)
        { return true; } 
        else {
            return this.rest.willCall(s) || this.rest2.willCall(s);
        }
    }

    // help function for longestCHain
    public int longestChain() {
        return 1 + Math.max(this.rest.longestChain(), this.rest2.longestChain());
    }

}

// represent player
class Player {
    String name;
    String phoneNumber;

    Player(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}

// examples
class Examples {
    Player Jen = new Player("Jen", "12345678");
    Player May = new Player("May", "12345676345678");
    Player Bea = new Player("Bea", "859648");
    Player Kim = new Player("Kim", "784626");
    Player Pat = new Player("Pat", "5784156");
    Player Ann = new Player("Ann", "8496183");
    Player Joy = new Player("Joy", "48562");
    Player Tay = new Player("Tay", "5479861236");
    Player Zoe = new Player("Zoe", "8764262");
    Player Meg = new Player("Meg", "4826536");
    Player Lou = new Player("Lou", "354246382");
    Player Cam = new Player("Cam", "23462556");
    Player Eve = new Player("Eve", "34425635");
    Player Tam = new Player("Tam", "234542552");
    MtLoPhoneChain empty = new MtLoPhoneChain();
    IPhoneChain Tayc = new ConsLoPhoneChain(this.Tay, this.empty, this.empty);
    IPhoneChain Zoec = new ConsLoPhoneChain(this.Zoe, this.empty, this.empty);
    IPhoneChain Megc = new ConsLoPhoneChain(this.Meg, this.empty, this.empty);
    IPhoneChain Louc = new ConsLoPhoneChain(this.Lou, this.empty, this.empty);
    IPhoneChain Kimc = new ConsLoPhoneChain(this.Kim, this.Tayc, this.Zoec);
    IPhoneChain Patc = new ConsLoPhoneChain(this.Pat, this.Megc, this.Louc);
    IPhoneChain Mayc = new ConsLoPhoneChain(this.May, this.Kimc, this.Patc);

    // tests
    boolean testLongestChain1(Tester t) {
        return t.checkExpect(this.Mayc.longestChain(), 3);
    }

    boolean testwillCall1(Tester t) {
        return t.checkExpect(this.Mayc.willCall("Lou"), true);
    }

    boolean testwillCall2(Tester t) {
        return t.checkExpect(this.Mayc.willCall("Tam"), false);
    }

    boolean testcountPlayers(Tester t) {
        return t.checkExpect(this.Mayc.countPlayers(), 7);
    }

    boolean testcountPlayersCalled(Tester t) {
        return t.checkExpect(this.Mayc.countPlayersCalled(), 2);
    }
    
    boolean testcountPlayersCalled2(Tester t) {
        return t.checkExpect(this.Tayc.countPlayersCalled(), 0);
    }
}