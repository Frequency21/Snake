package hu.alkfejl.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

// rendering will be inside GameController's game loop
// TODO: 2021. 04. 15. ez így biztos jó?
//  kell-e gamestatus, vagy elég a boolean?
public class GameModel {
    private ObjectProperty<PlayerModel> player1 = new SimpleObjectProperty<>();
    private ObjectProperty<PlayerModel> player2 = new SimpleObjectProperty<>();
    private ObjectProperty<BoardModel> board = new SimpleObjectProperty<>();
    private BooleanProperty status = new SimpleBooleanProperty();

    public GameModel() { }

    public GameModel(PlayerModel player1, PlayerModel player2, BoardModel board, boolean status) {
        this.player1.set(player1);
        this.player2.set(player2);
        this.board.set(board);
        this.status .set(status);
    }

    public PlayerModel getPlayer1() {
        return player1.get();
    }

    public ObjectProperty<PlayerModel> player1Property() {
        return player1;
    }

    public void setPlayer1(PlayerModel player1) {
        this.player1.set(player1);
    }

    public PlayerModel getPlayer2() {
        return player2.get();
    }

    public ObjectProperty<PlayerModel> player2Property() {
        return player2;
    }

    public void setPlayer2(PlayerModel player2) {
        this.player2.set(player2);
    }

    public BoardModel getBoard() {
        return board.get();
    }

    public ObjectProperty<BoardModel> boardProperty() {
        return board;
    }

    public void setBoard(BoardModel board) {
        this.board.set(board);
    }

    public boolean isStatus() {
        return status.get();
    }

    public BooleanProperty statusProperty() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status.set(status);
    }
}
