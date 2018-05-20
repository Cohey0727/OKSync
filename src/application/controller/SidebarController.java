package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SidebarController {

    @FXML
    private Button importButton;
    @FXML
    private Button openButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button preferenceButton;

    public void setOwner(MainController mainController) {
        importButton.setOnAction((e) -> {
            mainController.importButton.fire();
        });
        openButton.setOnAction((e) -> {
            mainController.openButton.fire();
        });
        saveButton.setOnAction((e) -> {
            mainController.saveButton.fire();
        });
        preferenceButton.setOnAction((e) -> {
            mainController.preferenceButton.fire();
        });
    }

}
