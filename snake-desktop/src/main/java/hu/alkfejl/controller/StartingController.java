package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.model.GameModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartingController extends BaseController {

    @FXML
    private Button playerOneBtn;
    @FXML
    private Button playerTwoBtn;
    @FXML
    private Button settingsBtn;
    @FXML
    private Button exitBtn;

    public void exit(ActionEvent actionEvent) {
        App.exit();
    }

    public void settings(ActionEvent actionEvent) {
        sceneManager.switchScene("../settings.fxml");
    }

    public void playerOne(ActionEvent actionEvent) {
        gameModel.setMultiPlayer(false);
        sceneManager.switchScene("../canvas.fxml");
    }

    public void playerTwo(ActionEvent actionEvent) {
        gameModel.setMultiPlayer(true);
        sceneManager.switchScene("../canvas.fxml");
    }

    @Override
    public void init() {

    }

    @Override
    public void onSwitch() {
        if (gameModel.getStatus() == GameModel.GameStatus.OVER) {
            playerOneBtn.setDisable(false);
            playerTwoBtn.setDisable(false);
        } else if (gameModel.getStatus() == GameModel.GameStatus.RUNNING
                || gameModel.getStatus() == GameModel.GameStatus.STOPPED) {
            if (gameModel.isMultiPlayer()) {
                playerOneBtn.setDisable(true);
                playerTwoBtn.setDisable(false);
            }
            else {
                playerOneBtn.setDisable(false);
                playerTwoBtn.setDisable(true);
            }
        }
    }
}
