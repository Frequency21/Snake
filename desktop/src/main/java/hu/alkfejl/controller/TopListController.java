package hu.alkfejl.controller;

import hu.alkfejl.DAO.PlayerDAO;
import hu.alkfejl.DAO.SimplePlayerDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.ResourceBundle;

public class TopListController extends GameController implements Initializable {
    @FXML
    private Tab onePlayer;
    @FXML
    private Tab twoPlayer;
    @FXML
    private OnePlayerTabController onePlayerTabController;
    @FXML
    private TwoPlayerTabController twoPlayerTabController;
    private PlayerDAO playerDAO = new SimplePlayerDAO();

    @Override
    public void init() { }

    @Override
    public void onSwitch() {
        onePlayerTabController.showPlayers();
        twoPlayerTabController.showPlayers();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onePlayerTabController.setTopListController(this);
        twoPlayerTabController.setTopListController(this);
    }

    public void goBack() { sceneManager.switchScene("../starting.fxml"); }
}
