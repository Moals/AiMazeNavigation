

public class Node {
    public int f;
    public int g;
    public int h;
    public Node ancestor;
    private int x;
    private int y;
    

    public Node() {
    }
    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }
    
    public void setF(int g, int h) {
        this.f = g+h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    

    public Node getAncestor() {
        return ancestor;
    }

    public void setAncestor(Node ancestor) {
        this.ancestor = ancestor;
    }
	public int getX() {
		return x;
	}
	public void setX(int x1) {
		this.x = x1;
	}
	public int getY() {
		return y;
	}
	public void setY(int y1) {
		this.y = y1;
	}

    

}