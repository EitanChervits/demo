package engine;

import java.util.ArrayList;

public class Player {
    private String name;
    protected Color color;
    protected ArrayList<Checker> myCheckers = new ArrayList<>();

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


    public void setMyCheckers(ArrayList<Checker> myCheckers) {
        this.myCheckers = myCheckers;
    }

    // getters
    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }


    public ArrayList<Checker> getMyCheckers() {
        return myCheckers;
    }

    public void addChecker(Checker checker) {
        myCheckers.add(checker);
    }

    public void deleteChecker(Checker checker){
        myCheckers.remove(checker);
    }

    public void setCheckedFalse(){
        for(Checker c:this.getMyCheckers()){
            c.setChecked(false);
        }
    }

    public int getCheckersLength() {
        return getMyCheckers().size();
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
