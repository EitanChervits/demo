

public class Checker {
    private Position position;
    private Color color;
    private int touch;
    private boolean checked;
    public Checker() {

    }

    public Checker(Color color, int x,int y) {
        this.position.setX(x);
        this.position.setY(y);
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
                /* + "\nIt's Position on Board : " + getPosition().draw() */;
    }
    //tells if a cell is a border cell
    public boolean cellIsBorder(){
        if(position.getX()==0||position.getY()==0||position.getX()==7||position.getY()==7)
            return true;
        return false;
    }


}




