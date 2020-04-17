import java.util.Stack;
public class Board {
	private Checker[][] checkers = new Checker[8][8];
	private Player white;
	private Player black;
	private int turn;

	public Board() {
		white = new Player(1, true);
		black = new Player(2, false);

	}

	// getters
	public Checker[][] getcell() {
		return checkers;
	}

	public Player getWhitePlayer() {
		return white;
	}

	public Player getBlackPlayer() {
		return black;
	}

	public Checker getcheckerAt(Position p) {
		return getcell()[p.getY()][p.getX()];
	}

	public int getTurn() {
		return turn;
	}
	public void changeTurn(){
		if(getTurn()==1)setTurn(2);
		else setTurn(1);
	}
	public Player getPlayer(){
		return getTurn()==1?white:black;
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
	public void setTurn(int turn) {
		this.turn = turn;
	}
	/*the function generates position and color for all the cells on the board;*/
	public void setCheckers() {
		Position p = new Position();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				p.setX(i);
				p.setY(j);
				if ((i == 0 || i == 7) && (j != 0 && j != 7))
					checkers[i][j] = new Checker(2,p);//sets all the black cells
				else if ((i != 0 && i != 7) && (j == 0 || j == 7))
					checkers[i][j] = new Checker(1,p);
			}
		}
	}

	public void setPlayersCheckers() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (checkers[i][j].getColor() == 1)
					white.getMyCheckers()[i].setPosition(checkers[i][j].getPosition());
				else if (checkers[i][j].getColor() == 2)
					black.getMyCheckers()[i].setPosition(checkers[i][j].getPosition());
			}
		}
	}

	public int countUpDown(Checker c) {
		int x = c.getPosition().getX();
		int y = c.getPosition().getY();
		int counter = 0;
		// number of checkers in up and down direction
		for (int i = 0; i < 8; i++) {
			if (checkers[i][y].getColor() != 0)
				counter++;
		}
		return counter;
	}

	public int countLeftRight(Checker c) {
		int x = c.getPosition().getX();
		int y = c.getPosition().getY();
		int counter = 0;
		for (int i = counter = 0; i < 8; i++) {
			if (checkers[x][i].getColor() != 0)
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
				if (checkers[i][j].getColor() != 0)
					counter++;
			}
		} else if (y > x) {
			for (int i = counter = 0, j = y - x; j < 8; j++, i++) {
				if (checkers[i][j].getColor() != 0)
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
				if (checkers[i][j].getColor() != 0)
					counter++;
			}
		} else if (x + y > 7) {
			for (int i = 7 - y, j = 7; i <= 7; i++, j--) {
				if (checkers[i][j].getColor() != 0)
					counter++;
			}
		}
		return counter;
	}

	public LinkedList canGo(Checker c) {
		int x = c.getPosition().getX();
		int y = c.getPosition().getY();
		int counter = countUpDown(c);
		LinkedList list = new LinkedList();// the list will contain the number of cells the player can go in each direction
		for (int i = 0; i < 8; i++) {
			list.add(i, 0);
		}
		//can go up and down
		/* 7	0	1
		   6	cell 2
		   5     4    3
		 */
		if (x + counter < 8 && checkers[x + counter][y].getColor() != c.getColor())
			list.goTO(4, counter);
		;//the cell can go in that direction(down)
		if (x - counter >= 0 && checkers[x - counter][y].getColor() != c.getColor())
			list.goTO(0, counter);//the cell can go in that direction(up)
		// number of checkers in left and right direction
		counter = countLeftRight(c);
		if (y + counter < 8 && checkers[x][y + counter].getColor() != c.getColor())
			list.goTO(2, counter);//the cell can go in that direction(right)
		if (y - counter >= 0 && checkers[x][y - counter].getColor() != c.getColor())
			list.goTO(6, counter);//the cell can go in that direction(left)
		//from up left to bottom right
		counter = countTopLeftToBottomRight(c);
		if (y + counter < 8 && x + counter < 8 && checkers[x + counter][y + counter].getColor() != c.getColor())
			list.goTO(3, counter);//bottom right
		if (y - counter < 8 && x - counter < 8 && checkers[x - counter][y - counter].getColor() != c.getColor())
			list.goTO(7, counter);//bottom right
		//from up right to bottom left
		counter = countTopRightToBottomLeft(c);
		if (y + counter < 8 && x - counter < 8 && checkers[x - counter][y + counter].getColor() != c.getColor())
			list.goTO(1, counter);//top right
		if (y - counter < 8 && x + counter < 8 && checkers[x + counter][y - counter].getColor() != c.getColor())
			list.goTO(5, counter);//bottom left

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
	public boolean piecesContiguous(Player player) {
		int contigPieces = 0;
		Stack<Checker> stack = new Stack<Checker>();
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
		player.setCheckedFasle();
		return (contigPieces == player.getCheckersLength());
	}
	public void setCheckerAt(Checker checker,Position p){
			if(getcheckerAt(p)!=null){
				checkers[p.getX()][p.getY()] = new Checker(checker.getColor(),p);
				checkers[checker.getPosition().getX()][checker.getPosition().getY()] = null;
			}
			else
			{
				checkers[p.getX()][p.getY()].setColor(checker.getColor());
				checkers[checker.getPosition().getX()][checker.getPosition().getY()] = null;
			}
	}
//the function switches between the moving checker and the distention cell and clears the moving cell
//	plus changes the position of the moving checker in the players' checkers array
	public void makeAMove(Checker checker,Position position){
			setCheckerAt(checker,position);
				for (Checker c :getPlayer().getMyCheckers()){
					if (c==checker)
						c.setPosition(position);
				}
				changeTurn();

		}
		//checks if each one of the players got all of his pieces in a sequence
		public int GameOver(){
		boolean whitePlayer = piecesContiguous(white);
		boolean blackPlayer = piecesContiguous(black);
		return (whitePlayer==true)?(blackPlayer==true)?
				(getTurn()==1)?1:2
				:1
				:(blackPlayer==true)?2:0;
	 }

	}




