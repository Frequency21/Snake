package hu.alkfejl.controller;

import hu.alkfejl.DAO.SimplePlayerDAO;
import hu.alkfejl.model.*;
import hu.alkfejl.model.Game.GameStatus;
import hu.alkfejl.view.MultiNameDialog;
import hu.alkfejl.view.NameDialog;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.*;


public class CanvasController extends BaseController {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private final ObjectProperty<List<Fruit>> fruits = new SimpleObjectProperty<>();
    private int blockSize;
    private AnimationTimer timer;
    private Snake snake1;
    private Snake snake2;
    private boolean snake1Released = false;
    private boolean snake2Released = false;
    private final SimplePlayerDAO playerDAO = new SimplePlayerDAO();
    private final MultiNameDialog multiNameDialog = new MultiNameDialog();
    private final NameDialog nameDialog = new NameDialog();

    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE:
                stopped();
                break;
            case W:
                snake1.setDirection(Snake.Direction.UP);
                snake1Released = false;
                break;
            case D:
                snake1.setDirection(Snake.Direction.RIGHT);
                snake1Released = false;
                break;
            case S:
                snake1.setDirection(Snake.Direction.DOWN);
                snake1Released = false;
                break;
            case A:
                snake1.setDirection(Snake.Direction.LEFT);
                snake1Released = false;
                break;
            case UP:
                snake2.setDirection(Snake.Direction.UP);
                snake2Released = false;
                break;
            case RIGHT:
                snake2.setDirection(Snake.Direction.RIGHT);
                snake2Released = false;
                break;
            case DOWN:
                snake2.setDirection(Snake.Direction.DOWN);
                snake2Released = false;
                break;
            case LEFT:
                snake2.setDirection(Snake.Direction.LEFT);
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
        timer.stop();
        game.setStatus(GameStatus.STOPPED);
        sceneManager.switchScene("../starting.fxml");
    }

    private void exit() {
        timer.stop();
        game.setStatus(GameStatus.OVER);
        if (game.isMultiPlayer()) {
            if (!multiNameDialog.isNamesSet()) {
                multiNameDialog.show();
            } else {
                playerDAO.save(snake1.getOwner(), snake2.getOwner());
            }
        } else if (!nameDialog.isNameSet()) {
            nameDialog.show();
        } else {
            playerDAO.save(snake1.getOwner());
        }
        sceneManager.switchScene("../starting.fxml");
    }

    @Override
    public void init() {
        // initialize fields
        blockSize = game.getBlockSize();
        snake1 = game.getBoard().getSnake1();
        snake2 = game.getBoard().getSnake2();
        fruits.bindBidirectional(game.getBoard().fruitsProperty());

        // set canvas size to fit stage size
        canvas.heightProperty().bind(game.getBoard().sizeProperty().multiply(blockSize));
        canvas.widthProperty().bind(game.getBoard().sizeProperty().multiply(blockSize));
        gc = canvas.getGraphicsContext2D();
        try {
            game.generateFruit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: 2021. 04. 29. remove println
        nameDialog.resultProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(oldValue);
            System.out.println(newValue);
            if (newValue != null && !newValue.isEmpty()) {
                snake1.getOwner().setName(newValue);
                playerDAO.save(snake1.getOwner());
            }
        });

        multiNameDialog.resultProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.getFirst().isEmpty() && !newValue.getSecond().isEmpty()) {
                snake1.getOwner().setName(newValue.getFirst());
                snake2.getOwner().setName(newValue.getSecond());
                // TODO: 2021. 04. 29. save to db
            }
        });

        // simple timer
        timer = new AnimationTimer() {
            long last1;
            long last2;
            GameStatus gs1;
            GameStatus gs2;

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
                    gs1 = game.move(snake1, !snake1Released);
                    if (!game.isMultiPlayer() && gs1 == GameStatus.OVER) {
                        tick(gc);
                        if (snake1.getOwner().getName() == null || snake1.getOwner().getName().isEmpty())
                            System.out.println("Player1 lose");
                        else System.out.println(snake1.getOwner().getName() + " lose");
                        exit();
                    }
                }
                if (game.isMultiPlayer() && now - last2 > 1e9 / snake2.getSpeed()) {
                    last2 = now;
                    gs2 = game.move(snake2, !snake2Released);
                }

                if (gs1 == GameStatus.OVER || gs2 == GameStatus.OVER)
                    exit();

                /* after snakes move, did they collide? */
                if (game.isMultiPlayer()) {
                    if (game.checkCollision() == GameStatus.OVER)
                        exit();
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
        if (game.isMultiPlayer())
            draw(gc, snake2);
    }

    private void draw(GraphicsContext gc, Snake snake) {
        gc.setLineWidth(2);
        gc.setStroke(Color.BLACK);
        gc.setFill(snake.getColor());
        for (var bp: snake.getBody()) {
            gc.fillRoundRect(bp.getPosition().getX() * blockSize,
                    bp.getPosition().getY() * blockSize, blockSize, blockSize, 10, 10);
            gc.strokeRoundRect(bp.getPosition().getX() * blockSize, bp.getPosition().getY() * blockSize,
                    blockSize, blockSize, 10, 10);
        }

        /* if berserk draw counter to position head */
        gc.setFill(Color.RED);
        gc.setFont(new Font(20));
        gc.setTextAlign(TextAlignment.CENTER);
        if (snake1.getBerserkTimeLeft() > 0) {
            if (game.isMultiPlayer()) {
                gc.fillText("" + snake1.getBerserkTimeLeft(), snake1.getHead().getPosition().getX() * blockSize + blockSize / 2.0,
                        snake1.getHead().getPosition().getY() * blockSize + blockSize / 2.0);
            } else {
                gc.fillText("" + snake1.getBerserkTimeLeft(), snake1.getHead().getPosition().getX() * blockSize + blockSize / 2.0,
                        snake1.getHead().getPosition().getY() * blockSize + 20);
            }
        }
        if (snake2.getBerserkTimeLeft() > 0) {
            if (game.isMultiPlayer()) {
                gc.fillText("" + snake2.getBerserkTimeLeft(), snake2.getHead().getPosition().getX() * blockSize + blockSize / 2.0,
                        snake2.getHead().getPosition().getY() * blockSize + blockSize / 2.0);
            } else {
                gc.fillText("" + snake2.getBerserkTimeLeft(), snake2.getHead().getPosition().getX() * blockSize + blockSize / 2.0,
                        snake2.getHead().getPosition().getY() * blockSize + 20);
            }
        }
    }

    private void draw(GraphicsContext gc) {
        /* background */
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, game.getBoard().getSize() * blockSize, game.getBoard().getSize() * blockSize);

        if (game.getBoard().isBoundary()) {
            double padding = 5;
            double radius = 10;
            int size = game.getBoard().getSize();
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
                gc.fillRoundRect(i * blockSize + padding / 2, padding / 2,
                        blockSize - padding, blockSize - padding, radius, radius);
            }
        }

        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);
        for (int i = 0; i < game.getBoard().getSize(); i++) {
            gc.strokeLine(i * blockSize, 0, i * blockSize, canvas.getWidth());
            gc.strokeLine(0, i * blockSize, canvas.getHeight(), i * blockSize);
        }
    }

    private void draw(GraphicsContext gc, List<Fruit> fruits) {
        double padding = 2.5;
        for (var fruit: fruits) {
            gc.setFill(fruit.getColor());
            gc.fillOval(fruit.getPosition().getX() * blockSize + padding, fruit.getPosition().getY() *
                    blockSize + padding, blockSize - 2 * padding, blockSize - 2 * padding);
        }
    }

    @Override
    public void onSwitch() {
        if (game.getStatus() == GameStatus.OVER || game.getStatus() == GameStatus.INITIAL) {
            if (game.isMultiPlayer()) {
                if (!multiNameDialog.isNamesSet()) {
                    multiNameDialog.showAndWait();
                }
            } else {
                if (!nameDialog.isNameSet())
                    nameDialog.showAndWait();
            }
            game.restart();
        }
        timer.start();
        game.setStatus(GameStatus.RUNNING);
    }

}
