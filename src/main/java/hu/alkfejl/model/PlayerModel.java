package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

// TODO: 2021. 04. 15. increment score based on snake's speed
public class PlayerModel {
    private final ObjectProperty<SnakeModel> snake = new SimpleObjectProperty<>();
    private final IntegerProperty score = new SimpleIntegerProperty();
    
    public PlayerModel() { }

    public PlayerModel(SnakeModel snake, int score) {
        this.snake.set(snake);
        this.score.set(score);
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

    public int getScore() {
        return score.get();
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void setScore(int score) {
        this.score.set(score);
    }
}
