package hu.alkfejl.model;

import javafx.beans.property.*;

// TODO: 2021. 04. 15. increment score based on snake's speed
public class PlayerModel {
    private final IntegerProperty score = new SimpleIntegerProperty();
    /* when player gives his name, then we check is he played the game yet.
     * If he has played, then his score only get refreshed, if he beat his top score.
     * If he has not, then its the same with top score zero and insert instead of update. */
    private final IntegerProperty topScore = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private ObjectProperty<Snake> snake = new SimpleObjectProperty<>();

    public PlayerModel() { }

    void increaseScore(int with) {
        score.set(score.get() + with);
    }

    void decreaseScore(int with) {
        if (score.get() < with) score.set(0);
        else score.set(score.get() - with);
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

    public int getTopScore() {
        return topScore.get();
    }

    public IntegerProperty topScoreProperty() {
        return topScore;
    }

    public void setTopScore(int topScore) {
        this.topScore.set(topScore);
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

    public Snake getSnake() {
        return snake.get();
    }

    public ObjectProperty<Snake> snakeProperty() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake.set(snake);
    }

    @Override
    public String toString() { return "PlayerModel{name=" + name + ", score=" + score + '}'; }
}
