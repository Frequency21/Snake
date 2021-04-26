package hu.alkfejl;

import hu.alkfejl.controller.BaseController;
import hu.alkfejl.controller.SceneManager;
import hu.alkfejl.model.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {
    private final Game Game;

    public App() {
        // setup game
        Game = new Game();
    }

    @Override
    public void start(Stage stage) throws IOException {
        // create & set stage for sceneManager
        SceneManager sceneManager = new SceneManager(stage, Game);

        // get fxmlLoader for starting scene
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("starting.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Game.getSizePx(), Game.getSizePx());
        BaseController bc = fxmlLoader.getController();
        // set sceneManager for startingController
        bc.setSceneManager(sceneManager);
        bc.setGame(Game);

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