package hu.alkfejl;

import hu.alkfejl.controller.BaseController;
import hu.alkfejl.controller.SceneManager;
import hu.alkfejl.model.BoardModel;
import hu.alkfejl.model.GameModel;
import hu.alkfejl.model.PlayerModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {
    private final GameModel gameModel;

    public App() {
        // setup game models
        PlayerModel player1 = new PlayerModel();
        PlayerModel player2 = new PlayerModel();
        BoardModel board = new BoardModel(30, false);
        board.getSnake1().setOwner(player1);
        board.getSnake2().setOwner(player2);
        gameModel = new GameModel(player1, player2, board);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // create & set stage for sceneManager
        SceneManager sceneManager = new SceneManager(stage, gameModel);

        // get fxmlLoader for starting scene
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("starting.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), gameModel.getSizePx(), gameModel.getSizePx());
        BaseController bc = fxmlLoader.getController();
        // set sceneManager for startingController
        bc.setSceneManager(sceneManager);
        bc.setGameModel(gameModel);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.onCloseRequestProperty().set(event -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void exit() {
        System.exit(0);
    }
}