package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.form.FXMLForm;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public abstract class AbstractFormController implements Initializable {
    private FXMLForm form;

    public FXMLForm getForm() {
        return form;
    }

    public Stage getStage() {
        return form.getStage();
    }

    public void close() {
        form.close();
    }

    public void setForm(FXMLForm form) {
        this.form = form;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
