package hu.alkfejl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;


public class SettingsController extends BaseController {

    @FXML
    private ColorPicker foodColorCP;
    @FXML
    private ColorPicker snakeColorCP;
    @FXML
    private Spinner<Integer> snakeSpeedSp;
    @FXML
    private Spinner<Integer> boardSizeSp;
    @FXML
    private CheckBox boundaryCB;
    @FXML
    private Button goBackBtn;

    @Override
    public void init() {
        if (initialized) return;
        initialized = true;
        // DO NOT rebind every properties on every SwitchScene call..
        setControllers();
        gameModel.getPlayer1().getSnake().colorProperty().bindBidirectional(snakeColorCP.valueProperty());
        gameModel.getPlayer2().getSnake().colorProperty().bindBidirectional(snakeColorCP.valueProperty());
        gameModel.getBoard().boundaryProperty().bindBidirectional(boundaryCB.selectedProperty());
        // TODO: 2021. 04. 16. set food color property (there are no fruits in the game yet)..
    }

    @FXML
    private void goBack(ActionEvent actionEvent) {
        sceneManager.switchScene("../starting.fxml");
    }

    private void setControllers() {
        snakeSpeedSp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1, 1));
        boardSizeSp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 20, 10, 1));
        snakeColorCP.setValue(Color.LIGHTGREEN);
        boundaryCB.setSelected(true);
    }
}
