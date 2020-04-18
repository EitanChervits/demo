
public class Player {
    private String name;
    private Color color;
    private Checker [] myCheckers = new Checker[12];

    public Player(Color color) {
        this.color = color;
    }

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;


    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public void setMyCheckers(Checker[] myCheckers) {
        this.myCheckers = myCheckers;
    }

    // getters
    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }


    public Checker[] getMyCheckers() {
        return myCheckers;
    }

    public void addChecker(Checker checker) {
        for (int i = 0; i < getMyCheckers().length; i++) {
            if (getMyCheckers()[i] == null) {
                getMyCheckers()[i] = checker;
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
