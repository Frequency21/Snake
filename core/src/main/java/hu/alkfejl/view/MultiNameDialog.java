package hu.alkfejl.view;

import hu.alkfejl.model.Tuple;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;


public class MultiNameDialog extends Dialog<Tuple<String, String>> {
    private boolean isNamesSet = false;

    public MultiNameDialog() {
        setTitle("Addjátok meg a neveiteket");

        ButtonType loginButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField playerOneTF = new TextField();
        playerOneTF.setPromptText("Python");
        TextField playerTwoTF = new TextField();
        playerTwoTF.setPromptText("Anaconda");

        grid.add(new Label("Első játékos:"), 0, 0);
        grid.add(playerOneTF, 1, 0);
        grid.add(new Label("Második játékos:"), 0, 1);
        grid.add(playerTwoTF, 1, 1);

        getDialogPane().setContent(grid);

        setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                if (!playerOneTF.getText().isEmpty() || !playerTwoTF.getText().isEmpty()) {
                    isNamesSet = true;
                    return new Tuple<>(playerOneTF.getText(), playerTwoTF.getText());
                }
            }
            isNamesSet = false;
            return null;
        });

        initModality(Modality.APPLICATION_MODAL);
    }

    public boolean isNamesSet() {
        return isNamesSet;
    }
}
