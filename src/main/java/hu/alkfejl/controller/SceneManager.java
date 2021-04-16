package hu.alkfejl.controller;

import hu.alkfejl.model.GameModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// very very budget dependency injection service..
public class SceneManager {
    private final Stage rootStage;
    // ATTENTION. not the best solution..
    // rootStage could change across the program lifecycle
    // but gameModel should never
    private final GameModel gameModel;

    public SceneManager(Stage rootStage, GameModel gameModel) {
        if (rootStage == null) throw new IllegalArgumentException();
        this.rootStage = rootStage;
        this.gameModel = gameModel;
    }

    private final Map<String, Scene> scenes = new HashMap<>();

    public void switchScene(String url) {
        Scene scene = scenes.computeIfAbsent(url, u -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(u));
            try {
                Pane p = loader.load();
                BaseController controller = loader.getController();
                controller.setSceneManager(this);
                controller.setGameModel(gameModel);
                controller.init();
                return new Scene(p);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        rootStage.setScene(scene);
    }


    public void switchScene(String url, int width, int height) {
        Scene scene = scenes.computeIfAbsent(url, u -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(u));
            try {
                Pane p = loader.load();
                BaseController controller = loader.getController();
                controller.setSceneManager(this);
                controller.setGameModel(gameModel);
                controller.init();
                return new Scene(p, width, height);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        rootStage.setScene(scene);
    }
}
