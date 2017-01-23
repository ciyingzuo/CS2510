// Assignment 2
// partner1-Ci Yingzuo
// partner1-ciyingzuo

import tester.*;

// represents IData
interface IData {

}



// represents Cell
class Cell {
    int row;
    char column;
    Data data;

    // default constructor
    Cell(int row, char column, Data data) {
        this.column = column;
        this.data = data;
        this.row = row;
    }

    // constructor without data
    Cell(int row, char column) {
        this.row = row;
        this.column = column;
    }

    // return the value of the cell
    public int value() {
        if (this.data.num)
        { return this.data.number; }
        else if (this.data.fun.equals("mul")) 
        { return this.data.first.value() * this.data.second.value(); }
        else if (this.data.fun.equals("sum")) 
        { return this.data.first.value() + this.data.second.value(); }
        else { return this.data.first.value() % this.data.second.value(); }
    }

    // count how many argues in the cell
    public int countArgs() {
        if (this.data.num)
        { return 1; }
        else { return this.data.first.countArgs() + this.data.second.countArgs(); }
    }

    //count how many functions in the cell
    public int countFuns() {
        if (this.data.num)
        { return 0; } 
        else { return 1 + this.data.first.countFuns() + this.data.second.countFuns(); }
    }
    
    // return the depth of formula
    public int formulaDepth() {
        if (this.data.num)
        { return 0; } 
        else {
            return 1 + Math.max(this.data.first.formulaDepth(), this.data.second.formulaDepth());
        }
    }
    
    // return the calculate process of the cell
    public String formula(int depth) {
        if (depth == 0) 
        { return this.column + Integer.toString(this.row); } 
        else if (depth >= 1 && this.data.num) 
        { return Integer.toString(this.data.number); } 
        else {
            return this.data.fun + "(" + this.data.first.formula(depth - 1)
                + ", " + this.data.second.formula(depth - 1) + ")";
        }
    }
}



// represent Data
class Data {
    Cell first;
    Cell second;
    int number; // assign if the data is number
    String fun; // assign if the data contains formula
    boolean num; // if the data is number, true

    // constructor without number
    Data(Cell first, Cell second, String fun) {
        this.first = first;
        this.second = second;
        this.fun = fun;
        this.num = false;
    }

    // constructor only with number
    Data(int number) {
        this.number = number;
        this.fun = null;
        this.num = true;
    }
}



// example cells
class ExamplesExcelCells {
    
    Cell cellA1 = new Cell(1, 'A', new Data(25));
    Cell cellB1 = new Cell(1, 'B', new Data(10));
    Cell cellC1 = new Cell(1, 'C', new Data(1));
    Cell cellD1 = new Cell(1, 'D', new Data(27));
    Cell cellE1 = new Cell(1, 'E', new Data(16));
    Cell cellA3 = new Cell(3, 'A', new Data(this.cellA1, this.cellB1, "mul"));
    Cell cellC2 = new Cell(2, 'C', new Data(this.cellA3, this.cellC1, "sum"));
    Cell cellA2 = new Cell(2, 'A');
    Cell cellB2 = new Cell(2, 'B');
    Cell cellE2 = new Cell(2, 'E', new Data(this.cellE1, this.cellD1, "sum"));
    Cell cellD2 = new Cell(2, 'D', new Data(this.cellC2, this.cellE2, "mod"));
    Cell cellB3 = new Cell(3, 'B', new Data(this.cellE1, this.cellA3, "mod"));
    Cell cellC3 = new Cell(3, 'C');
    Cell cellD3 = new Cell(3, 'D', new Data(this.cellD2, this.cellA1, "mul"));
    Cell cellE3 = new Cell(3, 'E');
    Cell cellA4 = new Cell(4, 'A');
    Cell cellB4 = new Cell(4, 'B');
    Cell cellC4 = new Cell(4, 'C', new Data(this.cellE1, this.cellD1, "mul"));
    Cell cellD4 = new Cell(4, 'D', new Data(this.cellC4, this.cellA1, "sum"));
    Cell cellE4 = new Cell(4, 'E');
    Cell cellB5 = new Cell(5, 'B');
    Cell cellC5 = new Cell(5, 'C', new Data(this.cellD4, this.cellB3, "sum"));
    Cell cellA5 = new Cell(5, 'A', new Data(this.cellD3, this.cellC5, "mod"));
    Cell cellD5 = new Cell(5, 'D');
    Cell cellE5 = new Cell(5, 'E');

    // student cell
    Cell cellA6 = new Cell(6, 'A', new Data(this.cellB1, this.cellE1, "sum"));
    Cell cellB6 = new Cell(6, 'B', new Data(this.cellA1, this.cellC1, "mod"));
    Cell cellC6 = new Cell(6, 'C', new Data(this.cellC1, this.cellB1, "mul"));

    // tests
    boolean testValue1(Tester t) {
        return t.checkExpect(cellA3.value(), 250);
    }

    boolean testValue2(Tester t) {
        return t.checkExpect(cellC5.value(), 473);
    }

    boolean testCountArgs1(Tester t) {
        return t.checkExpect(cellE2.countArgs(), 2);
    }

    boolean testCountArgs2(Tester t) {
        return t.checkExpect(cellC5.countArgs(), 6);
    }

    boolean testCountFuns1(Tester t) {
        return t.checkExpect(cellD2.countFuns(), 4);
    }

    boolean testCountFuns2(Tester t) {
        return t.checkExpect(cellC5.countFuns(), 5);
    }

    boolean testFormulaDepth1(Tester t) {
        return t.checkExpect(cellC5.formulaDepth(), 3);
    }

    boolean testFormulaDepth2(Tester t) {
        return t.checkExpect(cellC4.formulaDepth(), 1);
    }

    boolean testFormula1(Tester t) {
        return t.checkExpect(cellD2.formula(4), "mod(sum(mul(25, 10), 1), sum(16, 27))");
    }

    boolean testFormula2(Tester t) {
        return t.checkExpect(cellD3.formula(1), "mul(D2, A1)");
    }
}