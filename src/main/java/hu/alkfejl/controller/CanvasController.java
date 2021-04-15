package hu.alkfejl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class CanvasController extends BaseController implements Initializable {

    @FXML
    private Canvas canvas;
    private int posX = 0;
    private int posY = 0;
    private int offset = 0;
    private int size = 20;
    private GraphicsContext gc;

    public void drawRectangle(ActionEvent actionEvent) {
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 600, 400);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);

    }

    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE:
                exit();
                break;
        }
    }

    private void exit() {
        // TODO: 2021. 04. 15. set game status to end
        // TODO: 2021. 04. 15. save scores to db
        sceneManager.switchScene("../starting.fxml");
    }
}
