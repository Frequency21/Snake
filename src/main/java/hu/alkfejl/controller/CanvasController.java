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

import java.util.List;


public class CanvasController extends BaseController {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private final ObjectProperty<List<FruitModel>> fruits = new SimpleObjectProperty<>();
    private int blockSize;
    private AnimationTimer timer;
    private SnakeModel snake1;
    private SnakeModel snake2;

    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE:
                exit();
                break;
            case W:
                snake1.setDirection(SnakeModel.Direction.UP);
                break;
            case UP:
                snake2.setDirection(SnakeModel.Direction.UP);
                break;
            case D:
                snake1.setDirection(SnakeModel.Direction.RIGHT);
                break;
            case RIGHT:
                snake2.setDirection(SnakeModel.Direction.RIGHT);
                break;
            case S:
                snake1.setDirection(SnakeModel.Direction.DOWN);
                break;
            case DOWN:
                snake2.setDirection(SnakeModel.Direction.DOWN);
                break;
            case A:
                snake1.setDirection(SnakeModel.Direction.LEFT);
                break;
            case LEFT:
                snake2.setDirection(SnakeModel.Direction.LEFT);
                break;
        }
    }

    private void exit() {
        // TODO: 2021. 04. 15. set game status to pause
        // TODO: 2021. 04. 15. save scores to db
        timer.stop();
        gameModel.setStatus(GameModel.GameStatus.STOPPED);
        sceneManager.switchScene("../starting.fxml");
    }

    @Override
    public void init() {
        // initialize fields
        blockSize = gameModel.getBlockSize();
        snake1 = gameModel.getBoard().getSnake1();
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

        snake1.setSpeed(5);

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
                    gameModel.move(snake1);
                }
                if (gameModel.isMultiPlayer() && now - last2 > 1e9 / snake2.getSpeed()) {
                    last2 = now;
                    gameModel.move(snake2);
                }
                tick(gc);
            }
        };
    }

    private void tick(GraphicsContext gc) {
        // draw grid
        draw(gc);

        // draw fruits
        draw(gc, fruits.get());

        // draw snakes
        draw(gc, snake1);
        if (gameModel.isMultiPlayer())
            draw(gc, snake2);
    }

    private void draw(GraphicsContext gc, SnakeModel snake) {
        gc.setFill(snake.getColor());
        for (var bp: snake.getBody()) {
            gc.fillRect(bp.getPosition().getX() * blockSize, bp.getPosition().getY() * blockSize, blockSize, blockSize);
        }
    }

    private void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, gameModel.getBoard().getSize() * blockSize, gameModel.getBoard().getSize() * blockSize);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);
        for (int i = 0; i < gameModel.getBoard().getSize(); i++) {
            gc.strokeLine(i * blockSize, 0, i * blockSize, canvas.getWidth());
            gc.strokeLine(0, i * blockSize, canvas.getHeight(), i * blockSize);
        }
    }

    private void draw(GraphicsContext gc, List<FruitModel> fruits) {
        for (var fruit: fruits) {
            gc.setFill(fruit.getColor());
            gc.fillOval(fruit.getPosition().getX() * blockSize, fruit.getPosition().getY() * blockSize, blockSize, blockSize);
        }
    }

    @Override
    public void onSwitch() {
        timer.start();
    }
}
