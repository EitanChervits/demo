package engine;

public class Checker {
    private Position position;
    private Color color;
    private boolean checked;
    public Checker() {

    }

    public Checker(Color color, int x,int y) {
      this.position= new Position(x,y);
        this.color = color;
        this.checked = false;
    }
    public Checker(Color color, Position position) {
        this.position = position;
        this.color = color;
        this.checked = false;
    }

    // getters
    public Color getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isChecked() {
        return checked;
    }

    // setters
    public void setPosition(Position p) {
        position = p;
    }

    public void setColor(Color c) {
        color = c;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String toString() {
        return getColor() + "---"
                /* + "\nIt's engine.Position on engine.Board : " + getPosition().draw() */;
    }
}



