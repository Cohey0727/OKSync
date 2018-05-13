package application.form;

public class GluonTest extends FXMLForm {

    private static final String TITLE = "OKSync";
    private static final String FXML = "Gluon.fxml";
    private static final String[] CSS = {};
    private static final String ICON = "logo.jpg";

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
