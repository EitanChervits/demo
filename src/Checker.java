

public class Checker {
	private Position position;
	private int color;
	private int touch;
	private boolean checked;
	public Checker() {

	}

	public Checker(int color, Position position) {
		this.position = position;
		this.color = color;
		this.checked = false;
	}

	// getters
	public int getColor() {
		return color;
	}

	public Position getPosition() {
		return position;
	}

	public boolean isChecked() {
		return checked;
	}

	// setters
	public void setPosition(Position p) {
		position = p;
	}

	public void setColor(int c) {
		color = c;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String toString() {
		return getColor() + "---"
		/* + "\nIt's Position on Board : " + getPosition().draw() */;
	}
	//tells if a cell is a border cell
	public boolean cellIsBorder(){
		if(position.getX()==0||position.getY()==0||position.getX()==7||position.getY()==7)
			return true;
		return false;
	}


}




