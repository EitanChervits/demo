package engine;

import java.util.ArrayList;
import java.util.Stack;
 public class Board {
    private Checker[][] checkers = new Checker[8][8];
    private Player white;
    private Player black;
    private Color turn;

    public Board() {
        white = new Player(Color.WHITE);
        black = new Player(Color.BLACK);
        turn = Color.WHITE;
        setInitCheckers();
    }
    public Board(Board copy) {
        this.white = copy.white;
        this.black = copy.black;
        this.turn = copy.getTurn();
        this.checkers = copy.checkers;
        setInitCheckers();
    }

    // getters
    public Checker[][] getCheckers() {
        return checkers;
    }

    public Player getWhitePlayer() {
        return white;
    }

    public Player getBlackPlayer() {
        return black;
    }

    public Checker getCheckerAt(Position p) {
        return getCheckers()[p.getX()][p.getY()];
    }

    public Color getTurn() {
        return turn;
    }
    public void changeTurn(){
        turn = turn.opponent();
    }
    public Player getPlayerByColor(Color color){return color==Color.WHITE?white:black;}
    public Player getopponent(Color color){return color==Color.WHITE?black:white;}
    public Player getCurrentPlayer(){
        return getTurn() == Color.WHITE ? getWhitePlayer() : getBlackPlayer();
    }

    // setters
    public void setCheckers(Checker[][] c) {
        checkers = c;
    }

    public void setWhitePlayer(Player p) {
        white = p;
    }

    public void setBlackPlayer(Player p) {
        black = p;
    }

    public void setTurn(Color turn) {
        this.turn = turn;
    }

    /*the function generates position and color for all the cells on the board;*/
    public void setInitCheckers() {
        // I want you to do this fix by yourself - pay attention to that: you create only
        // one new instance of engine.Position and keep changing it's fields, which means all checkers
        // will have the same position by the end.
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if ((i == 0 || i == 7) && (j != 0 && j != 7))
                    checkers[i][j] = new Checker(Color.BLACK,i,j);//sets all the black cells
                else if ((i != 0 && i != 7) && (j == 0 || j == 7))
                    checkers[i][j] = new Checker(Color.WHITE,i,j);
            }
        }
    }

    public void setPlayersCheckers() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (checkers[i][j].getColor() == Color.WHITE)
                    white.addChecker(checkers[i][j]);
                else if (checkers[i][j].getColor() == Color.BLACK)
                    black.addChecker(checkers[i][j]);
            }
        }
    }

    public int countUpDown(Checker c) {
        int x = c.getPosition().getX();
        int y = c.getPosition().getY();
        int counter = 0;
        // number of checkers in up and down direction
        for (int i = 0; i < 8; i++) {
            if (checkers[i][y]!= null)
                counter++;
        }
        return counter;
    }

    public int countLeftRight(Checker c) {
        int x = c.getPosition().getX();
        int y = c.getPosition().getY();
        int counter = 0;
        for (int i = counter = 0; i < 8; i++) {
            if (checkers[x][i] != null)
                counter++;
        }
        return counter;
    }

    public int countTopLeftToBottomRight(Checker c) {
        int x = c.getPosition().getX();
        int y = c.getPosition().getY();
        int counter = 0;
        if (x >= y) {
            for (int i = x - y, j = counter = 0; i < 8; j++, i++) {
                if (checkers[i][j]!= null)
                    counter++;
            }
        } else if (y > x) {
            for (int i = counter = 0, j = y - x; j < 8; j++, i++) {
                if (checkers[i][j]!= null)
                    counter++;
            }
        }
        return counter;
    }

    public int countTopRightToBottomLeft(Checker c) {
        int x = c.getPosition().getX();
        int y = c.getPosition().getY();
        int counter = 0;
        if (x + y <= 7) {
            for (int i = 0, j = x + y; i <= x + y; i++, j--) {
                if (checkers[i][j]!= null)
                    counter++;
            }
        } else if (x + y > 7) {
            for (int i = 7 - y, j = 7; i <= 7; i++, j--) {
                if (checkers[i][j]!= null)
                    counter++;
            }
        }
        return counter;
    }

    public ArrayList<Position> canGo(Checker c) {
        int x = c.getPosition().getX();
        int y = c.getPosition().getY();
        int counter = countUpDown(c);
       ArrayList<Position> list= new ArrayList<Position>();

        //can go up and down
		/* 7	0	1
		   6	cell 2
		   5     4    3
		 */
        if (x + counter < 8 && checkers[x + counter][y].getColor() != c.getColor()){
            Position position = new Position(x+counter,y);
            list.add(position);
        }
        ;//the cell can go in that direction(down)
        if (x - counter >= 0 && checkers[x - counter][y].getColor() != c.getColor()){
            Position position = new Position(x-counter,y+counter);
            list.add(position);
        }
        counter = countLeftRight(c);
        if (y + counter < 8 && checkers[x][y + counter].getColor() != c.getColor()){
            Position position = new Position(x,y+counter);
            list.add(position);
        }

        if (y - counter >= 0 && checkers[x][y - counter].getColor() != c.getColor()){
            Position position = new Position(x,y-counter);
            list.add(position);
        }
            //the cell can go in that direction(left)
        //from up left to bottom right
        counter = countTopLeftToBottomRight(c);
        if (y + counter < 8 && x + counter < 8 && checkers[x + counter][y + counter].getColor() != c.getColor()){
            Position position = new Position(x+counter,y+counter);
            list.add(position);
        }
            //bottom right
        if (y - counter < 8 && x - counter < 8 && checkers[x - counter][y - counter].getColor() != c.getColor()){
            Position position = new Position(x-counter,y-counter);
            list.add(position);
        }
            //bottom right
        //from up right to bottom left
        counter = countTopRightToBottomLeft(c);
        if (y + counter < 8 && x - counter < 8 && checkers[x - counter][y + counter].getColor() != c.getColor()){
            Position position = new Position(x-counter,y+counter);
            list.add(position);
        }
            //top right
        if (y - counter < 8 && x + counter < 8 && checkers[x + counter][y - counter].getColor() != c.getColor()){
            Position position = new Position(x+counter,y-counter);
            list.add(position);
        }
            //bottom left

        return list;

    }


    //c is the cell in the area of the cell we are testing
