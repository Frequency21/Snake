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
import javafx.scene.input.MouseEvent;

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
    public TextField newNameTF;
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

    public void save(ActionEvent actionEvent) {
        if (
                nameTF.getText().isEmpty()
                || scoreTF.getText().isEmpty()
        ) {
            // TODO: 2021. 05. 01. alert
        } else {
            PlayerModel player = new PlayerModel();
            player.setName(nameTF.getText());
            player.setScore(Integer.parseInt(scoreTF.getText()));
            playerDAO.save(player);
            clear();
            showPlayers();
        }
    }


    public void update(ActionEvent actionEvent) {
        if (
                nameTF.getText().isEmpty()
                || scoreTF.getText().isEmpty()
        ) {
            // TODO: 2021. 05. 01. alert
        } else {
            PlayerModel player = new PlayerModel();
            player.setName(nameTF.getText());
            player.setScore(Integer.parseInt(scoreTF.getText()));
            playerDAO.update(player, newNameTF.getText().isEmpty() ? nameTF.getText() : newNameTF.getText());
            clear();
            showPlayers();
        }
    }

    public void delete(ActionEvent actionEvent) {
        if (
                nameTF.getText().isEmpty()
        ) {
            // TODO: 2021. 05. 01. alert
        } else {
            PlayerModel player = new PlayerModel();
            player.setName(nameTF.getText());
            playerDAO.delete(player);
            clear();
            showPlayers();
        }
    }

    private void clear() {
        nameTF.clear();
        newNameTF.clear();
        scoreTF.clear();
    }

    public void setTextFields(MouseEvent mouseEvent) {
        PlayerModel player = playerTv.getSelectionModel().getSelectedItem();
        if (player == null) return;
        nameTF.setText(player.getName());
        scoreTF.setText("" + player.getScore());
    }
}
