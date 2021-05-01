package hu.alkfejl.controller;

import hu.alkfejl.model.Game;
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
    // but Game should never
    private final Game Game;
    // hold reference both to Scene and its controller (onSwitch and init callbacks)
    private final Map<String, Tuple<Scene, GameController>> gameScenes = new HashMap<>();

    public SceneManager(Stage rootStage, Game Game) {
        if (rootStage == null) throw new IllegalArgumentException();
        this.rootStage = rootStage;
        this.Game = Game;
    }

    // scene size will match board size
    public void switchScene(String url) {
        Tuple<Scene, GameController> tuple = gameScenes.computeIfAbsent(url, u -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(u));
            try {
                Pane p = loader.load();
                GameController controller = loader.getController();
                controller.setSceneManager(this);
                controller.setGame(Game);
                controller.init();
                return new Tuple<>(new Scene(p, Game.getSizePx(), Game.getSizePx()), controller);
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
