
public class Player {
	private String name;
	private int color;
	private boolean turn;
	private cell [] myCheckers = new cell[12];

	public Player(int color, boolean turn) {
		this.color = color;
		this.turn = turn;
	}

	public Player(String name, int color) {
		this.name = name;
		this.color = color;
		if (color == 1) {
			setTurn(true);
		} else {
			setTurn(false);
		}

	}

	// setters
	public void setName(String name) {
		this.name = name;
	}
 
	public void setColor(int color) {
		this.color = color;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public void setMyCheckers(cell[] myCheckers) {
		this.myCheckers = myCheckers;
	}

	// getters
	public String getName() {
		return name;
	}

	public int getColor() {
		return color;
	}

	public boolean isTurn() {
		return turn;
	}

	public cell[] getMyCheckers() {
		return myCheckers;
	}

	public void addChecker(Position p) {
		for (int i = 0; i < getMyCheckers().length; i++) {
			if (getMyCheckers()[i] == null) {
				getMyCheckers()[i] = new cell(this.getColor(), p);
				return;
			}
		}
	}

}
