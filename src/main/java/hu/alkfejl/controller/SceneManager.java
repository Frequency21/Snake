package hu.alkfejl.controller;

import hu.alkfejl.model.GameModel;
import hu.alkfejl.model.Tuple;
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

    // hold reference both to Scene and its controller (onSwitch and init callbacks)
    private final Map<String, Tuple<Scene, BaseController>> scenes = new HashMap<>();

    // scene size will match board size
    public void switchScene(String url) {
        Tuple<Scene, BaseController> tuple = scenes.computeIfAbsent(url, u -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(u));
            try {
                Pane p = loader.load();
                BaseController controller = loader.getController();
                controller.setSceneManager(this);
                controller.setGameModel(gameModel);
                controller.init();
                return new Tuple<>(new Scene(p, gameModel.getBoard().getSizePx(), gameModel.getBoard().getSizePx()), controller);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        tuple.getSecond().onSwitch();
        rootStage.setScene(tuple.getFirst());
    }


    public void switchScene(String url, int width, int height) {
        Tuple<Scene, BaseController> tuple = scenes.computeIfAbsent(url, u -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(u));
            try {
                Pane p = loader.load();
                BaseController controller = loader.getController();
                controller.setSceneManager(this);
                controller.setGameModel(gameModel);
                controller.init();
                return new Tuple<>(new Scene(p, width, height), controller);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        tuple.getSecond().onSwitch();
        rootStage.setScene(tuple.getFirst());
    }

    public Stage getRootStage() {
        return rootStage;
    }
}
