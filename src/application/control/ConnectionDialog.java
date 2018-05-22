package application.control;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ConnectionDialog extends Pane {
    private Button saveButton = new Button();
    private Button cancelButton = new Button();

    private Button testButton = new Button();
    private TextField nameField = new TextField();
    private TextField hostField = new TextField();
    private TextField sidField = new TextField();
    private TextField portField = new TextField();
    private TextField userField = new TextField();
    private PasswordField passField = new PasswordField();

    public ConnectionDialog() {
        init();
    }

    private void init() {
        nameField.setLayoutX(160.0);
        nameField.setLayoutY(40.0);
        nameField.setPrefSize(400.0, 32.0);
        nameField.setPromptText("Name");
        this.getChildren().add(nameField);

        hostField.setLayoutX(160.0);
        hostField.setLayoutY(100.0);
        hostField.setPrefSize(400.0, 32.0);
        hostField.setPromptText("Host");
        this.getChildren().add(hostField);

        sidField.setLayoutX(160.0);
        sidField.setLayoutY(160.0);
        sidField.setPrefSize(192.0, 32.0);
        sidField.setPromptText("SID");
        this.getChildren().add(sidField);

        portField.setLayoutX(368.0);
        portField.setLayoutY(160.0);
        portField.setPrefSize(192.0, 32.0);
        portField.setPromptText("Port");
        this.getChildren().add(portField);

        userField.setLayoutX(160.0);
        userField.setLayoutY(220.0);
        userField.setPrefSize(192.0, 32.0);
        userField.setPromptText("User");
        this.getChildren().add(userField);

        passField.setLayoutX(368.0);
        passField.setLayoutY(220.0);
        passField.setPrefSize(192.0, 32.0);
        passField.setPromptText("Password");
        this.getChildren().add(passField);

        saveButton.setLayoutX(30.0);
        saveButton.setLayoutY(285.0);
        saveButton.setPrefSize(192, 32);
        saveButton.setText("SAVE");
        this.getChildren().add(saveButton);

        cancelButton.setLayoutX(232.0);
        cancelButton.setLayoutY(285.0);
        cancelButton.setPrefSize(192, 32);
        cancelButton.setText("CANCEL");
        this.getChildren().add(cancelButton);

        testButton.setLayoutX(464.0);
        testButton.setLayoutY(285.0);
        testButton.setPrefSize(96, 32);
        testButton.setText("TEST");
        this.getChildren().add(testButton);

        Label nameLabel = new Label();
        nameLabel.setLayoutX(34.0);
        nameLabel.setLayoutY(41.0);
        nameLabel.setPrefHeight(31.0);
        nameLabel.setPrefWidth(92.0);
        nameLabel.setText("NAME");
        this.getChildren().add(nameLabel);

        Label hostLabel = new Label();
        hostLabel.setLayoutX(34.0);
        hostLabel.setLayoutY(101.0);
        hostLabel.setPrefHeight(31.0);
        hostLabel.setPrefWidth(92.0);
        hostLabel.setText("HOST");
        this.getChildren().add(hostLabel);

        Label sidPortLabel = new Label();
        sidPortLabel.setLayoutX(34.0);
        sidPortLabel.setLayoutY(161.0);
        sidPortLabel.setPrefHeight(31.0);
        sidPortLabel.setPrefWidth(92.0);
        sidPortLabel.setText("SID / PORT");
        this.getChildren().add(sidPortLabel);

        Label userPassLabel = new Label();
        userPassLabel.setLayoutX(34.0);
        userPassLabel.setLayoutY(221.0);
        userPassLabel.setPrefHeight(31.0);
        userPassLabel.setPrefWidth(92.0);
        userPassLabel.setText("USER / PASS");
        this.getChildren().add(userPassLabel);

        Label slash1 = new Label();
        slash1.setLayoutX(356.0);
        slash1.setLayoutY(161.0);
        slash1.setPrefHeight(31.0);
        slash1.setPrefWidth(8.0);
        slash1.setText("/");
        this.getChildren().add(slash1);

        Label slash2 = new Label();
        slash2.setLayoutX(356.0);
        slash2.setLayoutY(221.0);
        slash2.setPrefHeight(31.0);
        slash2.setPrefWidth(8.0);
        slash2.setText("/");
        this.getChildren().add(slash2);

    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getTestButton() {
        return testButton;
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getHostField() {
        return hostField;
    }

    public TextField getSidField() {
        return sidField;
    }

    public TextField getPortField() {
        return portField;
    }

    public TextField getUserField() {
        return userField;
    }

    public PasswordField getPassField() {
        return passField;
    }

}
