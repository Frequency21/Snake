package hu.alkfejl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;


// TODO: 2021. 04. 16. refactor foodColor for every type..
public class SettingsController extends BaseController {

    @FXML
    private ColorPicker foodColorCP;
    @FXML
    private ColorPicker snake1ColorCP;
    @FXML
    private ColorPicker snake2ColorCP;
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
        setControllers();
        gameModel.getPlayer1().getSnake().colorProperty().bindBidirectional(snake1ColorCP.valueProperty());
        gameModel.getPlayer2().getSnake().colorProperty().bindBidirectional(snake2ColorCP.valueProperty());
        gameModel.getBoard().boundaryProperty().bindBidirectional(boundaryCB.selectedProperty());
        // if board size changes then resize and move stage
        boardSizeSp.valueProperty().addListener((obs, oldValue, newValue) -> {
            int size = gameModel.getBoard().getBlockSize();
            sceneManager.getRootStage().setWidth(newValue * size);
            sceneManager.getRootStage().setHeight(newValue * size);
            sceneManager.getRootStage().setX(
                    sceneManager.getRootStage().getX() + (oldValue - newValue) * size / 2.0);
            sceneManager.getRootStage().setY(
                    sceneManager.getRootStage().getY() + (oldValue - newValue) * size / 2.0);
        });
        // TODO: 2021. 04. 16. set food color property (there are no fruits in the game yet)..
    }

    @Override
    public void onSwitch() { }

    @FXML
    private void goBack(ActionEvent actionEvent) {
        sceneManager.switchScene("../starting.fxml");
    }

    private void setControllers() {
        snakeSpeedSp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1, 1));
        boardSizeSp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(30, 40, 30, 1));
        snake1ColorCP.setValue(Color.LIGHTGREEN);
        snake2ColorCP.setValue(Color.GREEN);
        boundaryCB.setSelected(true);
    }
}
