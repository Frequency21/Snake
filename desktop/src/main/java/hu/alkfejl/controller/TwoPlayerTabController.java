package hu.alkfejl.controller;

import hu.alkfejl.DAO.MultiPlayerDAO;
import hu.alkfejl.DAO.SimpleMultiPlayerDAO;
import hu.alkfejl.model.PTuple;
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
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class TwoPlayerTabController implements Initializable {

    /* parent controller */
    private TopListController topListController;
    private final MultiPlayerDAO mpDAO = new SimpleMultiPlayerDAO();
    public TableView<PTuple<PlayerModel, PlayerModel>> playersTv;
    public TableColumn<PTuple<PlayerModel, PlayerModel>, String> nameOneCol;
    public TableColumn<PTuple<PlayerModel, PlayerModel>, Integer> scoreOneCol;
    public TableColumn<PTuple<PlayerModel, PlayerModel>, String> nameTwoCol;
    public TableColumn<PTuple<PlayerModel, PlayerModel>, Integer> scoreTwoCol;
    public TextField nameOneTF;
    public TextField newNameTwoTF;
    public TextField scoreOneTF;
    public TextField nameTwoTF;
    public TextField newNameOneTF;
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
        nameTwoCol.setCellValueFactory(cellData -> cellData.getValue().getSecond().nameProperty());
        scoreTwoCol.setCellValueFactory(cellData -> cellData.getValue().getSecond().scoreProperty().asObject());
        showPlayers();
    }

    void showPlayers() {
        ObservableList<PTuple<PlayerModel, PlayerModel>> players =
                FXCollections.observableArrayList(mpDAO.getAll());
        playersTv.setItems(players);
    }

    public void goBack(ActionEvent actionEvent) {
        topListController.goBack();
    }

    public TopListController getTopListController() {
        return topListController;
    }

    public void save(ActionEvent actionEvent) {
        if (
                nameOneTF.getText().isEmpty()
                        || scoreOneTF.getText().isEmpty()
                        || nameTwoTF.getText().isEmpty()
                        || scoreTwoTF.getText().isEmpty()
        ) {
            // TODO: 2021. 05. 01. alert
        } else {
            PTuple<PlayerModel, PlayerModel> players = new PTuple<>(new PlayerModel(), new PlayerModel());
            players.getFirst().setName(nameOneTF.getText());
            players.getFirst().setScore(Integer.parseInt(scoreOneTF.getText()));
            players.getSecond().setName(nameTwoTF.getText());
            players.getSecond().setScore(Integer.parseInt(scoreTwoTF.getText()));
            mpDAO.save(players);
            clear();
            showPlayers();
        }
    }

    public void update(ActionEvent actionEvent) {
        if (
                nameOneTF.getText().isEmpty()
                        || scoreOneTF.getText().isEmpty()
                        || nameTwoTF.getText().isEmpty()
                        || scoreTwoTF.getText().isEmpty()
        ) {
            // TODO: 2021. 05. 01. alert
        } else {
            PTuple<PlayerModel, PlayerModel> players = new PTuple<>(new PlayerModel(), new PlayerModel());
            players.getFirst().setName(nameOneTF.getText());
            players.getFirst().setScore(Integer.parseInt(scoreOneTF.getText()));
            players.getSecond().setName(nameTwoTF.getText());
            players.getSecond().setScore(Integer.parseInt(scoreTwoTF.getText()));

            Tuple<String, String> newNames = new Tuple<>(
                    newNameOneTF.getText().isEmpty() ? nameOneTF.getText() : newNameOneTF.getText(),
                    newNameTwoTF.getText().isEmpty() ? nameTwoTF.getText() : newNameTwoTF.getText()
            );

            mpDAO.update(players, newNames);
            clear();
            showPlayers();
        }
    }

    public void delete(ActionEvent actionEvent) {
        if (nameOneTF.getText().isEmpty() || nameTwoTF.getText().isEmpty()) {
            // TODO: 2021. 05. 01. alert
        } else {
            PTuple<PlayerModel, PlayerModel> players = new PTuple<>(new PlayerModel(), new PlayerModel());
            players.getFirst().setName(nameOneTF.getText());
            players.getSecond().setName(nameTwoTF.getText());
            mpDAO.delete(players);
            clear();
            showPlayers();
        }
    }

    public void setTextFields(MouseEvent mouseEvent) {
        PTuple<PlayerModel, PlayerModel> playerTuple = playersTv.getSelectionModel().getSelectedItem();
        if (playerTuple == null) return;
        nameOneTF.setText(playerTuple.getFirst().getName());
        scoreOneTF.setText("" + playerTuple.getFirst().getScore());
        nameTwoTF.setText(playerTuple.getSecond().getName());
        scoreTwoTF.setText("" + playerTuple.getSecond().getScore());
    }

    private void clear() {
        nameOneTF.clear();
        newNameOneTF.clear();
        scoreOneTF.clear();
        nameTwoTF.clear();
        scoreTwoTF.clear();
        newNameTwoTF.clear();
    }

}
