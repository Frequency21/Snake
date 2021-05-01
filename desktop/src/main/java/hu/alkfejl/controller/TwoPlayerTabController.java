package hu.alkfejl.controller;

import hu.alkfejl.DAO.PlayerDAO;
import hu.alkfejl.DAO.SimplePlayerDAO;
import hu.alkfejl.model.PlayerModel;
import hu.alkfejl.model.Tuple;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TwoPlayerTabController implements Initializable {

    /* parent controller */
    private TopListController topListController;
    private final PlayerDAO playerDAO = new SimplePlayerDAO();
    public TableView<Tuple<PlayerModel, PlayerModel>> playersTv;
    public TableColumn<Tuple<PlayerModel, PlayerModel>, String> nameOneCol;
    public TableColumn<Tuple<PlayerModel, PlayerModel>, Integer> scoreOneCol;
    public TableColumn<Tuple<PlayerModel, PlayerModel>, String> nameTwoCol;
    public TableColumn<Tuple<PlayerModel, PlayerModel>, Integer> scoreTwoCol;
    public TextField nameOneTF;
    public TextField scoreOneTF;
    public TextField nameTwoTF;
    public TextField scoreTwoTF;
    public Button saveBtn;
    public Button updateBtn;
    public Button deleteBtn;

    public void setTopListController(TopListController topListController) {
        this.topListController = topListController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameOneCol.setCellValueFactory(cellData -> cellData.getValue().getFirst().nameProperty());
        scoreOneCol.setCellValueFactory(cellData -> cellData.getValue().getFirst().scoreProperty().asObject());
        nameTwoCol.setCellValueFactory(cellData -> cellData.getValue().getFirst().nameProperty());
        scoreTwoCol.setCellValueFactory(cellData -> cellData.getValue().getFirst().scoreProperty().asObject());
        showPlayers();
    }

    void showPlayers() {
        ObservableList<Tuple<PlayerModel, PlayerModel>> players =
                FXCollections.observableArrayList(playerDAO.getAllMultiPlayer());
        playersTv.setItems(players);
    }

    public void goBack(ActionEvent actionEvent) {
        topListController.goBack();
    }

    public TopListController getTopListController() {
        return topListController;
    }
}
