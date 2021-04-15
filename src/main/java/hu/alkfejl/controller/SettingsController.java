package hu.alkfejl.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;


public class SettingsController extends BaseController {

    public ColorPicker foodColorCP;
    public ColorPicker snakeColorCP;
    public Spinner<Integer> snakeSpeedSp;
    public Spinner<Integer> boardSizeSp;
    public CheckBox boundaryCB;
    public Button goBackBtn;

    public void goBack(ActionEvent actionEvent) {
        sceneManager.switchScene("../starting.fxml");
    }
}
