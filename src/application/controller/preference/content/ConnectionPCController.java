package application.controller.preference.content;

import java.net.URL;
import java.util.ResourceBundle;

import application.controller.preference.PreferenceContentController;
import application.form.preference.content.ConnectionDialogForm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class ConnectionPCController extends PreferenceContentController {
    @FXML
    Button newButton;
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;
    @FXML
    TableView<String> mainTable;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newButton.setOnAction((e) -> {
            ConnectionDialogForm form = new ConnectionDialogForm();
            form.show(getStage());
        });
    }

}
