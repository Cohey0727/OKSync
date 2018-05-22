package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.control.PreferenceListCell;
import application.controller.preference.PreferenceContentController;
import common.ini.preference.Preference;
import common.ini.preference.PreferenceDao;
import common.system.SystemUtil;
import common.system.SystemUtil.ResourceType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class PreferenceFormContoller extends AbstractFormController {

    @FXML
    private ListView<Preference> itemList;
    @FXML
    private AnchorPane contentArea;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    private static final String XML_PREFIX = "preference/";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadItemList();
        inializeEvent();
        loadContent();
    }

    private void inializeEvent() {
        itemList.setOnMouseClicked((e) -> {
            switch (e.getClickCount()) {
            case 1:
                break;
            case 2:
                loadContent();
                break;
            default:
                break;
            }
        });
        cancelButton.setOnAction((e) -> {
            close();
        });
        okButton.setOnAction((e) -> {
        });

    }

    private void loadItemList() {
        itemList.setCellFactory(param -> new PreferenceListCell());
        itemList.getItems().addAll(PreferenceDao.load());
    }

    private void loadContent() {
        contentArea.getChildren().clear();
        Preference preference = itemList.getSelectionModel().getSelectedItem() != null
                ? itemList.getSelectionModel().getSelectedItem()
                : itemList.getItems().get(0);
        try {
            contentArea.getChildren().add(getPane(preference));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AnchorPane getPane(Preference preference) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                SystemUtil.getResourceURL(XML_PREFIX + preference.getXml(), ResourceType.FXML));
        AnchorPane pane = (AnchorPane) fxmlLoader.load();
        PreferenceContentController controller = (PreferenceContentController) fxmlLoader.getController();
        controller.setOwner(this);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        return pane;
    }
}
