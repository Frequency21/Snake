package hu.alkfejl.controller;

import hu.alkfejl.model.GameModel;

public abstract class BaseController {
    protected SceneManager sceneManager;
    protected GameModel gameModel;
    protected boolean initialized = false;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    // called after fxml loads inside SceneManager switchScene method..
    abstract public void init();
}
