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

import java.util.ArrayList;
import java.util.List;


public class CanvasController extends BaseController {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private List<FruitModel> fruits = new ArrayList<>();

    public void drawRectangle(ActionEvent actionEvent) {
        gc.setFill(gameModel.getPlayer1().getSnake().getColor());
        gc.fillRect(0, 0, 600, 400);
        drawFruits(fruits);
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
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);
        for (var type : FruitType.values()) fruits.add(new FruitModel(type));
    }

    void drawFruits(List<FruitModel> fruits) {
        Position init = new Position(0, 50);
        int size = 20;
        int offset = 50;
        int i = 0;
        for (var fruit: fruits) {
            System.out.println(fruit);
            gc.setFill(fruit.getColor());
            gc.fillOval(init.getX() + i * offset, init.getY(), size, size);
            ++i;
        }
    }

    void drawGrid() {

    }

    @Override
    public void onSwitch() {

    }
}
