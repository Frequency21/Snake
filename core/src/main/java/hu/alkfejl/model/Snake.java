package hu.alkfejl.model;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.*;
import java.util.stream.Collectors;

public class Snake {
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
    private final int berserkDuration = 5;
    private final IntegerProperty berserkTimeLeft = new SimpleIntegerProperty();

    public Snake(Position position, Color color, Direction direction) {
        head = new BodyPart(position);
        body.add(head);
        this.speed.set(MIN_SPEED);
        this.color.set(color);
        lastDirection = this.direction = direction;
    }

    public void eat(Fruit fruit) {
        increaseSpeed();
        switch (fruit.getType()) {
            case COMMON:
                owner.get().increaseScore(FruitType.COMMON.getValue());
                break;
            case REVERSE:
                owner.get().increaseScore(FruitType.REVERSE.getValue());
                reverse();
                break;
            case SLOW:
                owner.get().increaseScore(FruitType.SLOW.getValue());
                timer.slow();
                break;
            case EXTRA:
                owner.get().increaseScore(FruitType.EXTRA.getValue());
                break;
            case BERSERK:
                owner.get().increaseScore(FruitType.BERSERK.getValue());
//                berserk = true;
                berserkTimeLeft.set(berserkDuration);
                Timeline berserkTimeLine = new Timeline(
                        new KeyFrame(
                                Duration.seconds(berserkDuration + 1),
//                                event -> berserk = false,
                                new KeyValue(berserkTimeLeft, 0)
                        )
                );
                berserkTimeLine.playFromStart();
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

    public void cut(BodyPart bodyPart) {
        body = body.stream().takeWhile(bp -> bp != bodyPart).collect(Collectors.toList());
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
    void move() {
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

    public boolean isBerserk() {
        return berserkTimeLeft.get() > 0;
    }

    public int getBerserkTimeLeft() {
        return berserkTimeLeft.get();
    }

    public IntegerProperty berserkTimeLeftProperty() {
        return berserkTimeLeft;
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

    /**
     * Timer class to trace slowing effect and snake speed
     * (slowing effect can stack, but snake's speed should never reach 0)
     */
    private class SlowingTimer {
        private final Timer timer = new Timer();
        private final Snake snake = Snake.this;
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

    /*private class BerserkTimer {
        private final Timer timer = new Timer();
        private final int seconds;
        private IntegerProperty timeLeft = new SimpleIntegerProperty();

        public BerserkTimer(int seconds) {
            this.seconds = seconds;
        }

        public void slow() {
            timer.schedule(new BerserkTimerTask(), seconds * 1000L);

            timeLeft.set(seconds);
            berserk = true;
        }

        private class BerserkTimerTask extends TimerTask {
            @Override
            public void run() {
                berserk = false;
            }
        }
    }*/
}
