
public class Player {
	private String name;
	private int color;
	private boolean turn;
	private Checker [] myCheckers = new Checker[12];

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

	public void setMyCheckers(Checker[] myCheckers) {
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

	public Checker[] getMyCheckers() {
		return myCheckers;
	}

	public void addChecker(Position p) {
		for (int i = 0; i < getMyCheckers().length; i++) {
			if (getMyCheckers()[i] == null) {
				getMyCheckers()[i] = new Checker(this.getColor(), p);
				return;
			}
		}
	}
	public void deleteAPiece(Checker checker){
		Checker c = null;
		for (int i = 0; i<this.getMyCheckers().length;i++){
			c = this.getMyCheckers()[i];
			if(c.getPosition() == checker.getPosition()){
				this.getMyCheckers()[i] = this.getMyCheckers()[this.getMyCheckers().length-1];
				this.getMyCheckers()[this.getMyCheckers().length-1] = null;
			}
		}
	}

	public void setCheckedFasle(){
		for(Checker c:this.getMyCheckers()){
			c.setChecked(false);
		}
	}

}
