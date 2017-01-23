// Assignment 1
// Ci Yingzuo
// ciyingzuo



// to represent a Person
public class Person {
    String name;
    int yob;
    String state;
    boolean citizen;
    
// constructor for person
    Person(String name, int yob, String state, boolean citizen) {
        this.name = name;
        this.yob = yob;
        this.state = state;
        this.citizen = citizen;
    }
}

// examples for Person
class ExamplesPerson {
    Person simone = new Person("Simone Manuel", 1996, "TX", true);
    Person marty = new Person("Marty Walsh", 1967, "MA", true);
    Person drake = new Person("Drake", 1996, "MA", false);
}