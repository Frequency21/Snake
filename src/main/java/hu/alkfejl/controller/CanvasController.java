package hu.alkfejl.controller;

import hu.alkfejl.model.FruitModel;
import hu.alkfejl.model.FruitType;
import hu.alkfejl.model.GameModel;
import hu.alkfejl.model.Position;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;


public class CanvasController extends BaseController {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private ObjectProperty<List<FruitModel>> fruits = new SimpleObjectProperty<>();
    private int blockSize;


    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE:
                exit();
                break;
        }
    }

    private void exit() {
        // TODO: 2021. 04. 15. set game status to pause
        // TODO: 2021. 04. 15. save scores to db
        gameModel.setStatus(GameModel.GameStatus.STOPPED);
        sceneManager.switchScene("../starting.fxml");
    }

    @Override
    public void init() {
        // set canvas size to fit stage size
        blockSize = gameModel.getBoard().getBlockSize();
        canvas.heightProperty().bind(gameModel.getBoard().sizeProperty().multiply(blockSize));
        canvas.widthProperty().bind(gameModel.getBoard().sizeProperty().multiply(blockSize));
        // bind fruits
        fruits.bindBidirectional(gameModel.getBoard().fruitsProperty());
        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(0.05),
                        event -> drawGame()));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
        gc = canvas.getGraphicsContext2D();
    }

    private void drawGame() {
        drawGrid();
        gameModel.generateFruit(FruitType.COMMON);
        for (var fruit: fruits.get()) {
            drawFruit(fruit);
        }
    }

    private void drawFruit(FruitModel fruit) {
        gc.setFill(fruit.getColor());
        gc.fillOval(fruit.getPosition().getX() * blockSize, fruit.getPosition().getY() * blockSize, blockSize, blockSize);
    }

    void drawFruits(List<FruitModel> fruits) {
        Position init = new Position(0, 50);
        int size = blockSize;
        int offset = 2 * blockSize;
        int i = 0;
        for (var fruit: fruits) {
            gc.setFill(fruit.getColor());
            gc.fillOval(init.getX() + i * offset, init.getY(), size, size);
            ++i;
        }
    }

    void drawGrid() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, gameModel.getBoard().getSizePx(), gameModel.getBoard().getSizePx());
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);
        for (int i = 0; i < gameModel.getBoard().getSize(); i++) {
            gc.strokeLine(i * blockSize, 0, i * blockSize, canvas.getWidth());
            gc.strokeLine(0, i * blockSize, canvas.getHeight(), i * blockSize);
        }
    }

    @Override
    public void onSwitch() {
        drawGrid();
    }
}
