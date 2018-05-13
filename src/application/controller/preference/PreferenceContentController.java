package application.controller.preference;

import application.controller.PreferenceFormContoller;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public abstract class PreferenceContentController implements Initializable {
    private PreferenceFormContoller owner;

    public PreferenceFormContoller getOwner() {
        return owner;
    }

    public void setOwner(PreferenceFormContoller contoller) {
        this.owner = contoller;
    }

    protected Stage getStage() {
        return this.owner.getStage();
    }

    protected void close() {
        this.owner.getStage().close();
    }

}
