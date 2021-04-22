package hu.alkfejl.controller;

import hu.alkfejl.model.*;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.*;


public class CanvasController extends BaseController {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private final ObjectProperty<List<FruitModel>> fruits = new SimpleObjectProperty<>();
    private int blockSize;
    private AnimationTimer timer;
    private SnakeModel snake1;
    private SnakeModel snake2;
    private boolean snake1Released = false;
    private boolean snake2Released = false;

    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE:
                stopped();
                break;
            case W:
                snake1.setDirection(SnakeModel.Direction.UP);
                snake1Released = false;
                break;
            case D:
                snake1.setDirection(SnakeModel.Direction.RIGHT);
                snake1Released = false;
                break;
            case S:
                snake1.setDirection(SnakeModel.Direction.DOWN);
                snake1Released = false;
                break;
            case A:
                snake1.setDirection(SnakeModel.Direction.LEFT);
                snake1Released = false;
                break;
            case UP:
                snake2.setDirection(SnakeModel.Direction.UP);
                snake2Released = false;
                break;
            case RIGHT:
                snake2.setDirection(SnakeModel.Direction.RIGHT);
                snake2Released = false;
                break;
            case DOWN:
                snake2.setDirection(SnakeModel.Direction.DOWN);
                snake2Released = false;
                break;
            case LEFT:
                snake2.setDirection(SnakeModel.Direction.LEFT);
                snake2Released = false;
                break;
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W:
            case D:
            case S:
            case A:
                snake1Released = true;
                break;
            case UP:
            case RIGHT:
            case DOWN:
            case LEFT:
                snake2Released = true;
                break;
        }
    }

    private void stopped() {
        // TODO: 2021. 04. 15. save scores to db
        timer.stop();
        gameModel.setStatus(GameModel.GameStatus.STOPPED);
        sceneManager.switchScene("../starting.fxml");
    }

    private void exit() {
        timer.stop();
        gameModel.restart();
        snake1 = gameModel.getBoard().getSnake1();
        snake2 = gameModel.getBoard().getSnake2();
        sceneManager.switchScene("../starting.fxml");
    }

    @Override
    public void init() {
        // initialize fields
        blockSize = gameModel.getBlockSize();
        snake1 = gameModel.getBoard().getSnake1();
        snake1.setSpeed(6);
        snake2 = gameModel.getBoard().getSnake2();
        fruits.bindBidirectional(gameModel.getBoard().fruitsProperty());

        // set canvas size to fit stage size
        canvas.heightProperty().bind(gameModel.getBoard().sizeProperty().multiply(blockSize));
        canvas.widthProperty().bind(gameModel.getBoard().sizeProperty().multiply(blockSize));
        gc = canvas.getGraphicsContext2D();
        try {
            gameModel.generateFruit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // simple timer
        timer = new AnimationTimer() {
            long last1;
            long last2;

            @Override
            public void handle(long now) {
                if (last1 == 0 || last2 == 0) {
                    last1 = now;
                    last2 = now;
                    tick(gc);
                    return;
                }
                if (now - last1 > 1e9 / snake1.getSpeed()) {
                    last1 = now;
                    if (gameModel.move(snake1, !snake1Released) == GameModel.GameStatus.OVER) {
                        tick(gc);
                        // TODO: 2021. 04. 22. get player real name if (s)he has
                        //  and write to the canvas
                        System.out.println("Player1 lose");
                        exit();
                    }
                }
                if (gameModel.isMultiPlayer() && now - last2 > 1e9 / snake2.getSpeed()) {
                    last2 = now;
                    gameModel.move(snake2, !snake2Released);
                }
                tick(gc);
            }
        };
    }

    private void tick(GraphicsContext gc) {
        // draw grid and wall
        draw(gc);

        // draw fruits
        draw(gc, fruits.get());

        // draw snakes
        draw(gc, snake1);
        if (gameModel.isMultiPlayer())
            draw(gc, snake2);
    }

    private void draw(GraphicsContext gc, SnakeModel snake) {
        gc.setLineWidth(2);
        gc.setStroke(Color.BLACK);
        gc.setFill(snake.getColor());
        for (var bp: snake.getBody()) {
            gc.fillRoundRect(bp.getPosition().getX() * blockSize, bp.getPosition().getY() * blockSize, blockSize, blockSize, 10, 10);
            gc.strokeRoundRect(bp.getPosition().getX() * blockSize, bp.getPosition().getY() * blockSize, blockSize, blockSize, 10, 10);
        }
    }

    private void draw(GraphicsContext gc) {
        /* background */
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, gameModel.getBoard().getSize() * blockSize, gameModel.getBoard().getSize() * blockSize);

        if (gameModel.getBoard().isBoundary()) {
            double padding = 5;
            double radius = 10;
            int size = gameModel.getBoard().getSize();
            gc.setFill(Color.ORANGE);
            for (int i = 0; i < size; ++i) {
                // left
                gc.fillRoundRect(padding / 2, i * blockSize + padding / 2, blockSize - padding,
                        blockSize - padding, radius, radius);
                // right
                gc.fillRoundRect((size - 1) * blockSize + padding / 2, i * blockSize + padding / 2,
                        blockSize - padding, blockSize - padding, radius, radius);
                // bottom
                gc.fillRoundRect(i * blockSize + padding / 2, (size - 1) * blockSize + padding / 2,
                        blockSize - padding, blockSize - padding, radius, radius);
                // top
                gc.fillRoundRect(i * blockSize + padding / 2,padding / 2,
                        blockSize - padding, blockSize - padding, radius, radius);
            }
        }

        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);
        for (int i = 0; i < gameModel.getBoard().getSize(); i++) {
            gc.strokeLine(i * blockSize, 0, i * blockSize, canvas.getWidth());
            gc.strokeLine(0, i * blockSize, canvas.getHeight(), i * blockSize);
        }
    }

    private void draw(GraphicsContext gc, List<FruitModel> fruits) {
        double padding = 2.5;
        for (var fruit: fruits) {
            gc.setFill(fruit.getColor());
            gc.fillOval(fruit.getPosition().getX() * blockSize + padding, fruit.getPosition().getY() *
                    blockSize + padding, blockSize - 2 * padding, blockSize - 2 * padding);
        }
    }

    @Override
    public void onSwitch() {
        timer.start();
    }

}
