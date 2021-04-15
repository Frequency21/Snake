package hu.alkfejl;

import hu.alkfejl.controller.BaseController;
import hu.alkfejl.controller.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // create & set stage for sceneManager
        SceneManager sceneManager = new SceneManager(stage);

        // get fxmlLoader for starting scene
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("starting.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        BaseController bc = fxmlLoader.getController();
        // set sceneManager for startingController
        bc.setSceneManager(sceneManager);

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