import java.util.ArrayList;
import java.util.Iterator;

public class MachinePlayer extends Player{
    private Color color;
    private Checker [] myCheckers = new Checker[12];
    private Board board;

    public MachinePlayer(Color color,Board boardOG) {
        super(color);
        this.color = color;
        this.board = new Board(boardOG);
        this.myCheckers = boardOG.getPlayerByColor(color).getMyCheckers();

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
        for (Checker[] checkerArray: board.getCheckers()) {
            for (Checker c: checkerArray) {
                if (c.getColor() == color) {
                    Position position = new Position(c.getPosition());
                    computer.add(position);
                } else if (c.getColor()!=color) {
                    Position position = new Position(c.getPosition());
                    human.add(position);
                }
            }
        }
        int comp = distance(computer);
        int hum = distance(human);
        return hum - comp;
    }
    int distance(ArrayList<Position> pieces) {
        double twopiecedist;
        int sum = 0;
        double x1;
        double y1;
        for (Position positionI:pieces) {
            for (Position positionJ:pieces) {
                x1 = Math.pow(positionI.getX()-positionJ.getX(), 2);
                y1 = Math.pow(positionI.getY()-positionJ.getY(), 2);
                twopiecedist =
                        Math.sqrt(x1 + y1);
                sum += Math.round(twopiecedist);
            }
        }
        return sum;
    }
  public Position minmax(Color Color, int depth, int alpha, int beta) {
        Position best = null;
        int bestmove = Integer.MIN_VALUE;
        int current = Integer.MIN_VALUE;
        ArrayList<LinkedList> list = new ArrayList<LinkedList>() ;
      for (Checker[] checkerArray: board.getCheckers()) {
          for (Checker c: checkerArray) {
              if (c!=null)
                  list.add(board.canGo(c));
          }
          }
        while (!list.isEmpty()) {
            Move next = iter.next();
            copy.makeMove(next);
            current =
                    alphabeta(side.opponent(), copy, alpha, beta, depth - 1);
            if (current > bestmove) {
                best = next;
                bestmove = current;
            }
            copy.retract();
        }
        return best;
    }

}
