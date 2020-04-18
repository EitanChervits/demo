
public class Position {
    private int x;
    private int y;

    public Position() {
        x = 0;
        y = 0;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void move(int xDelta, int yDelta) {
        x += xDelta;
        y += yDelta;
    }

    public String draw() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Position p) {
        if (this.getX() == p.getX() && this.getY() == p.getY()) {
            return 0;
        }
        return 1;
    }
}
