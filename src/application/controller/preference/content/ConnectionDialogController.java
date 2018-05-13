package application.controller.preference.content;

import java.net.URL;
import java.util.ResourceBundle;

import application.component.java.ConnectionDialog;
import application.controller.AbstractFormController;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ConnectionDialogController extends AbstractFormController {
    private ConnectionDialog dialog = new ConnectionDialog();
    private Button saveButton = dialog.getSaveButton();
    private Button cancelButton = dialog.getCancelButton();
    private Button testButton = dialog.getTestButton();
    private TextField nameField = dialog.getNameField();
    private TextField hostField = dialog.getHostField();
    private TextField sidField = dialog.getSidField();
    private TextField portField = dialog.getPortField();
    private TextField userField = dialog.getUserField();
    private PasswordField passField = dialog.getPassField();
    private int id = -1;

    public ConnectionDialogController() {
        initialize(null, null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        portField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    Integer.valueOf(newValue);
                } catch (NumberFormatException e) {
                    portField.setText(oldValue);
                }
            }
        });

        saveButton.setOnAction((e) -> {
        });

        cancelButton.setOnAction((e) -> {
            close();
        });
    }

    private void formatCheck() {
        String name = nameField.getText();
        if (name.isEmpty()) {
        }
        String host = hostField.getText();
        if (host.isEmpty()) {
        }

        String sid = sidField.getText();
        if (sid.isEmpty()) {
        }

        String port = portField.getText();
        if (port.isEmpty()) {
        }

        String user = userField.getText();
        if (user.isEmpty()) {
        }

        String pass = passField.getText();
        if (sid.isEmpty()) {
        }
    }

    public ConnectionDialog getDialog() {
        return dialog;
    }

}
