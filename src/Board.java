
public class Board {
	private Checker[][] checkers = new Checker[8][8];
	private Player white;
	private Player black;
	int directions[] = new int[8];/* each cell has 8 directions in which he can go,
									the directions are numbers between 0-7 clockwise.*/

	public Board() {
		white = new Player(1, true);
		black = new Player(2, false);
		// need to build setStartConfig();
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
			directions[4] = 1;//the cell can go in that direction(down)
		if (x - counter >= 0 && checkers[x - counter][y].getColor() != c.getColor())
			directions[0] = 1;//the cell can go in that direction(up)
		// number of checkers in left and right direction
		for (int i = counter = 0; i < 8; i++) {
			if (checkers[x][i].getColor() != 0)
				counter++;
		}
		if (y + counter < 8 && checkers[x][y + counter].getColor() != c.getColor())
			directions[2] = 1;//the cell can go in that direction(right)
		if (y - counter >= 0 && checkers[x][y - counter].getColor() != c.getColor())
			directions[6] = 1;//the cell can go in that direction(left)
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
			directions[3] = 1;//top left
		if (y - counter < 8 && x - counter < 8 && checkers[x - counter][y - counter].getColor() != c.getColor())
			directions[7] = 1;//bottom right
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
			directions[1] = 1;//top right
		if (y - counter < 8 && x + counter < 8 && checkers[x + counter][y - counter].getColor() != c.getColor())
			directions[5] = 1;//bottom left

	}
}
