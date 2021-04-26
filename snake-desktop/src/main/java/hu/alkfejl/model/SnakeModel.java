package hu.alkfejl.model;

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
    private Direction lastDirection;
    private List<BodyPart> body = new LinkedList<>();
    private BodyPart head;
    private int startingSpeed = 5;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 10;
    /**
     * set slowing effect duration
     */
    private final SlowingTimer timer = new SlowingTimer(5);

    public SnakeModel(Position position, Color color, Direction direction) {
        head = new BodyPart(position);
        body.add(head);
        this.speed.set(MIN_SPEED);
        this.color.set(color);
        lastDirection = this.direction = direction;
    }

    public void eat(FruitModel fruitModel) {
        increaseSpeed();
        switch (fruitModel.getType()) {
            case COMMON:
                break;
            case REVERSE:
                reverse();
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

    private void reverse() {
        int size = body.size();
        if (size < 2) return;

        /* determine where tail is looking */
        BodyPart tail = body.get(size - 1);
        BodyPart tailPrev = body.get(size - 2);

        if (tail.position.getX() == tailPrev.position.getX()) {
            if (tail.position.getY() > tailPrev.position.getY())
                direction = lastDirection = Direction.DOWN;
            else
                direction = lastDirection = Direction.UP;
        } else {
            if (tail.position.getX() > tailPrev.getPosition().getX())
                direction = lastDirection = Direction.RIGHT;
            else
                direction = lastDirection = Direction.LEFT;
        }

        for (int i = 0; i < body.size() / 2; i++) Collections.swap(body, i, size - 1 - i);

        head = body.get(0);
    }

    void clear() {
        body.clear();
        head = null;
    }

    void add(Position position) {
        body.add(new BodyPart(position));
        if (head == null) head = body.get(0);
    }

    public enum Direction {
        UP, RIGHT, DOWN, LEFT;

        public static boolean opposites(Direction p, Direction q) {
            return (p.ordinal() + 2) % 4 == q.ordinal();
        }
    }

    /**
     * move snake according to its direction
     *
     * @return is it bitten itself
     */
    boolean move() {
        if (!Direction.opposites(lastDirection, direction)) {
            direction = lastDirection;
        }

        if (body.size() > 1) {
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
        /* check is it bitten itself */
        return body.stream().skip(1).anyMatch(bp -> head.getPosition().equals(bp.getPosition()));
    }

    void accelerate() {
        if (Direction.opposites(direction, lastDirection))
            decreaseSpeed();
        else
            increaseSpeed();
    }

    private void increaseSpeed() {
        if (speed.get() < MAX_SPEED) speed.set(speed.get() + 1);
    }

    private void decreaseSpeed() {
        if (speed.get() > MIN_SPEED) speed.set(speed.get() - 1);
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

    /* direction will be set when move gets invoked */
    public void setDirection(Direction direction) {
        lastDirection = direction;
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

    public BodyPart getHead() {
        return head;
    }

    /** needs for board reset */
    void resetDirection() {
        direction = Direction.DOWN;
        lastDirection = Direction.DOWN;
    }

    public int getStartingSpeed() {
        return startingSpeed;
    }

    public void setStartingSpeed(int startingSpeed) {
        this.startingSpeed = startingSpeed;
    }

    public void increase() {
        BodyPart bp = new BodyPart(new Position(head.getPosition()));
        body.add(bp);
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

        public SlowingTimer(int seconds) {
            this.seconds = seconds;
        }

        public void slow() {
            int speed = snake.getSpeed();
            speeds.add(speed);
            timer.schedule(new SlowTimerTask(), seconds * 1000L);
            snake.setSpeed(speed >= 2 * MIN_SPEED ? speed / 2 : MIN_SPEED);
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
