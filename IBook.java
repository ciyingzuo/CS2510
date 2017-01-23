// lab4
// Ziyi Zhao
// sophia
// Yingzuo Ci
// ciyingzuo
// to represent Ibook

import tester.*;

public interface IBook {
    // produces the number of days this book is overdue
    int daysOverdue(int i);

    // determine if the book is overdue
    boolean isOverdue(int i);

    // calculate the fine for book if its return in given day
    int computeFine(int i);
}

// to represent same elements of book
abstract class ABook implements IBook {
    String title;
    int dayTaken;

    // constructor reference ABook
    ABook(String title, int dayTaken) {
        this.title = title;
        this.dayTaken = dayTaken;
    }

    public boolean isOverdue(int i) {
        return this.daysOverdue(i) > 0;
    }
}

// to represent a book
class Book extends ABook {
    String author;

    Book(String title, String author, int dayTaken) {
        super(title, dayTaken);
        this.author = author;
    }

    public int daysOverdue(int i) {
        return i - this.dayTaken - 2;
    }

    public int computeFine(int i) {
        if (this.daysOverdue(i) <= 0) {
            return 0;
        }
        else {
            return this.daysOverdue(i) * 10;
        }
    }
}

// to represent RefBook
class RefBook extends ABook {

    RefBook(String title, int dayTaken) {
        super(title, dayTaken);
    }

    public int daysOverdue(int i) {
        return i - this.dayTaken - 2;
    }

    public int computeFine(int i) {
        if (this.daysOverdue(i) <= 0) {
            return 0;
        }
        else {
            return this.daysOverdue(i) * 10;
        }
    }
}

// to represent AudioBook
class AudioBook extends ABook {
    String author;

    AudioBook(String title, String author, int dayTaken) {
        super(title, dayTaken);
        this.author = author;
    }

    public int daysOverdue(int i) {
        return i - this.dayTaken - 14;
    }

    public int computeFine(int i) {
        if (this.daysOverdue(i) <= 0) {
            return 0;
        }
        else {
            return this.daysOverdue(i) * 20;
        }
    }
}

// Examples to represent Books
class ExamplesBooks {
    IBook b1 = new Book("pride", "Tim", 5012);
    IBook b2 = new Book("strong", "Jack", 5011);
    IBook b3 = new Book("prejudice", "Kim", 5013);

    IBook r1 = new RefBook("Todayisgood", 5014);
    IBook r2 = new RefBook("woderfulday", 5015);
    IBook r3 = new RefBook("rainyday", 5016);

    IBook a1 = new AudioBook("Horrid Henry", "Marmite", 5017);
    IBook a2 = new AudioBook("good morning", "Wisly", 5018);
    IBook a3 = new AudioBook("awesome stroy", "Kelly", 5019);
    
    //tests
    boolean test(Tester t) {
        return t.checkExpect(this.b1.daysOverdue(5015), 1) &&
                t.checkExpect(this.b2.isOverdue(5017), true) &&
                t.checkExpect(this.a2.computeFine(5019), 0) &&
                t.checkExpect(this.r2.daysOverdue(5015), -2) &&
                t.checkExpect(this.r3.isOverdue(5016), false) &&
                t.checkExpect(this.b1.computeFine(5015), 10);
    }
}