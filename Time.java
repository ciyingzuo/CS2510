// Assignment 4
// Yingzuo Ci
// ciyingzuo

import tester.*;

//represent Time
public class Time {
    int hour;
    int minute;
    int second;

    // constructor with hour minute and second
    Time(int hour, int minute, int second) {
        if (hour <= 23 && hour >= 0) {
            this.hour = hour;
        }
        else {
            throw new IllegalArgumentException("Invalid hour: " + Integer.toString(hour));
        }

        if (minute <= 59 && minute >= 0) {
            this.minute = minute;
        }
        else {
            throw new IllegalArgumentException("Invalid minute: " + Integer.toString(minute));
        }

        if (second <= 59 && second >= 0) {
            this.second = second;
        }
        else {
            throw new IllegalArgumentException("Invalid second: " + Integer.toString(second));
        }
    }

    // constructor with hour and minute, second will be set to zero
    Time(int hour, int minute) {
        this(hour, minute, 0);
    }

    // constructor with hour and minute, also include a boolean to determine
    // if the time is AM or PM, second will be set to zero
    Time(int hour, int minute, boolean isAM) {
        if (!(hour <= 12 && hour >= 1)) {
            throw new IllegalArgumentException("Invalid hour: " + Integer.toString(hour));
        }
        else if ((isAM) && (hour == 12)) {
            this.hour = 0;
        }
        else if (isAM) {
            this.hour = hour;
        }
        else if (hour == 12) {
            this.hour = hour;
        }
        else {
            this.hour = hour + 12;
        }
        this.minute = minute;
        this.second = 0;
    }

    // returns true if two time are same
    public boolean sameTime(Time that) {
        return this.hour == that.hour && this.minute == that.minute && this.second == that.second;
    }
}

class ExampleTime {
    Time t1 = new Time(6, 30, 0);
    Time t2 = new Time(23, 12, 50);
    Time t4 = new Time(23, 12, 50);
    Time t5 = new Time(8, 40);
    Time t6 = new Time(6, 30, true);
    Time t7 = new Time(8, 40, false);

    boolean testSameTime(Tester t) {
        return t.checkExpect(t1.sameTime(t6), true) && t.checkExpect(t5.sameTime(t7), false);
    }

    boolean testConstructor(Tester t) {
        return t.checkConstructorException(
                // the expected exception
                new IllegalArgumentException("Invalid minute: 70"),

                // the *name* of the class (as a String) whose constructor we
                // invoke
                "Time",

                // the arguments for the constructor
                21, 70, 23);
    }
    
    boolean testConstructor2(Tester t) {
        return t.checkConstructorException(
                // the expected exception
                new IllegalArgumentException("Invalid hour: 50"),

                // the *name* of the class (as a String) whose constructor we
                // invoke
                "Time",

                // the arguments for the constructor
                50, 23, 23);
    }
    
    boolean testConstructor3(Tester t) {
        return t.checkConstructorException(
                // the expected exception
                new IllegalArgumentException("Invalid second: 99"),

                // the *name* of the class (as a String) whose constructor we
                // invoke
                "Time",

                // the arguments for the constructor
                21, 20, 99);
    }
}