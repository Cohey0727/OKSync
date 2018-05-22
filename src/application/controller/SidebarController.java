package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.control.AcceleratableButton;
import application.form.AboutMeForm;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class SidebarController implements Initializable {

    @FXML
    private Button importButton;
    @FXML
    private Button openButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button preferenceButton;
    @FXML
    private AcceleratableButton testBtutton;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        testBtutton.setOnAction((e) -> {
            AboutMeForm form = new AboutMeForm();
            form.show();
        });
    }
}
