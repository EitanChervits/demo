public class Game extends Board {
    private Checker[][] checkers = new Checker[8][8];
    private Color turn;

    public Game(Board board, Color color) {
        super(board);
        this.turn = color;
        retract(board);
    }

    public void retract(Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getCheckers()[i][j] != null) {
                    checkers[i][j].setChecked(board.getCheckers()[i][j].isChecked());
                    checkers[i][j].setColor(board.getCheckers()[i][j].getColor());
                    checkers[i][j].setPosition(board.getCheckers()[i][j].getPosition());
                }
            }
        }
    }

    @Override
    protected void makeAMove(Checker checker, Position position) {
        super.makeAMove(checker, position);
    }

    @Override
    protected Color GameOver() {
        return super.GameOver();
    }
}



