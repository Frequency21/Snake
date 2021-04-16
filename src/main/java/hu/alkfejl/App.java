package hu.alkfejl;

import hu.alkfejl.controller.BaseController;
import hu.alkfejl.controller.SceneManager;
import hu.alkfejl.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {
    private GameModel gameModel;

    public App() {
        // TODO: 2021. 04. 16. értelmes starting position a kígyóknak
        SnakeModel snake1 = new SnakeModel(new Position(1, 1), 0, Color.BLACK, SnakeModel.Direction.DOWN);
        SnakeModel snake2 = new SnakeModel(new Position(9, 1), 0, Color.BLACK, SnakeModel.Direction.DOWN);
        PlayerModel player1 = new PlayerModel(snake1, 0);
        PlayerModel player2 = new PlayerModel(snake2, 0);
        BoardModel board = new BoardModel(10, false);
        gameModel = new GameModel(player1, player2, board);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // create & set stage for sceneManager
        SceneManager sceneManager = new SceneManager(stage, gameModel);

        // get fxmlLoader for starting scene
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("starting.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        BaseController bc = fxmlLoader.getController();
        // set sceneManager for startingController
        bc.setSceneManager(sceneManager);
        bc.setGameModel(gameModel);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void exit() {
        System.exit(0);
    }
}