package engine;

import java.util.ArrayList;

public class MachinePlayer extends Player {

    private Board board;

    public MachinePlayer(Color color, Board boardOG) {
        super(color);
        this.board = new Board(boardOG);
        this.myCheckers = this.board.getPlayerByColor(color).getMyCheckers();

    }

    int eval(Board game) {
        if (game.piecesContiguous(board.getPlayerByColor(color))) {
            return Integer.MAX_VALUE;//returns the value of +infinity
        }
        if (game.piecesContiguous(board.getOpponent(color))) {
            return Integer.MIN_VALUE;//returns the value of -infinity
        }
        ArrayList<Position> human = board.getPlayerByColor(color).getPlayerCheckersPositions();
        ArrayList<Position> computer = board.getOpponent(color).getPlayerCheckersPositions();
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
    public Move minmax( int depth, int alpha, int beta) {
        Move move = null;
        int bestmove = Integer.MIN_VALUE;
        int current = Integer.MIN_VALUE;
        ArrayList<Position> list = null;
        Board gameCopy = new Board(board);
        for (Checker c : board.getPlayerByColor(color).getMyCheckers()) {
                list = board.canGo(c);//list of possible positions this checker can go to
            for (Position p:list) {
                gameCopy.makeAMove(c,p);
                current = alphabeta(color.opponent(), gameCopy, alpha, beta, depth - 1);
                //if a position can lead the bot to winning faster its marked as the best position
                if (current > bestmove) {
                    bestmove = current;
                    move = new Move(c,p);
                }
            }
            gameCopy = new Board(board);
        }
        return move;
    }
//returns a evaluation for a game senario few staps a head each time to be ahead of the human player
//and to make the best moves that will lead the bot to victory faster
// the best game scenario will get the highest score which means by making this move the bot is more likely to win
    int alphabeta(Color clr, Board board, int alpha, int beta, int depth) {

        if (board.GameOver() != null || depth <= 0) {
            return eval(board);
        }
        int score;
        Board gameCopy = new Board(board);
        ArrayList<Board> state = new ArrayList<>();
        ArrayList<Position> list = new ArrayList<Position>();
        for (Checker c : board.getPlayerByColor(clr).getMyCheckers()) {
            if (c != null)
                list = board.canGo(c);
            for (Position p: list) {//collects all the possible moves for each checker and its effect on the game
                gameCopy.makeAMove(c,p);
                state.add(gameCopy);
            }
        }
        // each game scenario is send through got get his next best move, but for the human player the function
        // takes the worst case because the goal for the bot is to win the the human player to lose
            if (board.getPlayerByColor(clr) == board.getPlayerByColor(color)) {
                for (Board b : state) {
                    score = alphabeta(board.getOpponent(clr).getColor(), b, alpha, beta, depth - 1);
                    if (score > alpha) {// each score is evaluated and only the highest score for each game option is returned
                        alpha = score;
                    }
                    if (alpha >= beta) {
                        return alpha;
                    }
                }
                return alpha;
            } else {
                for (Board b : state) {
                    score = alphabeta(board.getOpponent(clr).getColor(), b, alpha, beta, depth - 1);
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
    public Move makeMove(Board board){
        return minmax(3,Integer.MAX_VALUE,Integer.MIN_VALUE);
    }
}
