package hu.alkfejl.controller;

import hu.alkfejl.DAO.PlayerDAO;
import hu.alkfejl.DAO.SimplePlayerDAO;
import hu.alkfejl.model.PlayerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class OnePlayerTabController implements Initializable {

    /* parent controller */
    private TopListController topListController;
    private final PlayerDAO playerDAO = new SimplePlayerDAO();
    public TableView<PlayerModel> playerTv;
    public TableColumn<PlayerModel, String> nameCol;
    public TableColumn<PlayerModel, Integer> scoreCol;
    public TextField nameTF;
    public TextField scoreTF;
    public Button saveBtn;
    public Button updateBtn;
    public Button deleteBtn;

    public void setTopListController(TopListController topListController) {
        this.topListController = topListController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        showPlayers();
    }

    void showPlayers() {
        ObservableList<PlayerModel> players = FXCollections.observableArrayList(playerDAO.getAll());
        playerTv.setItems(players);
    }

    public void goBack(ActionEvent actionEvent) {
        topListController.goBack();
    }
}
