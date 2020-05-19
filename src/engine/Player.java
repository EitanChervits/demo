package engine;

import java.util.ArrayList;

public class Player {
    protected Color color;
    protected Checker [] myCheckers = new Checker[12];

    public Player(Color color) {
        this.color = color;
    }

    public Player(String name, Color color) {
        this.color = color;


    }

    // setters

    public void setColor(Color color) {
        this.color = color;
    }


    public void setMyCheckers(Checker[] myCheckers) {
        this.myCheckers = myCheckers;
    }

    // getters

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
        Checker c;
        for (int i = 0; i<getMyCheckers().length;i++){
            c = getMyCheckers()[i];
            if(c.getPosition() == checker.getPosition()){
                getMyCheckers()[i] = getMyCheckers()[getMyCheckers().length-1];
                getMyCheckers()[getMyCheckers().length-1] = null;
            }
        }
    }

    public void setCheckedFalse(){
        for(Checker c:this.getMyCheckers()){
            c.setChecked(false);
        }
    }
    public int getCheckersLength() {
        return getMyCheckers().length;
    }
    public ArrayList<Position> getPlayerCheckersPositions() {
        ArrayList<Position> positions = new ArrayList<>();
        for (Checker c:myCheckers ){
            positions.add(c.getPosition());
        }
        return positions;
    }
    public Move makeMove(Board board){
        MachinePlayer machinePlayer = new MachinePlayer(color,board);
        return machinePlayer.minmax(3,Integer.MAX_VALUE,Integer.MIN_VALUE);
    }

}
