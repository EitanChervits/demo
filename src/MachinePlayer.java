import java.util.ArrayList;

public class MachinePlayer extends Player {

    private Board board;

    public MachinePlayer(Color color, Board boardOG) {
        super(color);
        this.board = new Board(boardOG);

    }

    public void setBoard(Board board) {
        this.board = board;
    }

    int eval(Board game) {
        if (game.piecesContiguous(board.getPlayerByColor(color))) {
            return Integer.MAX_VALUE;//returns the value of +infinity
        }
        if (game.piecesContiguous(board.getopponent(color))) {
            return Integer.MIN_VALUE;//returns the value of -infinity
        }
        ArrayList<Position> human = board.getPlayerByColor(color).getPlayerCheckersPositions();
        ArrayList<Position> computer = board.getopponent(color).getPlayerCheckersPositions();
        int comp = distance(computer);
        int hum = distance(human);
        return hum - comp;
    }
//calculates the distance between all the checkers of a certain type
    int distance(ArrayList<Position> pieces) {
        double twopiecedist;
        int sum = 0;
        double x1;
        double y1;
        for (Position positionI : pieces) {
            for (Position positionJ : pieces) {
                x1 = Math.pow(positionI.getX() - positionJ.getX(), 2);
                y1 = Math.pow(positionI.getY() - positionJ.getY(), 2);
                twopiecedist = Math.sqrt(x1 + y1);
                sum += Math.round(twopiecedist);
            }
        }
        return sum;
    }
// demonstrates a scenario of a possible player move, sends it to alpha beta for
// evaluation of a possible chance for victory few moves down
    public Move minmax(Color Color, int depth, int alpha, int beta) {
        Move move = null;
        int bestmove = Integer.MIN_VALUE;
        int current = Integer.MIN_VALUE;
        ArrayList<Position> list = null;
        Game gameCopy = new Game(board, color);
        for (Checker c : board.getPlayerByColor(color).getMyCheckers()) {
                list = board.canGo(c);//list of possible positions this checker can go to
            for (Position p:list) {
                gameCopy.makeAMove(c,p);
                current = alphabeta(color, gameCopy, alpha, beta, depth - 1);
                //if a position can lead the bot to winning faster its marked as the best position
                if (current > bestmove) {
                    bestmove = current;
                    move = new Move(c,p);
                }
                gameCopy.retract(board);//takes the board to his last version
            }
        }
        return move;
    }
//returns a evaluation for a game senario few staps a head each time to be ahead of the human player
//and to make the best moves that will lead the bot to victory faster
// the best game scenario will get the highest score which means by making this move the bot is more likely to win
    int alphabeta(Color color, Game game, int alpha, int beta, int depth) {

        if (game.GameOver() != null || depth <= 0) {
            return eval(board);
        }
        int score;
        Game gameCopy = new Game(board, color);
        ArrayList<Game> state = new ArrayList<Game>();
        ArrayList<Position> list = new ArrayList<Position>();
        for (Checker c : board.getPlayerByColor(color).getMyCheckers()) {
            if (c != null)
                list = board.canGo(c);
            for (Position p: list) {//collects all the possible moves for each checker and its effect on the game
                gameCopy.makeAMove(c,p);
                state.add(gameCopy);
            }
        }
        // each game scenario is send through got get his next best move, but for the human player the function
        // takes the worst case because the goal for the bot is to win the the human player to lose
            if (board.getPlayerByColor(color) == board.getPlayerByColor(this.color)) {
                for (Game b : state) {
                    score = alphabeta(board.getopponent(color).getColor(), b, alpha, beta, depth - 1);
                    if (score > alpha) {// each score is evaluated and only the highest score for each game option is returned
                        alpha = score;
                    }
                    if (alpha >= beta) {
                        return alpha;
                    }
                }
                return alpha;
            } else {
                for (Game b : state) {
                    score = alphabeta(board.getopponent(color).getColor(), b, alpha, beta, depth - 1);
                    if (score < beta) {// for the human player the lowest score  is returned
                        beta = score;
                    }
                    if (alpha >= beta) {
                        return beta;
                    }
                }

            }
            return beta;
    }
}
