// Lab 1
// Yingzuo Ci
// Ziyi Zhao

// to represent menu
interface IMenu {

}

// to represent soup
class Soup implements IMenu {
    String name;
    int price;
    boolean vegetarian;

    Soup(String name, int price, boolean vegetarian) {
        this.name = name;
        this.price = price;
        this.vegetarian = vegetarian;
    }
}

// to represent salads
class Salads implements IMenu {
    String name;
    int price;
    boolean vegetarian;
    String dressing;

    Salads(String name, int price, boolean vegetarian, String dressing) {
        this.name = name;
        this.price = price;
        this.vegetarian = vegetarian;
        this.dressing = dressing;
    }
}

// to represent sandwich
class Sandwich implements IMenu {
    String name;
    int price;
    String bread;
    String fill1;
    String fill2;

    Sandwich(String name, int price, String bread, String fill1, String fill2) {
        this.name = name;
        this.price = price;
        this.bread = bread;
        this.fill1 = fill1;
        this.fill2 = fill2;
    }
}

// examples
class Examples {
    Soup soup1 = new Soup("Chicken soup", 500, false);
    Soup soup2 = new Soup("Tomato soup", 500, true);
    Salads salads1 = new Salads("Fruit salads", 200, true, "spicy");
    Salads salads2 = new Salads("Chicken salads", 500, false, "sweet");
    Sandwich sandwich1 = new Sandwich("Chicken sandwich", 800, "white bread", "chicken", "mayo");
    Sandwich sandwich2 = new Sandwich("Tuna sandwich", 1300, "wheat bread", "tuna", "lettuce");
}