//the function receives a direction, and a checker and returns the checker in that specific
    private Checker whichCell(int i, Checker checker) {
        int x = checker.getPosition().getX();
        int y = checker.getPosition().getY();
        boolean notTopBrdr = x > 0;
        boolean notBtmBrdr = x < 7;
        boolean notLftBrdr = y > 0;
        boolean notRgtBrdr = y < 7;

        return (i == 0 && notTopBrdr && notLftBrdr) ? checkers[x - 1][y - 1] :
                (i == 1 && notTopBrdr) ? checkers[x - 1][y] :
                (i == 2 && notTopBrdr && notRgtBrdr) ? checkers[x - 1][y + 1] :
                (i == 3 && notLftBrdr) ? checkers[x][y - 1] :
                (i == 4 && notRgtBrdr) ? checkers[x][y + 1] :
                (i == 5 && notBtmBrdr && notLftBrdr) ? checkers[x + 1][y - 1] :
                (i == 6 && notBtmBrdr) ? checkers[x + 1][y] :
                (i == 7 && notBtmBrdr && notRgtBrdr) ? checkers[x + 1][y + 1] :
                null;
    }

    //this method checks if all the pieces of a player are continues
    //by placing each
    protected boolean piecesContiguous(Player player) {
        int contigPieces = 0;
        Stack<Checker> stack = new Stack<>();
        Checker firstSpot = player.getMyCheckers()[0];
        stack.push(firstSpot);
        firstSpot.setChecked(true);
        Checker pop;
        while (!stack.isEmpty()) {
            pop = stack.pop();
            contigPieces++;
            for (int i = 0; i < 8; i++) {
                Checker c = (whichCell(i, pop));
                if (c == null) {
                    continue;
                }
                if (c.getColor() == player.getColor()) {
                    c.setChecked(true);
                    stack.push(c);
                }
            }
        }
        player.setCheckedFalse();
        return (contigPieces == player.getCheckersLength());
    }
    protected void setCheckerAt(Checker checker,Position p){
        checkers[p.getX()][p.getY()] = checker;
        checkers[checker.getPosition().getX()][checker.getPosition().getY()] = null;
        checker.setPosition(p);

    }
    //the function switches between the moving checker and the distention cell and clears the moving cell
//	plus changes the position of the moving checker in the players' checkers array
    protected void makeAMove(Checker checker,Position position){
        setCheckerAt(checker,position);
        for (Checker c : getCurrentPlayer().getMyCheckers()){
            if (c==checker)
                c.setPosition(position);
        }
        changeTurn();

    }
    //checks if each one of the players got all of his pieces in a sequence
   protected  Color GameOver(){
        boolean whitePlayer = piecesContiguous(white);
        boolean blackPlayer = piecesContiguous(black);
        return (whitePlayer)?(blackPlayer)?
                (getTurn()==Color.WHITE) ? Color.WHITE : Color.BLACK
                : Color.WHITE
                : blackPlayer ? Color.BLACK: null;
    }

}




