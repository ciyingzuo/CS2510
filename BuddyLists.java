import tester.*;

//represents a list of Person's buddies
interface ILoBuddy {
    public boolean contains(Person p);

    public int containsh(Person p);

    public ILoBuddy pch(ILoBuddy p);

    public ILoBuddy pchh(ILoBuddy p);

    public ILoBuddy append(ILoBuddy that);

    public ILoBuddy removeSame();

    public int length(int i);

    public int counth(ILoBuddy that);

    public boolean isEmpty();
}

// represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {

    public boolean contains(Person p) {
        return false;
    }

    public int containsh(Person p) {
        return 0;
    }

    public ILoBuddy pch(ILoBuddy p) {
        return new MTLoBuddy();
    }

    public ILoBuddy pchh(ILoBuddy p) {
        return new MTLoBuddy();
    }

    public ILoBuddy append(ILoBuddy that) {
        if (that.isEmpty()) {
            return new MTLoBuddy();
        }
        else {
            return that;
        }
    }

    public ILoBuddy removeSame() {
        return new MTLoBuddy();
    }

    public int length(int i) {
        return i;
    }

    public int counth(ILoBuddy that) {
        return 0;
    }

    public boolean isEmpty() {
        return true;
    }
}

// represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {
    Person first;
    ILoBuddy rest;

    ConsLoBuddy(Person first, ILoBuddy rest) {
        this.first = first;
        this.rest = rest;
    }

    // returns true if the given person is in the list
    public boolean contains(Person p) {
        if (this.first.equals(p)) {
            return true;
        }
        else {
            return this.rest.contains(p);
        }
    }

    public int containsh(Person p) {
        if (this.contains(p)) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public ILoBuddy pch(ILoBuddy p) {
        return new MTLoBuddy();
    }

    // append two list
    public ILoBuddy append(ILoBuddy that) {
        return new ConsLoBuddy(this.first, this.rest.append(that));
    }

    // remove the same person in the list
    public ILoBuddy removeSame() {
        if (this.first.compare(this.rest)) {
            return this.rest.removeSame();
        }
        else {
            return new ConsLoBuddy(this.first, this.rest.removeSame());
        }
    }

    // return the length of a list
    public int length(int i) {
        return this.rest.length(i + 1);
    }

    // helper function for count common buddies
    public int counth(ILoBuddy that) {
        if (this.first.compare(that)) {
            return 1 + this.rest.counth(that);
        }
        else {
            return this.rest.counth(that);
        }
    }

    // returns false because its a list
    public boolean isEmpty() {
        return false;
    }

    // helper function for partycount helper function
    public ILoBuddy pchh(ILoBuddy p) {
        if (p.contains(this.first)) {
            return new MTLoBuddy();
        }
        else {
            return new ConsLoBuddy(this.first,
                    this.first.pch(p)).append(this.rest.pchh(p));
        }
    }
}

// represents a Person with a user name and a list of buddies
class Person {
    String username;
    ILoBuddy buddies;

    Person(String username) {
        this.username = username;
        this.buddies = new MTLoBuddy();
    }

    public boolean compare(ILoBuddy that) {
        if (that.isEmpty()) {
            return false;
        }
        else if (this.equals(((ConsLoBuddy) that).first)) {
            return true;
        }
        else {
            return this.compare(((ConsLoBuddy) that).rest);
        }
    }

    // helper function for partycount
    public ILoBuddy pch(ILoBuddy p) {
        if (this.buddies.isEmpty()) {
            return new ConsLoBuddy(this, new MTLoBuddy());
        }
        else if (p.contains(this)) {
            return new MTLoBuddy();
        }
        else {
            return new ConsLoBuddy(this, this.buddies).append(
                    this.buddies.pchh(new ConsLoBuddy(this, p)));
        }
    }

    // add a buddy to a person
    public void addBuddy(Person p) {
        this.buddies = new ConsLoBuddy(p, this.buddies);
    }

    // returns true if this Person has that as a direct buddy
    public boolean hasDirectBuddy(Person that) {
        if (this.buddies.isEmpty()) {
            return false;
        }
        else {
            return this.buddies.contains(that);
        }
    }

    // returns the number of people who will show up at the party
    // given by this person
    public int partyCount() {
        return this.pch(new MTLoBuddy()).removeSame().length(0);
    }

    // returns the number of people that are direct buddies
    // of both this and that person
    public int countCommonBuddies(Person that) {
        return this.buddies.counth(that.buddies);
    }

    // will the given person be invited to a party
    // organized by this person?
    public boolean hasExtendedBuddy(Person that) {
        return that.compare(this.pch(new MTLoBuddy()));
    }
}