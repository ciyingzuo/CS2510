
public interface ITree {
    boolean leftLeaning();
    int size();
    ITree getLeft();
    ITree getRight();
}

class Leaf implements ITree {

    @Override
    public boolean leftLeaning() {
        return false;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public ITree getLeft() {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public ITree getRight() {
        // TODO Auto-generated method stub
        return this;
    }
}

class Branch implements ITree {
    ITree left, right;

    Branch(ITree left, ITree right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean leftLeaning() {
        return (left.size() >= right.size()) && (left.getLeft().leftLeaning());
    }

    @Override
    public int size() {
        return left.size() + right.size() + 1;
    }

    @Override
    public ITree getLeft() {
        // TODO Auto-generated method stub
        return this.left;
    }

    @Override
    public ITree getRight() {
        // TODO Auto-generated method stub
        return this.right;
    }
}