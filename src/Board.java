import java.util.Stack;
public class Board {
	private Checker[][] checkers = new Checker[8][8];
	private Player white;
	private Player black;
	private LinkedList list = new LinkedList();// the list will contain the number of cells the player can go in each direction

	public Board() {
		white = new Player(1, true);
		black = new Player(2, false);
		for (int i = 0; i < 8; i++) {
			list.add(i,0);
		}
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

	public Checker getcellAt(Position p) {
		return getcell()[p.getY()][p.getX()];
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
	/*the function generates position and color for all the cells on the board;*/
	public void setCheckers(){
		Position p = new Position();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j <8 ; j++) {
				p.setX(i);
				p.setY(j);
				checkers[i][j].setPosition(p);// sets position for each cell
				if ((i==0||i==7)&&(j!=0&&j!=7))
					checkers[i][j].setColor(2);//sets all the black cells
				else if((i!=0&&i!=7)&&(j==0||j==7))
					checkers[i][j].setColor(1);//sets all the white cells
				else checkers[i][j].setColor(0);//sets the empty cells
			}
		}
	}

	public void setPlayersCheckers(){
		for (int i = 0; i <8 ; i++) {
			for (int j = 0; j < 8; j++) {
				if(checkers[i][j].getColor()==1)
					white.getMyCheckers()[i].setPosition(checkers[i][j].getPosition());
				else if (checkers[i][j].getColor()==2)
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

public int countLeftRight(Checker c){
	int x = c.getPosition().getX();
	int y = c.getPosition().getY();
	int counter = 0;
		for (int i = counter = 0; i < 8; i++) {
		if (checkers[x][i].getColor() != 0)
			counter++;
	}
		return counter;
}
public  int countTopLeftToBottomRight(Checker c){
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
	public  int countTopRightToBottomLeft(Checker c){
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

	public void canGo(Checker c) {
		int x = c.getPosition().getX();
		int y = c.getPosition().getY();
		int counter = countUpDown(c);
		//can go up and down
		if (x +counter < 8 && checkers[x + counter][y].getColor() != c.getColor())
			list.goTO(4,counter); ;//the cell can go in that direction(down)
		if (x - counter >= 0 && checkers[x - counter][y].getColor() != c.getColor())
			list.goTO(1,counter);//the cell can go in that direction(up)
		// number of checkers in left and right direction
		counter = countLeftRight(c);
		if (y + counter < 8 && checkers[x][y + counter].getColor() != c.getColor())
			list.goTO(2,counter);//the cell can go in that direction(right)
		if (y - counter >= 0 && checkers[x][y - counter].getColor() != c.getColor())
			list.goTO(6,counter);//the cell can go in that direction(left)
		//from up left to bottom right
		counter = countTopLeftToBottomRight(c);
		if (y + counter < 8 && x + counter < 8 && checkers[x + counter][y + counter].getColor() != c.getColor())
			list.goTO(3,counter);//top left
		if (y - counter < 8 && x - counter < 8 && checkers[x - counter][y - counter].getColor() != c.getColor())
			list.goTO(7,counter);//bottom right
		//from up right to bottom left
		counter = countTopRightToBottomLeft(c);
		if (y + counter < 8 && x - counter < 8 && checkers[x - counter][y + counter].getColor() != c.getColor())
			list.goTO(1,counter);//top right
		if (y - counter < 8 && x + counter < 8 && checkers[x + counter][y - counter].getColor() != c.getColor())
			list.goTO(5,counter);//bottom left

	}
	//identifies in what part of the board the border cell is located,
	// if the cell is bottom position sends it to borderCellsBottom
	public Checker  borderCells(int i, Checker checker){
		int x = checker.getPosition().getX();
		int y = checker.getPosition().getY();
		Checker c = null;
		if(x==7||y==7)
			return borderCellsBottom(i,checker);
		if(x==0&&i<3)return null;
		 if(y==0&&i>3){
		 	if(x==0){
		 		return case4To7(i,x,y);
			}
		 	switch (i){
				case 1:
					c = checkers[x-1][y];
					break;
				case 2:
					c = checkers[x-1][y+1];
					break;
				default:
					return case4To7(i,x,y);
			}
		 	if (x==0){
		 		switch (i){
					case 3:
						c = checkers[x][y-1];
						break;
					case 5:
						c = checkers[x+1][y-1];
						break;
					case 7:
						c = checkers[x+1][y+1];
						break;
					default: return case4To7(i,x,y);
				}
			}
		 }
		return c;
	}
	//sums up all the cases when we need cells in positions 4,6,7
	private Checker case4To7(int i,int x,int y){
		Checker c = null;
		switch (i){
			case 4:
				c = checkers[x][y+1];
				break;
			case 6:
				c = checkers[x+1][y];
				break;
			case 7:
				c = checkers[x+1][y+1];
				break;
		}

		return c;
	}
	//sums up all the cases of the directions 1,2,4
	private Checker case1To4(int i,int x,int y){
		Checker c = null;
		switch (i){
			case 4:
				c = checkers[x][y+1];
				break;
			case 1:
				c = checkers[x-1][y];
				break;
			case 2:
				c = checkers[x-1][y+1];
				break;
		}

		return c;
	}
	//sums up all the cases of the 3rd direction
	private Checker case3(int x,int y){ return checkers[x][y-1];
	}
// sorts the bottom cells by identifying the position and matching the available directions
	public Checker borderCellsBottom(int i, Checker checker){
		Checker c= null;
		int x = checker.getPosition().getX();
		int y = checker.getPosition().getY();
		if(x==7){
			if(y==0){
				return case1To4(i,x,y);
			}
			if(y==7){
				switch (i){
					case 0:
					c = checkers[x-1][y-1];
					break;
					case 1:
						c = checkers[x-1][y];
						break;
					default:return case3(x,y);
				}
			}
			switch (i){
				case 0:
					c = checkers[x-1][y-1];
					break;
				case 3:
					c = checkers[x][y-1];
					break;
				default:return case1To4(i,x,y);
			}
		}
		if (y==7){
			if (x==0) {
				switch (i) {
					case 3:
						c = checkers[x][y - 1];
						break;
					case 5:
						c = checkers[x + 1][y - 1];
						break;
					default:
						return case3(x, y);
				}
			}
		switch (i){
			case 0:
				c = checkers[x-1][y-1];
				break;
			case 1:
				c = checkers[x-1][y];
				break;
			case 5:
				c = checkers[x+1][y-1];
				break;
			case 6:
				c = checkers[x+1][y];
				break;
			default: return case3(x,y);

		}
		}
		return c;
	}

//c is the cell in the area of the cell we are testing
//the function receives a direction, and a checker and returns the checker in that specific
	private Checker whichCell(int i, Checker checker) {
	 int x = checker.getPosition().getX();
	 int y = checker.getPosition().getY();
	 if (checker.cellIsBorder())
	 	return borderCells( i, checker);
	 Checker c = null;
	 switch (i){
		 case 0:
		 	c = checkers[x-1][y-1];
		 	break;
		 case 1:
		 	c = checkers[x-1][y];
			 break;
		 case 2:
			 c = checkers[x-1][y+1];
			 break;
		 case 3:
			 c = checkers[x][y-1];
			 break;
		 case 4:
			 c = checkers[x][y+1];
			 break;
		 case 5:
			 c = checkers[x+1][y-1];
			 break;
		 case 6:
			 c = checkers[x+1][y];
			 break;
		 case 7:
			 c = checkers[x+1][y+1];
			 break;
	 }
	 return c;
	}
	public boolean piecesContiguous(Player player){
		int contigPieces = 0;
		Stack<Checker> stack = new Stack<Checker>();
		Checker firstSpot = player.getMyCheckers()[0];
		stack.push(firstSpot);
		firstSpot.setChecked(true);
		while (!stack.isEmpty()) {
			contigPieces++;
			for (int i = 0; i <8 ; i++) {
				Checker c = (whichCell(i,stack.pop()));
				if(c==null){
					continue;
				}

				if(c.getColor() == player.getColor()){
					c.setChecked(true);
					stack.push(c);
				}

			}

		}
		player.setCheckedFasle();
		return 	(contigPieces == player.getMyCheckers().length);
	}



}
