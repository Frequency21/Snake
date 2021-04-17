package hu.alkfejl.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Random;

// rendering will be inside CanvasController's game loop
public class GameModel {
    private ObjectProperty<PlayerModel> player1 = new SimpleObjectProperty<>();
    private ObjectProperty<PlayerModel> player2 = new SimpleObjectProperty<>();
    private ObjectProperty<BoardModel> board = new SimpleObjectProperty<>();
    private ObjectProperty<GameStatus> status = new SimpleObjectProperty<>(GameStatus.INITIAL);
    private BooleanProperty multiPlayer = new SimpleBooleanProperty();
    private final Random rand = new Random();

    public GameModel() { }

    public GameModel(PlayerModel player1, PlayerModel player2, BoardModel board) {
        this.player1.set(player1);
        this.player2.set(player2);
        this.board.set(board);
    }

    public enum GameStatus {
        INITIAL, STOPPED, RUNNING, OVER
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

    public GameStatus getStatus() {
        return status.get();
    }

    public ObjectProperty<GameStatus> statusProperty() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status.set(status);
    }

    public boolean isMultiPlayer() {
        return multiPlayer.get();
    }

    public BooleanProperty multiPlayerProperty() {
        return multiPlayer;
    }

    public void setMultiPlayer(boolean multiPlayer) {
        this.multiPlayer.set(multiPlayer);
    }

    // TODO: 2021. 04. 17. it does NOT detect when board is full --> fall in infinity loop
    public FruitModel generateFruit(FruitType type) {
        Position randPos;
        do {
            Position position = randomPosition(getBoard().getSize());
            // if there's boundary
            if (getBoard().isBoundary()) {
                if (position.getX() == 0 || position.getY() == 0 ||
                        position.getX() == getBoard().getSize() || position.getY() == getBoard().getSize()) {
                    continue;
                }
            }
            if (getBoard().getFruits().stream().anyMatch(fruit -> fruit.getPosition().equals(position))) {
                continue;
            }
            if (getBoard().getSnake1().getBody().stream().anyMatch(bodyPart -> bodyPart.getPosition().equals(position))) {
                continue;
            }
            if (multiPlayer.get()) {
                if (getBoard().getSnake2().getBody().stream().anyMatch(bodyPart -> bodyPart.getPosition().equals(position))) {
                    continue;
                }
            }
            randPos = position;
            break;
        } while (true);
        FruitModel result = new FruitModel(type, randPos);
        getBoard().getFruits().add(result);
        return result;
    }

    private Position randomPosition(int size) {
        return new Position(rand.nextInt(size), rand.nextInt(size));
    }

}
