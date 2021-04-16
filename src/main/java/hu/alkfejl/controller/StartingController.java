package hu.alkfejl.controller;

import hu.alkfejl.App;
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
        sceneManager.switchScene("../canvas.fxml");
    }

    public void playerTwo(ActionEvent actionEvent) {

    }

    @Override
    public void init() {

    }

    @Override
    public void onSwitch() {

    }
}
