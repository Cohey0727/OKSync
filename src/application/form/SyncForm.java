package application.form;

import application.common.LayoutSize;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class SyncForm extends JavaForm {
    private static final String TITLE = "Sync";
    private static final String ICON = "logo.jpg";
    private static final String[] CSS = { "sync-dialog.css" };
    private static final LayoutSize size = new LayoutSize(600, 480);

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
    protected LayoutSize getLayoutSize() {
        return size;
    }

    @Override
    protected Parent getParent() {
        Pane pane = new Pane();
        pane.setId("main-pane");
        ComboBox<String> cb = new ComboBox<String>();
        Label syncLabel = new Label("Sync to ");
        syncLabel.setId("sync-lable");
        syncLabel.setLayoutX(24);
        syncLabel.setLayoutY(44.0);
        cb.getItems().add("Clipboard");
        cb.getSelectionModel().select(0);
        cb.setLayoutX(100);
        cb.setLayoutY(40);
        cb.setMinWidth(300);
        cb.setMaxWidth(300);
        Button botton = new Button("Execute");
        botton.setLayoutX(420);
        botton.setLayoutY(40);
        TabPane tabPane = new TabPane();
        tabPane.setLayoutX(0);
        tabPane.setLayoutY(80);
        tabPane.getTabs().add(new Tab("Table Mapping"));
        tabPane.getTabs().add(new Tab("Value Mapping"));
        pane.getChildren().add(syncLabel);
        pane.getChildren().add(cb);
        pane.getChildren().add(botton);
        pane.getChildren().add(tabPane);
        return pane;
    }

}
