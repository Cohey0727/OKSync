package application.form.preference.content;

import application.common.LayoutSize;
import application.component.java.ConnectionDialog;
import application.form.JavaForm;
import javafx.scene.Parent;

public class ConnectionDialogForm extends JavaForm {
    private static final String TITLE = "Preference";
    private static final String ICON = "Settings.png";
    private static final String[] CSS = { "application.css", "preference.css" };
    private static final LayoutSize size = new LayoutSize(600, 384);

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

    @Override
    protected Parent getParent() {
        return new ConnectionDialog();
    }

    @Override
    protected LayoutSize getLayoutSize() {
        return size;
    }

    @Override
    protected void decolateStage() {
        getStage().setResizable(false);
    }

}
