import tester.*;

interface IData {
    public boolean isNumber();
    public int getNumber();
    public String getFormula();
    public Cell getFirst();
    public Cell getSecond();
}

class Cell {
    int row;
    char column;
    IData data;

    Cell(int row, char column, IData data) {
        this.column = column;
        this.data = data;
        this.row = row;
    }

    Cell() {

    }

    public int value() {
        if (this.data.isNumber() == true) {
            return this.data.getNumber();
        } else if (this.data.getFormula() == "mul") {
            return this.data.getFirst().value() * this.data.getSecond().value();
        } else if (this.data.getFormula() == "sun") {
            return this.data.getFirst().value() + this.data.getSecond().value();
        } else {
            return this.data.getFirst().value() % this.data.getSecond().value();
        }
    }

    public int countArgs() {
        if (this.data.isNumber() == true) {
            return 1;
        } else {
            return this.data.getFirst().countArgs() + this.data.getSecond().countArgs();
        }
    }

    public int countFuns() {
        if (this.data.isNumber() == false) {
            return 0;
        } else {
            return 1 + this.data.getFirst().countFuns() + this.data.getSecond().countFuns();
        }
    }
    
    public int formulaDepth() {
        if (this.data.isNumber() == true) {
            return 0;
        } else {
            return 1 + Math.max(this.data.getFirst().formulaDepth(), this.data.getSecond().formulaDepth());
        }
    }

}

class number implements IData {
    int number;

    number(int number) {
        this.number = number;
    }
    
    public boolean isNumber() {
        return true;
    }

    @Override
    public int getNumber() {
        // TODO Auto-generated method stub
        return this.number;
    }

    @Override
    public String getFormula() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cell getFirst() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cell getSecond() {
        // TODO Auto-generated method stub
        return null;
    }
}

class formula implements IData {
    Cell first;
    Cell second;
    String fun;

    formula(Cell first, Cell second, String fun) {
        this.first = first;
        this.second = second;
        this.fun = fun;
    }
    
    public boolean isNumber() {
        return false;
    }

    @Override
    public int getNumber() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getFormula() {
        // TODO Auto-generated method stub
        return this.fun;
    }

    @Override
    public Cell getFirst() {
        // TODO Auto-generated method stub
        return this.first;
    }

    @Override
    public Cell getSecond() {
        // TODO Auto-generated method stub
        return this.second;
    }
}

class ExamplesExcelCells {
    Cell cellA1 = new Cell(1, 'A', new number(25));
    Cell cellB1 = new Cell(1, 'B', new number(10));
    Cell cellC1 = new Cell(1, 'C', new number(1));
    Cell cellD1 = new Cell(1, 'D', new number(27));
    Cell cellE1 = new Cell(1, 'E', new number(16));
    Cell cellA2 = new Cell();
    Cell cellB2 = new Cell();
    Cell cellC2 = new Cell(2, 'C', new formula(this.cellA3, this.cellC1, "sum"));
    Cell cellD2 = new Cell(2, 'D', new formula(this.cellC2, this.cellE2, "mod"));
    Cell cellE2 = new Cell(2, 'E', new formula(this.cellE1, this.cellD1, "sum"));
    Cell cellA3 = new Cell(3, 'A', new formula(this.cellA1, this.cellB1, "mul"));
    Cell cellB3 = new Cell(3, 'B', new formula(this.cellE1, this.cellA3, "mod"));
    Cell cellC3 = new Cell();
    Cell cellD3 = new Cell(3, 'D', new formula(this.cellD2, this.cellA1, "mul"));
    Cell cellE3 = new Cell();
    Cell cellA4 = new Cell();
    Cell cellB4 = new Cell();
    Cell cellC4 = new Cell(4, 'C', new formula(this.cellE1, this.cellD1, "mul"));
    Cell cellD4 = new Cell(4, 'D', new formula(this.cellC4, this.cellA1, "sum"));
    Cell cellE4 = new Cell();
    Cell cellA5 = new Cell(5, 'A', new formula(this.cellD3, this.cellC5, "mod"));
    Cell cellB5 = new Cell();
    Cell cellC5 = new Cell(5, 'C', new formula(this.cellD4, this.cellB3, "sum"));
    Cell cellD5 = new Cell();
    Cell cellE5 = new Cell();

    Cell cellA6 = new Cell(6, 'A', new formula(this.cellA1, this.cellE2, "sum"));
    Cell cellB6 = new Cell(6, 'B', new formula(this.cellA1, this.cellB1, "mod"));
    Cell cellC6 = new Cell(6, 'C', new formula(this.cellA1, this.cellB1, "mul"));

    boolean test(Tester t) {
        return t.checkExpect(cellA3.value(), 250);
    }

    boolean test2(Tester t) {
        return t.checkExpect(cellD2.countArgs(), 5);
    }
    // boolean test3(Tester t) {
    // return t.checkExpect(cellA6.countFuns(), 2);
    // }
//     boolean test4(Tester t) {
//     return t.checkExpect(cellD2.formulaDepth(), 2);
//     }
}