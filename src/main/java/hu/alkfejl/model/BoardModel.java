package hu.alkfejl.model;

import javafx.beans.property.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


/**
 * simple board with cells <-- positions are integers
 */
public class BoardModel {
    /**
     * square board..
     */
    private final IntegerProperty size = new SimpleIntegerProperty();
    private final BooleanProperty boundary = new SimpleBooleanProperty();
    private final ObjectProperty<List<FruitModel>> fruits = new SimpleObjectProperty<>(new ArrayList<>());
    private final ObjectProperty<SnakeModel> snake1 = new SimpleObjectProperty<>();
    private final ObjectProperty<SnakeModel> snake2 = new SimpleObjectProperty<>();

    public BoardModel() {
    }

    public BoardModel(int size, boolean boundary) {
        this.size.set(size);
        this.boundary.set(boundary);
        this.setSnake1(new SnakeModel(new Position(2, 4), Color.LIGHTGREEN, SnakeModel.Direction.DOWN));
        snake1.get().getBody().add(new SnakeModel.BodyPart(new Position(2, 3)));
        snake1.get().getBody().add(new SnakeModel.BodyPart(new Position(2, 2)));
        this.setSnake2(new SnakeModel(new Position(9, 4), Color.GREEN, SnakeModel.Direction.DOWN));
        snake2.get().getBody().add(new SnakeModel.BodyPart(new Position(9, 3)));
        snake2.get().getBody().add(new SnakeModel.BodyPart(new Position(9, 2)));
    }

    void reset() {
        getSnake1().clear();
        getSnake1().setSpeed(snake1.get().getStartingSpeed());
        getSnake1().resetDirection();
        getSnake1().add(new Position(2, 4));
        getSnake1().add(new Position(2, 3));
        getSnake1().add(new Position(2, 2));

        getSnake2().clear();
        getSnake2().setSpeed(snake2.get().getStartingSpeed());
        getSnake2().resetDirection();
        getSnake2().add(new Position(9, 4));
        getSnake2().add(new Position(9, 3));
        getSnake2().add(new Position(9, 2));
    }

    public int getSize() {
        return size.get();
    }

    public IntegerProperty sizeProperty() {
        return size;
    }

    public void setSize(int size) {
        this.size.set(size);
    }

    public boolean isBoundary() {
        return boundary.get();
    }

    public BooleanProperty boundaryProperty() {
        return boundary;
    }

    public void setBoundary(boolean boundary) {
        this.boundary.set(boundary);
    }

    public List<FruitModel> getFruits() {
        return fruits.get();
    }

    public ObjectProperty<List<FruitModel>> fruitsProperty() {
        return fruits;
    }

    public void setFruits(List<FruitModel> fruits) {
        this.fruits.set(fruits);
    }

    public SnakeModel getSnake1() {
        return snake1.get();
    }

    public ObjectProperty<SnakeModel> snake1Property() {
        return snake1;
    }

    public void setSnake1(SnakeModel snake1) {
        this.snake1.set(snake1);
    }

    public SnakeModel getSnake2() {
        return snake2.get();
    }

    public ObjectProperty<SnakeModel> snake2Property() {
        return snake2;
    }

    public void setSnake2(SnakeModel snake2) {
        this.snake2.set(snake2);
    }

}
