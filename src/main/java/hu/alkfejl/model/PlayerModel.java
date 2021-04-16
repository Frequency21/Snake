package hu.alkfejl.model;

import javafx.beans.property.*;

// TODO: 2021. 04. 15. increment score based on snake's speed
public class PlayerModel {
    private final IntegerProperty score = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private ObjectProperty<SnakeModel> snake = new SimpleObjectProperty<>();
    
    public PlayerModel() { }

    public int getScore() {
        return score.get();
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SnakeModel getSnake() {
        return snake.get();
    }

    public ObjectProperty<SnakeModel> snakeProperty() {
        return snake;
    }

    public void setSnake(SnakeModel snake) {
        this.snake.set(snake);
    }
}
