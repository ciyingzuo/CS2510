// Assignment 1
// Ci Yingzuo
// ciyingzuo


interface ITaco {

}

// to represent a EmptyShell
class EmptyShell implements ITaco {
    boolean softShell;
    
// constructor for EmptyShell
    EmptyShell(boolean softShell) {
        this.softShell = softShell;
    }
}


// to represent fill
class Filled implements ITaco {
    ITaco taco;
    String filling;
    
 // constructor for fill
    Filled(ITaco taco, String filling) {
        this.taco = taco;
        this.filling = filling;
    }
}


// examples for Taco
class ExamplesTaco {
    ITaco softShell = new EmptyShell(false);
    ITaco hardShell = new EmptyShell(true);
    
    ITaco order1 = new Filled(new Filled(new Filled(new Filled(this.softShell, 
            "cheddar cheese"), "lettuce"), "salsa"), "carnitas");
    ITaco order2 = new Filled(new Filled(new Filled(this.softShell, 
            "sour cream"), "guacamole"), "veggies");
}