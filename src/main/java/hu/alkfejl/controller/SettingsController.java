package hu.alkfejl.controller;

import hu.alkfejl.model.GameModel;
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
            int size = gameModel.getBlockSize();
            int diff = newValue - oldValue;
            double stageSizeX = sceneManager.getRootStage().getWidth();
            double stageSizeY = sceneManager.getRootStage().getHeight();
            sceneManager.getRootStage().setWidth(stageSizeX + diff * size);
            sceneManager.getRootStage().setHeight(stageSizeY + diff * size);
            sceneManager.getRootStage().setX(
                    sceneManager.getRootStage().getX() - diff * size / 2.0);
            sceneManager.getRootStage().setY(
                    sceneManager.getRootStage().getY() - diff * size / 2.0);
        });
        gameModel.getBoard().sizeProperty().bind(boardSizeSp.valueProperty());
    }

    @Override
    public void onSwitch() {
        if (gameModel.getStatus() == GameModel.GameStatus.STOPPED) {
            boardSizeSp.setDisable(true);
            snakeSpeedSp.setDisable(true);
            boundaryCB.setDisable(true);
        } else {
            boardSizeSp.setDisable(false);
            snakeSpeedSp.setDisable(false);
            boundaryCB.setDisable(false);
        }
    }

    @FXML
    private void goBack(ActionEvent actionEvent) {
        // set initial speeds
        gameModel.getBoard().getSnake1().setStartingSpeed(snakeSpeedSp.getValue());
        gameModel.getBoard().getSnake2().setStartingSpeed(snakeSpeedSp.getValue());
        sceneManager.switchScene("../starting.fxml");
    }

    private void setControllers() {
        snakeSpeedSp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1, 1));
        boardSizeSp.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(30, 40, 30, 1));
        snake1ColorCP.setValue(Color.LIGHTGREEN);
        snake2ColorCP.setValue(Color.GREEN);
        boundaryCB.setSelected(false);
    }
}
