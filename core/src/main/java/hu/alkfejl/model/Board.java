package hu.alkfejl.model;

import javafx.beans.property.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


/**
 * simple board with cells <-- positions are integers
 */
public class Board {
    /**
     * square board..
     */
    private final IntegerProperty size = new SimpleIntegerProperty();
    private final BooleanProperty boundary = new SimpleBooleanProperty();
    private final ObjectProperty<List<Fruit>> fruits = new SimpleObjectProperty<>(new ArrayList<>());
    private final ObjectProperty<Snake> snake1 = new SimpleObjectProperty<>();
    private final ObjectProperty<Snake> snake2 = new SimpleObjectProperty<>();

    public Board() {
    }

    public Board(int size, boolean boundary) {
        this.size.set(size);
        this.boundary.set(boundary);
        this.setSnake1(new Snake(new Position(2, 4), Color.LIGHTGREEN, Snake.Direction.DOWN));
        snake1.get().getBody().add(new Snake.BodyPart(new Position(2, 3)));
        snake1.get().getBody().add(new Snake.BodyPart(new Position(2, 2)));
        this.setSnake2(new Snake(new Position(9, 4), Color.GREEN, Snake.Direction.DOWN));
        snake2.get().getBody().add(new Snake.BodyPart(new Position(9, 3)));
        snake2.get().getBody().add(new Snake.BodyPart(new Position(9, 2)));
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

    public List<Fruit> getFruits() {
        return fruits.get();
    }

    public ObjectProperty<List<Fruit>> fruitsProperty() {
        return fruits;
    }

    public void setFruits(List<Fruit> fruits) {
        this.fruits.set(fruits);
    }

    public Snake getSnake1() {
        return snake1.get();
    }

    public ObjectProperty<Snake> snake1Property() {
        return snake1;
    }

    public void setSnake1(Snake snake1) {
        this.snake1.set(snake1);
    }

    public Snake getSnake2() {
        return snake2.get();
    }

    public ObjectProperty<Snake> snake2Property() {
        return snake2;
    }

    public void setSnake2(Snake snake2) {
        this.snake2.set(snake2);
    }

}
