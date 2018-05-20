package application.form;

import application.common.LayoutSize;
import common.ini.version.VersionManager;
import common.system.SystemUtil;
import common.system.SystemUtil.ResourceType;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

public class AboutMeForm extends JavaForm {
    private static final String TITLE = "About OKSync";
    private static final String ICON = "logo.jpg";
    private static final String[] CSS = { "application.css" };
    private static final LayoutSize size = new LayoutSize(620, 408);
    private double offsetX = 0;
    private double offsetY = 0;

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
    protected void decolateStage() {
        getStage().setWidth(620);
        getStage().setHeight(408);
        getStage().setResizable(false);
        getStage().initStyle(StageStyle.TRANSPARENT);
        getStage().getScene().setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.F1 || e.getCode() == KeyCode.ESCAPE) {
                close();
            }
        });
    }

    @Override
    protected Parent getParent() {
        return getPane();
    }

    private Pane getPane() {
        Pane pane = new Pane();
        pane.setId("AboutMe");
        ImageView iv = new ImageView();
        Image im;
        try {
            im = new Image(SystemUtil.getResourceURL("full-logo.jpg", ResourceType.IMAGE).openStream());
            iv.setImage(im);
            pane.getChildren().add(iv);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Text version = new Text();
        version.setText("Ver " + VersionManager.getLatestVersion().toString());
        version.setFill(Color.valueOf("#293144"));
        version.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
        version.setLayoutX(12.0);
        version.setLayoutY(400.0);
        version.setWrappingWidth(184);
        pane.getChildren().add(version);
        Text closeText = new Text();
        closeText.setText("Press F1 or ESC to close");
        closeText.setFill(Color.valueOf("#495164"));
        closeText.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
        closeText.setLayoutX(440.0);
        closeText.setLayoutY(400.0);
        closeText.setWrappingWidth(184);
        pane.getChildren().add(closeText);
        pane.setOnMousePressed(event -> {
            offsetX = event.getSceneX();
            offsetY = event.getSceneY();
        });
        pane.setOnMouseDragged(event -> {
            getStage().setX(event.getScreenX() - offsetX);
            getStage().setY(event.getScreenY() - offsetY);
        });
        return pane;
    }

}
