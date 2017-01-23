import tester.Tester;

// Represents a bank account
public abstract class Account {

    int accountNum; // Must be unique
    int balance; // Must remain above zero (others Accts have more restrictions)
    String name; // Name on the account

    public Account(int accountNum, int balance, String name) {
        this.accountNum = accountNum;
        this.balance = balance;
        this.name = name;
    }

    public boolean same(Account that) {
        if ((this.isChecking() && (!that.isChecking())) ||
                (!(this.isChecking()) && (that.isChecking()))) {
            return false;
        }
        else if (this.isChecking()) {
            return this.sameChecking((Checking) that);
        }
        else {
            return this.sameSavings((Savings) that);
        }
    }

    public boolean sameChecking(Checking that) {
        return this.accountNum == that.accountNum && this.balance == that.balance
                && ((Checking) this).minimum == that.minimum && this.name == that.name;
    }

    public boolean sameSavings(Savings that) {
        return this.accountNum == that.accountNum && this.balance == that.balance
                && ((Savings) this).interest == that.interest && this.name == that.name;
    }

    public abstract boolean isSavings();

    public abstract boolean isChecking();

    public Checking asChecking() {
        if (this.isChecking()) {
            return new Checking(this.accountNum, this.balance,
                    this.name, ((Checking) this).minimum);
        }
        else {
            throw new ClassCastException("Invalid Cast");
        }
    }

    public Savings asSavings() {
        if (this.isSavings()) {
            return new Savings(this.accountNum, this.balance,
                    this.name, ((Savings) this).interest);
        }
        else {
            throw new ClassCastException("Invalid Cast");
        }
    }

    // produce the amount available for withdrawal from this account
    public abstract int amtAvailable();
}

// Represents a checking account
class Checking extends Account {

    int minimum; // The minimum account balance allowed

    public Checking(int accountNum, int balance, String name, int minimum) {
        super(accountNum, balance, name);
        this.minimum = minimum;
    }

    /*
     * TEMPLATE: Fields: ... this.accountNum ... -- int ... this.balance ... --
     * int ... this.name ... -- String ... this.minimum ... -- int
     * 
     * Methods: ... this.amtAvailable() ... -- int
     * 
     */

    // produce the amount available for withdrawal from this account
    public int amtAvailable() {
        return this.balance - this.minimum;
    }

    public boolean isChecking() {
        return true;
    }

    public boolean isSavings() {
        return false;
    }

    public boolean sameChecking(Checking that) {
        return this.accountNum == that.accountNum &&
                this.balance == that.balance && this.minimum == that.minimum
                && this.name == that.name;
    }
}

// Represents a savings account
class Savings extends Account {

    double interest; // The interest rate

    public Savings(int accountNum, int balance, String name, double interest) {
        super(accountNum, balance, name);
        this.interest = interest;
    }

    /*
     * TEMPLATE: Fields: ... this.accountNum ... -- int ... this.balance ... --
     * int ... this.name ... -- String ... this.interest ... -- double
     * 
     * Methods: ... this.amtAvailable() ... -- int
     * 
     */

    // produce the amount available for withdrawal from this account
    public int amtAvailable() {
        return this.balance;
    }

    public boolean isChecking() {
        return false;
    }

    public boolean isSavings() {
        return true;
    }

    public boolean sameSavings(Savings that) {
        return this.accountNum == that.accountNum &&
                this.balance == that.balance && this.interest == that.interest
                && this.name == that.name;
    }
}

// Bank Account Examples and Tests
class ExamplesBanking {

    Account check0 = new Checking(1, 100, "First Checking Account", 20);
    Account check1 = new Checking(1, 100, "First Checking Account", 20);
    Account savings1 = new Savings(4, 200, "First Savings Account", 2.5);

    // Tests the exceptions we expect to be thrown when
    // performing an "illegal" action.
    public boolean testAmtAvailable(Tester t) {
        return t.checkExpect(this.check1.amtAvailable(), 80) &&
                t.checkExpect(this.savings1.amtAvailable(), 200);
    }

    public boolean testSame(Tester t) {
        return t.checkExpect(this.check0.same(check1), true);
    }
}
