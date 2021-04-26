package hu.alkfejl.controller;

import hu.alkfejl.model.Game;

public abstract class BaseController {
    protected SceneManager sceneManager;
    protected Game game;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void setGame(Game Game) {
        this.game = Game;
    }

    // called after fxml loads for the first time (inside SceneManager switchScene method..)
    // bind Games property to FXML injected fields inside this (and initialize controller's values)
    abstract public void init();

    // called on every scene switch
    abstract public void onSwitch();
}
