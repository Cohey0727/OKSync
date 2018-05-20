package application.form;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import application.common.AfterCloseAction;
import application.controller.AbstractFormController;
import application.controller.preference.content.ConnectionDialogController;
import common.system.SystemUtil;
import common.system.SystemUtil.ResourceType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.AcceleratableButton;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class FXMLForm implements Form {

    protected final ConnectionDialogController controller = new ConnectionDialogController();

    private Stage stage = new Stage();
    private final ArrayList<AfterCloseAction> afterColseActionList = new ArrayList<AfterCloseAction>();

    protected abstract String getFxml();
    protected abstract String getTitle();
    protected abstract String[] getCss();
    protected abstract String getIcon();

    @Override
    public void show(Stage owner) {
        try {
            Scene scene = load();
            setCSS(scene);
            stage.getIcons().add(new Image(SystemUtil.getResourceURL(getIcon(), ResourceType.IMAGE).openStream()));
            stage.setTitle(getTitle());
            stage.setScene(scene);
            //            ScenicView.show(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            if (owner != null) {
                stage.initOwner(owner.getScene().getWindow());
            }
            decolateScene(scene);
            decolateStage(stage);
            stage.showAndWait();
            afterColseActionList.forEach((action) -> {
                action.run();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    private void decolateScene(Scene scene) {

    }
    protected void decolateStage(Stage stage) {

    }

    private void setCSS(Scene scene) {
        if (getCss() != null) {
            for (String css : getCss()) {
                scene.getStylesheets().add(SystemUtil.getResourceURL(css, ResourceType.CSS).toExternalForm());
            }
        }
    }

    @Override
    public Scene load() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SystemUtil.getResourceURL(getFxml(), ResourceType.FXML));
        Parent root = (Parent) fxmlLoader.load();
        AbstractFormController controller = fxmlLoader.getController();
        controller.setForm(this);
        Scene scene = new Scene(root);
        return scene;
    }

    public void addAfterCloseAction(AfterCloseAction action) {
        afterColseActionList.add(action);
    }

    /*
     * Close currently opening screen.
     */
    @Override
    public void close() {
        stage.close();
    };

    public final Stage getStage() {
        return stage;
    }

    public void getAcceleratable() {
        StringBuilder sb = new StringBuilder();
        sb.append("Class: " + this.getClass().getCanonicalName() + "\n");
        sb.append("Settings:\n");
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.get(this) instanceof AcceleratableButton) {
                    sb.append(field.getName() + " = " + field.get(this) + "\n");
                }
            } catch (Exception e) {
                sb.append(field.getName() + " = " + "access denied\n");
            }
        }
    }
}
