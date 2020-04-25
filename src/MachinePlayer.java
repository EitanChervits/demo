import java.util.ArrayList;
import java.util.Iterator;

public class MachinePlayer extends Player {
    private Color color;
    private Checker[] myCheckers = new Checker[12];
    private Board board;

    public MachinePlayer(Color color, Board boardOG) {
        super(color);
        this.color = color;
        this.board = new Board(boardOG);
        this.myCheckers = boardOG.getPlayerByColor(color).getMyCheckers();

    }

    public void setBoard(Board board) {
        this.board = board;
    }

    int eval(Board game) {
        if (game.piecesContiguous(board.getPlayerByColor(color))) {
            return Integer.MAX_VALUE;//returns the value of +infinity
        }
        if (game.piecesContiguous(board.getOpponetn(color))) {
            return Integer.MIN_VALUE;//returns the value of -infinity
        }
        ArrayList<Position> human = new ArrayList<Position>();
        ArrayList<Position> computer = new ArrayList<Position>();
        for (Checker[] checkerArray : board.getCheckers()) {
            for (Checker c : checkerArray) {
                if (c.getColor() == color) {
                    Position position = new Position(c.getPosition());
                    computer.add(position);
                } else if (c.getColor() != color) {
                    Position position = new Position(c.getPosition());
                    human.add(position);
                }
            }
        }
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
                twopiecedist =
                        Math.sqrt(x1 + y1);
                sum += Math.round(twopiecedist);
            }
        }
        return sum;
    }
// demonstrates a scenario of a possible player move, sends it to alpha beta for
// evaluation of a possible chance for victory few moves down
    public Position minmax(Color Color, int depth, int alpha, int beta) {
        Position best = null;
        int bestmove = Integer.MIN_VALUE;
        int current = Integer.MIN_VALUE;
        ArrayList<Position> list = null;
        Game gameCopy = new Game(board, color);
        for (Checker c : board.getPlayerByColor(color).getMyCheckers()) {
            int index = 0;
            if (c != null)
                list = board.canGo(c);//list of possible positions this checker can go to
            while (!list.isEmpty()) {
                gameCopy.makeAMove(c, list.get(index));
                current =
                        alphabeta(color, gameCopy, alpha, beta, depth - 1);
                //if a position can lead the bot to winning faster its marked as the best position
                if (current > bestmove) {
                    best = list.get(index);
                    bestmove = current;
                }
                gameCopy.retract(board);//takes the board to his last version
                index++;
            }
        }
        return best;
    }
//returns a evaluation for a game senario few staps a head each time to be ahead of the human player
//and to make the best moves that will lead the bot to victory faster
// the best game senario will get the highest score which means by making this move the bot is more likely to win
    int alphabeta(Color color, Game game, int alpha, int beta, int depth) {

        if (game.GameOver() != null || depth <= 0) {
            return eval(board);
        }
        int score;
        Game gameCopy = new Game(board, color);
        ArrayList<Game> state = new ArrayList<Game>();
        ArrayList<Position> list = new ArrayList<Position>();
        for (Checker c : board.getPlayerByColor(color).getMyCheckers()) {
            int index = 0;//to move between each possible position in the list
            if (c != null)
                list = board.canGo(c);
            while (!list.isEmpty()) {//collects all the possible moves for each checker and its effect on the game
                gameCopy.makeAMove(c, list.get(index));
                state.add(gameCopy);
            }
        }
        // each game scenario is send through got get his next best move, but for the human player the function
        // takes the worst case because the goal for the bot is to win the the human player to lose
            if (board.getPlayerByColor(color) == board.getPlayerByColor(this.color)) {
                for (Game b : state) {
                    score = alphabeta(board.getOpponetn(color).getColor(), b, alpha, beta, depth - 1);
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
                    score =
                            alphabeta(board.getOpponetn(color).getColor(), b, alpha, beta, depth - 1);
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
    public Position makeMove(Board board, int depth){
        setBoard(board);
        return minmax(this.color,depth,Integer.MAX_VALUE,Integer.MIN_VALUE);
    }
}
