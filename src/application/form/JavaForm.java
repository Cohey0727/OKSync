package application.form;

import java.io.IOException;
import java.util.ArrayList;

import org.scenicview.ScenicView;

import application.common.AfterCloseAction;
import application.common.LayoutSize;
import common.system.SystemUtil;
import common.system.SystemUtil.ResourceType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class JavaForm implements Form {

    private Stage stage = new Stage();

    // Currently opening screen stage
    private final ArrayList<AfterCloseAction> afterColseActionList = new ArrayList<AfterCloseAction>();
    protected abstract String getTitle();
    protected abstract String[] getCss();
    protected abstract String getIcon();
    protected abstract Parent getParent();
    protected abstract LayoutSize getLayoutSize();

    @Override
    public Scene load() throws IOException {
        Scene scene = new Scene(getParent());
        return scene;
    }

    @Override
    public void show(Stage owner) {
        try {
            Scene scene = load();
            setCSS(scene);
            stage.setScene(scene);
            stage.getIcons().add(new Image(SystemUtil.getResourceURL(getIcon(), ResourceType.IMAGE).openStream()));
            stage.setTitle(getTitle());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setWidth(getLayoutSize().width);
            stage.setHeight(getLayoutSize().height);
            if (owner != null) {
                stage.initOwner(owner.getScene().getWindow());
            }
            decolateStage();
            ScenicView.show(scene);
            stage.showAndWait();
            afterColseActionList.forEach((action) -> {
                action.run();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void decolateStage() {
    }

    private void setCSS(Scene scene) {
        if (getCss() != null) {
            for (String css : getCss()) {
                scene.getStylesheets().add(SystemUtil.getResourceURL(css, ResourceType.CSS).toExternalForm());
            }
        }
    }
    /*
     * Close currently opening screen.
     */
    @Override
    public void close() {
        stage.close();
    };

    /*
     * Get currently opening screen stage.
     */
    public final Stage getStage() {
        return stage;
    }

    public void addAfterCloseAction(AfterCloseAction action) {
        afterColseActionList.add(action);
    }

}
