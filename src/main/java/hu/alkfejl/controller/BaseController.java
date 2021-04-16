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

    // called after fxml loads for the first time (inside SceneManager switchScene method..)
    // bind gameModels property to FXML injected fields inside this (and initialize controller's values)
    abstract public void init();

    // called on every scene switch
    abstract public void onSwitch();
}
