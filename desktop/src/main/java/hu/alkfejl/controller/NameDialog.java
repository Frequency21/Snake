package hu.alkfejl.controller;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;


public class NameDialog extends Dialog<String> {
    private boolean nameSet = false;

    public NameDialog() {
        setTitle("Add meg a neved!");

        ButtonType loginButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField playerOneTF = new TextField();
        playerOneTF.setPromptText("Python");

        grid.add(new Label("Első játékos:"), 0, 0);
        grid.add(playerOneTF, 1, 0);

        getDialogPane().setContent(grid);

        setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                String result = playerOneTF.getText();
                if (result != null && !result.isEmpty())
                    nameSet = true;
                return playerOneTF.getText();
            }
            nameSet = false;
            return null;
        });

        initModality(Modality.APPLICATION_MODAL);
    }

    public boolean isNameSet() { return nameSet; }

    public void setNameSet(boolean nameSet) { this.nameSet = nameSet; }
}
