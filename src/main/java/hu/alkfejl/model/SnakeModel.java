package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SnakeModel {
    private ObjectProperty<PlayerModel> owner = new SimpleObjectProperty<>();
    private IntegerProperty speed = new SimpleIntegerProperty();
    private ObjectProperty<Color> color = new SimpleObjectProperty<>();
    private Direction direction;
    // body[0] = head
    private final List<BodyPart> body = new ArrayList<>();

    public SnakeModel() { }

    public SnakeModel(Position position, int speed, Color color, Direction direction) {
        body.add(new BodyPart(position));
        this.speed.set(speed);
        this.color.set(color);
        this.direction = direction;
    }

    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    public void move() {
        // TODO: 2021. 04. 15.
    }

    public static class BodyPart {
        private Position position;

        public BodyPart(Position position) {
            this.position = position;
        }

        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }
    }

    public int getSpeed() {
        return speed.get();
    }

    public IntegerProperty speedProperty() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed.set(speed);
    }

    public Color getColor() {
        return color.get();
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public List<BodyPart> getBody() {
        return body;
    }

    public PlayerModel getOwner() {
        return owner.get();
    }

    public ObjectProperty<PlayerModel> ownerProperty() {
        return owner;
    }

    // mutual relation
    public void setOwner(PlayerModel owner) {
        owner.setSnake(this);
        this.owner.set(owner);
    }
}
