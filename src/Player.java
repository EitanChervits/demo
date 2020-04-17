
public class Player {
	private String name;
	private int color;
	private Checker [] myCheckers = new Checker[12];

	public Player(int color, boolean turn) {
		this.color = color;
	}

	public Player(String name, int color) {
		this.name = name;
		this.color = color;


	}

	// setters
	public void setName(String name) {
		this.name = name;
	}
 
	public void setColor(int color) {
		this.color = color;
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
		for (int i = 0; i<getMyCheckers().length;i++){
			c = getMyCheckers()[i];
			if(c.getPosition() == checker.getPosition()){
				getMyCheckers()[i] = getMyCheckers()[getMyCheckers().length-1];
				getMyCheckers()[getMyCheckers().length-1] = null;
			}
		}
	}

	public void setCheckedFasle(){
		for(Checker c:this.getMyCheckers()){
			c.setChecked(false);
		}
	}
	public int getCheckersLength() {
		return getMyCheckers().length;
	}

}
