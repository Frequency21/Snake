package hu.alkfejl.controller;

import hu.alkfejl.model.FruitModel;
import hu.alkfejl.model.FruitType;
import hu.alkfejl.model.GameModel;
import hu.alkfejl.model.Position;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


public class CanvasController extends BaseController {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private List<FruitModel> fruits = new ArrayList<>();
    private int blockSize;

    public void drawRectangle(ActionEvent actionEvent) {
        drawFruits(fruits);
        drawGrid();
    }

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
        gc = canvas.getGraphicsContext2D();
        for (var type : FruitType.values()) fruits.add(new FruitModel(type));
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
        gc.setFill(Color.BLACK);
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
