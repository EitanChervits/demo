import java.util.ArrayList;
import java.util.Iterator;

public class MachinePlayer extends Player {
    private Color color; // not needed
    private Checker[] myCheckers = new Checker[12]; // not needed
    private Board board;

    public MachinePlayer(Color color, Board boardOG) {
        super(color);
        this.color = color; // not needed - the super does that
        this.board = new Board(boardOG);
        this.myCheckers = boardOG.getPlayerByColor(color).getMyCheckers(); // not needed

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
        // in the previous 2 lines - the Position in the right hand side is redundant
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
        // regarding the for above: consider adding a method to Player to get his
        // checker's positions, something like:
        // ArrayList<Position> positions = new ArrayList<>();
        // for (Checker c : getMyCheckers()) {
        //      positions.add(c.getPosition())
        // }
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
                        Math.sqrt(x1 + y1); // the new line isn't needed, move the line above
                sum += Math.round(twopiecedist);
            }
        }
        return sum;
    }
// demonstrates a scenario of a possible player move, sends it to alpha beta for
// evaluation of a possible chance for victory few moves down
    public Position minmax(Color Color, int depth, int alpha, int beta) {
        Position best = null;
        // pay attention - you keep only the best poistion to go to, but not the checker which should go
        // there - how could you preform this move afterwards? consider creating a Move class anyway.
        int bestmove = Integer.MIN_VALUE;
        int current = Integer.MIN_VALUE;
        ArrayList<Position> list = null;
        Game gameCopy = new Game(board, color); // the copy constructor still isn't good
        // regarding the next line - this should always be the current user, you should call getMyCheckers()
        // and that's it
        for (Checker c : board.getPlayerByColor(color).getMyCheckers()) {
            int index = 0;
            if (c != null)
                // c shouldn't be null in this array - please make sure that your implementation
                //so far promises that
                list = board.canGo(c);//list of possible positions this checker can go to
            while (!list.isEmpty()) {
                // pay attention - will the list get empty sometime?
                // you could use for (Position p : list) instead, and remove the index variable
                gameCopy.makeAMove(c, list.get(index));
                current =
                        alphabeta(color, gameCopy, alpha, beta, depth - 1); // again - remove new line
                // regarding the above line - it should be the opponent - he is the one who'll "play"
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
        // in the previous 2 lines - the Position in the right hand side is redundant
        for (Checker c : board.getPlayerByColor(color).getMyCheckers()) {
            int index = 0;//to move between each possible position in the list
            if (c != null)
                list = board.canGo(c); // like in minmax
            while (!list.isEmpty()) {//collects all the possible moves for each checker and its effect on the game
                gameCopy.makeAMove(c, list.get(index));
                state.add(gameCopy);
            }
        }
        // each game scenario is send through got get his next best move, but for the human player the function
        // takes the worst case because the goal for the bot is to win the the human player to lose
            if (board.getPlayerByColor(color) == board.getPlayerByColor(this.color)) {
                // it isn't good that color is both a field and a local variable, rename it in this method
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

    // makeMove should be void, and it should call board's/game's makeMove. In addition, makeMove should
    // be an abstract function in Player, and should be implemented according to the player's specifications
    public Position makeMove(Board board, int depth){
        setBoard(board);
        return minmax(this.color,depth,Integer.MAX_VALUE,Integer.MIN_VALUE);
    }
}
