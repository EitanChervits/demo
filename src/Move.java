/*this class identifies a move in the game by the state of the board, and the player that makes the move
*  */

public class Move {
private Player player;
private Board board;

    public Move(Player player, Board board) {
        this.player = player;
        this.board = board;
    }
//getters and setters
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
