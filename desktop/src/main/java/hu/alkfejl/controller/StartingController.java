package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.model.Game.GameStatus;
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
        game.setMultiPlayer(false);
        sceneManager.switchScene("../canvas.fxml");
    }

    public void playerTwo(ActionEvent actionEvent) {
        game.setMultiPlayer(true);
        sceneManager.switchScene("../canvas.fxml");
    }

    @Override
    public void init() {

    }

    @Override
    public void onSwitch() {
        if (game.getStatus() == GameStatus.OVER) {
            playerOneBtn.setDisable(false);
            playerTwoBtn.setDisable(false);
        } else if (game.getStatus() == GameStatus.RUNNING
                || game.getStatus() == GameStatus.STOPPED) {
            if (game.isMultiPlayer()) {
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
