package hu.alkfejl.model;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

import java.util.*;

public class SnakeModel {
    private final ObjectProperty<PlayerModel> owner = new SimpleObjectProperty<>();
    private final IntegerProperty speed = new SimpleIntegerProperty();
    private final ObjectProperty<Color> color = new SimpleObjectProperty<>();
    private Direction direction;
    private final List<BodyPart> body = new LinkedList<>();
    private BodyPart head;
    /** set slowing effect duration */
    private SlowingTimer timer = new SlowingTimer(5);


    public SnakeModel() {
        head = new BodyPart(new Position(1, 1));
        body.add(head);
        direction = Direction.DOWN;
        speed.set(1);
    }

    public SnakeModel(Position position, int speed, Color color, Direction direction) {
        head = new BodyPart(position);
        body.add(head);
        this.speed.set(speed);
        this.color.set(color);
        this.direction = direction;
    }

    public void eat(FruitModel fruitModel) {
        switch (fruitModel.getType()) {
            case COMMON:
                break;
            case SLOW:
                timer.slow();
                break;
            case EXTRA:
                break;
            case BERSERK:
                break;
        }
    }

    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    public void move() {
        // remove tail, then insert to neck with position of head
        BodyPart tail = body.get(body.size() - 1);
        tail.position.copy(head.position);
        body.remove(body.size() - 1);
        if (body.size() > 1)
            body.add(1, tail);
        else {
            body.add(0, tail);
            head = body.get(0);
        }

        // then move head
        switch (direction) {
            case UP:
                head.getPosition().decY();
                break;
            case RIGHT:
                head.getPosition().incX();
                break;
            case DOWN:
                head.getPosition().incY();
                break;
            case LEFT:
                head.getPosition().decX();
                break;
        }
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

    }

    // TODO: 2021. 04. 21. uniq timer for slowing effect duration would be nice.. (for the GUI)
    /**
     * Timer class to trace slowing effect and snake speed
     * (slowing effect can stack, but snake's speed should never reach 0)
     */
    private class SlowingTimer {
        private final Timer timer = new Timer();
        private final SnakeModel snake = SnakeModel.this;
        private final Vector<Integer> speeds = new Vector<>();
        private final int seconds;

        public SlowingTimer(int seconds) { this.seconds = seconds; }

        public void slow() {
            int speed = snake.getSpeed();
            speeds.add(speed);
            timer.schedule(new SlowTimerTask(), seconds * 1000L);
            snake.setSpeed(speed > 1 ? speed / 2 : 1);
        }

        private class SlowTimerTask extends TimerTask {
            @Override
            public void run() {
                int origSpeed = speeds.lastElement();
                snake.setSpeed(origSpeed);
                speeds.remove(speeds.size() - 1);
            }
        }
    }
}
