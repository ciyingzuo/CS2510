import tester.*;

public class Final02 {

}

// to represent a pet owner
class Person {
    String name;
    APet pet;
    int age;

    Person(String name, APet pet, int age) {
        this.name = name;
        this.pet = pet;
        this.age = age;
    }

    public boolean Compart(String a) {
        return this.pet.name.compareTo(a) == 0;
    }

    public Person perish() {
        return new Person(this.name, new NoPet(this.pet.kind, this.pet.name, this.pet.owner), this.age);
    }
}

abstract class APet {
    String name;
    String kind;
    Person owner;

    APet(String name, String kind, Person owner) {
        this.name = name;
        this.kind = kind;
        this.owner = owner;
    }

}

// to represent a pet cat
class Cat extends APet {
    boolean longhaired;

    Cat(String name, String kind, Person owner, boolean longhaired) {
        super(name, kind, owner);
        this.longhaired = longhaired;
    }
}

// to represent a pet dog
class Dog extends APet {
    boolean male;

    Dog(String name, String kind, Person owner, boolean male) {
        super(name, kind, owner);
        this.male = male;
    }
}

class NoPet extends APet {

    NoPet(String name, String kind, Person owner) {
        super(name, kind, owner);
    }
}

class example {
    Person a = new Person("A", null, 12);
    APet ap = new Cat("AP", "C", a, true);

    void n() {
        a.pet = ap;
    }

    boolean test(Tester t) {
        n();
        return t.checkExpect(a.Compart("AP"), true);
    }
}
