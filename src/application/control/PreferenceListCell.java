package application.control;

import common.ini.preference.Preference;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PreferenceListCell extends ListCell<Preference> {
    private final HBox hbox;
    private final ImageView image;
    private final VBox vbox;
    private final Text label;
    private final Text discripntion;

    public PreferenceListCell() {
        super();
        hbox = new HBox(5);
        image = new ImageView();
        image.setFitWidth(64);
        image.setFitHeight(64);
        vbox = new VBox(5);
        label = new Text();
        label.setFont(new Font("System Bold", 18));
        discripntion = new Text();
        VBox.setVgrow(label, Priority.NEVER);
        VBox.setVgrow(discripntion, Priority.ALWAYS);
        HBox.setHgrow(image, Priority.NEVER);
        HBox.setHgrow(vbox, Priority.ALWAYS);
        vbox.getChildren().addAll(label, discripntion);
        hbox.getChildren().addAll(image, vbox);
        label.wrappingWidthProperty().bind(vbox.widthProperty().subtract(5));
        discripntion.wrappingWidthProperty().bind(vbox.widthProperty().subtract(5));
    }

    @Override
    protected void updateItem(Preference item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            clearContent();
        } else {
            addContent(item);
        }
    }

    private void addContent(Preference item) {
        image.setImage(item.getImage());
        label.setText(item.getLabel());
        discripntion.setText(item.getDescription());
        setGraphic(hbox);
    }

    private void clearContent() {
        setText(null);
        setGraphic(null);
    }
}
