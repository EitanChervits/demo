
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

	public void canGo(Checker c) {
		int x = c.getPosition().getX();
		int y = c.getPosition().getY();
		int counter = 0;
		// number of checkers in up and down direction
		for (int i = 0; i < 8; i++) {
			if (checkers[i][y].getColor() != 0)
				counter++;
		}
		if (x + counter < 8 && checkers[x + counter][y].getColor() != c.getColor())
			list.goTO(4,counter); ;//the cell can go in that direction(down)
		if (x - counter >= 0 && checkers[x - counter][y].getColor() != c.getColor())
			list.goTO(1,counter);//the cell can go in that direction(up)

		// number of checkers in left and right direction
		for (int i = counter = 0; i < 8; i++) {
			if (checkers[x][i].getColor() != 0)
				counter++;
		}
		if (y + counter < 8 && checkers[x][y + counter].getColor() != c.getColor())
			list.goTO(2,counter);//the cell can go in that direction(right)
		if (y - counter >= 0 && checkers[x][y - counter].getColor() != c.getColor())
			list.goTO(6,counter);//the cell can go in that direction(left)
		//from up left to bottom right
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
		if (y + counter < 8 && x + counter < 8 && checkers[x + counter][y + counter].getColor() != c.getColor())
			list.goTO(3,counter);//top left
		if (y - counter < 8 && x - counter < 8 && checkers[x - counter][y - counter].getColor() != c.getColor())
			list.goTO(7,counter);//bottom right
		//from up right to bottom left
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
		if (y + counter < 8 && x - counter < 8 && checkers[x - counter][y + counter].getColor() != c.getColor())
			list.goTO(1,counter);//top right
		if (y - counter < 8 && x + counter < 8 && checkers[x + counter][y - counter].getColor() != c.getColor())
			list.goTO(5,counter);//bottom left

	}
}
