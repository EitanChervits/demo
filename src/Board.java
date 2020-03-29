
public class Board {
	private cell[][] cells = new cell[8][8];
	private Player white;
	private Player black;

	public Board() {
		white = new Player(1, true);
		black = new Player(2, false);
		// need to build setStartConfig();
	}

	// getters
	public cell[][] getcell() {
		return cells;
	}

	public Player getWhitePlayer() {
		return white;
	}

	public Player getBlackPlayer() {
		return black;
	}

	public cell getcellAt(Position p) {
		return getcell()[p.getY()][p.getX()];
	}

	// setters
	public void setCheckers(cell[][] c) {
		cells = c;
	}

	public void setWhitePlayer(Player p) {
		white = p;
	}

	public void setBlackPlayer(Player p) {
		black = p;
	}
}
