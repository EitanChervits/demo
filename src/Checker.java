

public class Checker {
	private Position position;
	private int color;

	public Checker() {

	}

	public Checker(int color, Position position) {
		this.position = position;
		this.color = color;
	}

	// getters
	public int getColor() {
		return color;
	}

	public Position getPosition() {
		return position;
	}

	// setters
	public void setPosition(Position p) {
		position = p;
	}

	public void setColor(int c) {
		color = c;
	}

	public String toString() {
		return getColor() + "---"
		/* + "\nIt's Position on Board : " + getPosition().draw() */;
	}
}




