package application.form;

public class PreferenceForm extends FXMLForm {
    private static final String TITLE = "Preference";
    private static final String FXML = "preference.fxml";
    private static final String[] CSS = { "application.css" };
    private static final String ICON = "Settings.png";

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