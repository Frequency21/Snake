package hu.alkfejl.controller;

import javafx.event.ActionEvent;

public class SettingsController extends BaseController {

    public void goBack(ActionEvent actionEvent) {
        sceneManager.switchScene("../starting.fxml");
    }
}
