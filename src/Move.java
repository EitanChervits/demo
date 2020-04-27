public class Move {
    private Checker checker;
    private Position position;

    public Move(Checker checker, Position position) {
        this.checker = checker;
        this.position = position;
    }

    public Checker getChecker() {
        return checker;
    }

    public void setChecker(Checker checker) {
        this.checker = checker;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
