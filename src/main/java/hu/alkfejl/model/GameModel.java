package hu.alkfejl.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;
import java.util.Optional;
import java.util.Random;

// rendering will be inside CanvasController's game loop
public class GameModel {
    private final ObjectProperty<PlayerModel> player1 = new SimpleObjectProperty<>();
    private final ObjectProperty<PlayerModel> player2 = new SimpleObjectProperty<>();
    private final ObjectProperty<BoardModel> board = new SimpleObjectProperty<>();
    private final ObjectProperty<GameStatus> status = new SimpleObjectProperty<>(GameStatus.INITIAL);
    private final BooleanProperty multiPlayer = new SimpleBooleanProperty();
    private final int blockSize = 25;
    private final Random rand = new Random();
    // local use
    private final SnakeModel snake1;
    private final SnakeModel snake2;
    private final List<FruitModel> fruits;

    public GameModel() {
        snake1 = board.get().getSnake1();
        snake2 = board.get().getSnake2();
        fruits = board.get().getFruits();
    }

    public GameModel(PlayerModel player1, PlayerModel player2, BoardModel board) {
        this.player1.set(player1);
        this.player2.set(player2);
        this.board.set(board);
        snake1 = board.getSnake1();
        snake2 = board.getSnake2();
        fruits = board.getFruits();
    }

    public void move(SnakeModel snake) {
        snake.move();
        Optional<FruitModel> eatenFruit = fruits.stream()
                .filter(fruit -> fruit.getPosition().equals(snake1.getHead().getPosition()))
                .findFirst();
        if (eatenFruit.isPresent()) {
            snake.increase();
            snake.eat(eatenFruit.get());
            fruits.remove(eatenFruit.get());
            try {
                generateFruit();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public FruitModel generateFruit() throws Exception {
        Position randPos;
        int trials = 0;
        do {
            ++trials;
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
            // if there's too much trials, then check is board full
            if (trials >= 100) {
                trials = 0;
                int reservedSpace = 0;
                reservedSpace += getBoard().getSnake1().getBody().size();
                if (multiPlayer.get()) reservedSpace += getBoard().getSnake2().getBody().size();
                reservedSpace += getBoard().getFruits().size() + 1;
                if (getBoard().isBoundary()) reservedSpace += getBoard().getSize() * 4 - 4;
                if (reservedSpace == getBoard().getSize() * getBoard().getSize())
                    throw new Exception("Board is full");
                continue;
            }
            randPos = position;
            break;
        } while (true);
        FruitModel result = new FruitModel(FruitModel.randomFruit(), randPos);
        getBoard().getFruits().add(result);
        return result;
    }

    private Position randomPosition(int size) {
        return new Position(rand.nextInt(size), rand.nextInt(size));
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

    public int getSizePx() {
        return board.get().getSize() * blockSize;
    }

    public int getBlockSize() {
        return blockSize;
    }

}
