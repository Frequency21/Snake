package hu.alkfejl.model;

import javafx.beans.property.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


// TODO: 2021. 04. 15. create wall
public class BoardModel {
    /** square board.. */
    private IntegerProperty size = new SimpleIntegerProperty();
    private BooleanProperty boundary = new SimpleBooleanProperty();
    private ObjectProperty<List<FruitModel>> fruits = new SimpleObjectProperty<>();
    private final ObjectProperty<SnakeModel> snake1 = new SimpleObjectProperty<>();
    private final ObjectProperty<SnakeModel> snake2 = new SimpleObjectProperty<>();
    /** obstacles' and snake BodyParts' size in pixel */
    private final int blockSize = 25;

    public BoardModel() { }

    public BoardModel(int size, boolean boundary) {
        this.size.set(size);
        this.boundary.set(boundary);
        this.fruits.set(new ArrayList<>());
        this.setSnake1(new SnakeModel(new Position(1, 1), 0, Color.LIGHTGREEN, SnakeModel.Direction.DOWN));
        this.setSnake2(new SnakeModel(new Position(9, 1), 0, Color.GREEN, SnakeModel.Direction.DOWN));
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

    public int getSizePx() {
        return size.get() * blockSize;
    }

    public int getBlockSize() {
        return blockSize;
    }
}
