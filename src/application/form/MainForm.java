package application.form;

import java.io.File;

public class MainForm extends FXMLForm {

    private static final String TITLE = "OKSync";
    private static final String FXML = "OKSyncMainForm.fxml";
    private static final String[] CSS = { "application.css" };
    private static final String ICON = "logo.jpg";
    private static File file = null;

    @Override
    protected String getFxml() {
        return FXML;
    }

    @Override
    protected String getTitle() {
        return TITLE;
    }

    @Override
    protected String[] getCss() {
        return CSS;
    }

    @Override
    protected String getIcon() {
        return ICON;
    }
}
