package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import application.component.java.SqlTab;
import application.form.AboutMeForm;
import application.form.PreferenceForm;
import application.form.SyncForm;
import common.db.DBManager;
import common.db.DataSourceFactory;
import common.system.SystemUtil;
import common.system.SystemUtil.ResourceType;
import common.system.component.TargetTableView;
import common.system.data.FileIOManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController extends AbstractFormController {

    private static DBManager context = new DBManager();

    @FXML
    private MenuItem importButton;
    @FXML
    private MenuItem preferenceButton;
    @FXML
    private MenuItem saveButton;
    @FXML
    private MenuItem openButton;
    @FXML
    private MenuItem closeButton;
    @FXML
    private CheckMenuItem displayEmptyTable;
    @FXML
    private MenuItem executeSqlButton;
    @FXML
    private MenuItem addAndEqualButton;
    @FXML
    private MenuItem addOrEqualButton;
    @FXML
    private MenuItem addAndLikeButton;
    @FXML
    private MenuItem addOrLikeButton;
    @FXML
    private MenuItem copyButton;
    @FXML
    private MenuItem tabCloseButton;
    @FXML
    private MenuItem aboutMeMenu;
    @FXML
    private MenuItem leftAlignButton;
    @FXML
    private MenuItem rightAlignButton;
    @FXML
    private ListView<String> tableListView;
    private ObservableList<String> tableList = FXCollections.observableArrayList();
    @FXML
    private TextField tableNameSearchArea;
    @FXML
    private MenuItem synchronizeButton;
    @FXML
    private TabPane sqlTabArea;
    @FXML
    private BorderPane dragArea;
    @FXML
    private JFXHamburger sideBarHambuger;
    @FXML
    private JFXDrawer sideDrawer;
    //    private AnchorPane titleBar;
    //    @FXML
    //    private Button closeSysButton;
    //    @FXML
    //    private Button minimizeSysButton;
    //    @FXML
    //    private Button maximizeSysButton;
    //    @FXML
    //    private Label titleLable;

    private double offsetX = 0;
    private double offsetY = 0;

    @FXML
    private void filterTableList(KeyEvent e) {
        String tableNm = tableNameSearchArea.getText().toUpperCase();
        if (tableNm == null || tableNm.length() == 0) {
            tableListView.setItems(tableList.filtered(s -> true));
        } else {
            tableListView.setItems(tableList.filtered(s -> s.contains(tableNm)));
        }
    }

    public void executeSQL() {
        try {
            SqlTab sqlTab = (SqlTab) sqlTabArea.getSelectionModel().getSelectedItem();
            sqlTab.executeSQL();
        } catch (Exception e) {
            e.printStackTrace();
            alert(e);
        }
    }

    private void alert(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(e.getCause().getMessage());
        alert.setHeaderText(e.toString().replaceAll("\\:", "\n"));
        alert.setContentText(e.getMessage().replaceAll("\\(", "\n\\("));
        alert.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableListView();
        InitializesqlTabArea();
        InitializeFileMenu();
        InitializeEditMenu();
        InitializeHelpMenu();
        InitializeSideBar();
    }

    private void InitializeSideBar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SystemUtil.getResourceURL("sidebar.fxml", ResourceType.FXML));
            AnchorPane sideBar = (AnchorPane) fxmlLoader.load();
            sideDrawer.setSidePane(sideBar);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(sideBarHambuger);
        task.setRate(-1);
        sideBarHambuger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            task.setRate(task.getRate() * -1);
            task.play();

            if (sideDrawer.isOpened()) {
                sideDrawer.close();
            } else {
                sideDrawer.open();
            }
        });
    }

    private void reloadTableData() {
        tableList = FXCollections.observableArrayList();
        tableList.addAll(context.getTableList(displayEmptyTable.isSelected()));
        tableList.sort((a, b) -> a.compareTo(b));
        tableListView.setItems(tableList);
        filterTableList(null);
    }

    private void loadData() {
        File file = new File("");
        if (file != null && file.exists()) {
            if (file.getAbsolutePath().endsWith(".csv")) {
                importFile(file);
            }
            if (file.getAbsolutePath().endsWith(".db")) {
                try {
                    DataSourceFactory.updateURL(file.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setTitle(file.getName());
                reloadTableData();
            }
        }
    }

    private SqlTab createNewTab(String tableNm) {
        SqlTab st = new SqlTab(tableNm, (e) -> {
            if (KeyCode.F7.equals(e.getCode())) {
                executeSQL();
            }
        });
        sqlTabArea.getTabs().add(st);
        dragArea.setVisible(false);
        return st;
    }

    private void initializeTableListView() {
        tableListView.setOnMouseClicked((e) -> {
            switch (e.getClickCount()) {
            case 1:
                break;
            case 2:
                String tableNm = tableListView.getSelectionModel().getSelectedItem() != null
                        ? tableListView.getSelectionModel().getSelectedItem()
                        : tableListView.getItems().get(0);
                sqlTabArea.getSelectionModel().select(createNewTab(tableNm));
                executeSQL();
                break;
            default:
                break;
            }
        });
        tableListView.setOnKeyPressed((e) -> {
            if (KeyCode.ENTER.equals(e.getCode())) {
                String tableNm = tableListView.getSelectionModel().getSelectedItem() != null
                        ? tableListView.getSelectionModel().getSelectedItem()
                        : tableListView.getItems().get(0);
                sqlTabArea.getSelectionModel().select(createNewTab(tableNm));
                executeSQL();
            }
        });

    }

    private void InitializesqlTabArea() {
        dragArea.setOnDragOver(event -> {
            Dragboard board = event.getDragboard();
            dragArea.setStyle("-fx-background-color: #9A9A9A;");
            if (board.hasFiles()) {
                if (board.getFiles().stream().filter(file -> file.getAbsolutePath().endsWith(".csv")).count() >= 1) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else if (board.getFiles().stream().filter(file -> file.getAbsolutePath().endsWith(".db"))
                        .count() == 1) {
                    event.acceptTransferModes(TransferMode.LINK);
                }
            }
        });
        dragArea.setOnDragExited(event -> {
            dragArea.setStyle("-fx-background-color: rgbs(0,0,0,0);");
        });
        dragArea.setOnDragDropped(event -> {
            Dragboard board = event.getDragboard();
            if (board.hasFiles()) {
                if (board.getFiles().stream().filter(file -> file.getAbsolutePath().endsWith(".csv")).count() >= 1) {
                    board.getFiles().stream().filter(file -> file.getAbsolutePath().endsWith(".csv")).forEach((file) -> {
                        importFile(file);
                    });
                } else if (board.getFiles().stream().filter(file -> file.getAbsolutePath().endsWith(".db")).count() == 1) {
                    File dbFile = board.getFiles().stream().filter(file -> file.getAbsolutePath().endsWith(".db")).findFirst().get();
                    try {
                        DataSourceFactory.updateURL(dbFile.getAbsolutePath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setTitle(dbFile.getName());
                    reloadTableData();
                }
            }
        });
    }

    private void InitializeFileMenu() {
        importButton.setOnAction((e) -> {
            String title = "Open Resource File";
            ExtensionFilter[] extensionFilters = { new ExtensionFilter("Data Files", "*.csv", "*.dat"),
                    new ExtensionFilter("All Files", "*.*") };
            File file = FileIOManager.open(getStage(), title, extensionFilters);
            if (file == null)
                return;
            importFile(file);
        });
        importButton.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));
        preferenceButton.setOnAction((e) -> {
            PreferenceForm form = new PreferenceForm();
            form.show(getStage());
        });
        preferenceButton.setAccelerator(new KeyCodeCombination(KeyCode.F10));
        closeButton.setOnAction((e) -> {
            close();
        });
        closeButton.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        openButton.setOnAction((e) -> {
            String title = "Open DB File";
            ExtensionFilter[] extensionFilters = { new ExtensionFilter("DB Files", "*.db"),
                    new ExtensionFilter("All Files", "*.*") };
            File file = FileIOManager.open(getStage(), title, extensionFilters);
            if (file == null)
                return;
            try {
                DataSourceFactory.updateURL(file.getAbsolutePath());
                setTitle(file.getName());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            setTitle(file.getName());
            reloadTableData();
            System.out.println("DB File is opened");
        });
        openButton.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        saveButton.setOnAction((e) -> {
            String title = "Save DB File";
            File target = new File(DataSourceFactory.getProp("path"));
            ExtensionFilter[] extensionFilters = { new ExtensionFilter("DB Files", "*.db"),
                    new ExtensionFilter("All Files", "*.*") };
            File file = FileIOManager.save(getStage(), title, target, extensionFilters);
            if (file == null)
                return;
            try {
                DataSourceFactory.updateURL(file.getAbsolutePath());
                setTitle(file.getName());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            System.out.println("DB File is saved");
        });
        saveButton.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
    }

    private void InitializeEditMenu() {
        displayEmptyTable.setOnAction((e) -> {
            reloadTableData();
        });

        executeSqlButton.setOnAction((e) -> {
            executeSQL();
        });
        executeSqlButton.setAccelerator(new KeyCodeCombination(KeyCode.F7));

        addAndEqualButton.setOnAction((e) -> {
            ((SqlTab) sqlTabArea.getSelectionModel().getSelectedItem())
                    .addAndEqual();
        });
        addAndEqualButton.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));

        addOrEqualButton.setOnAction((e) -> {
            ((SqlTab) sqlTabArea.getSelectionModel().getSelectedItem()).addOrEqual();
        });
        addOrEqualButton.setAccelerator(
                new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

        addAndLikeButton.setOnAction((e) -> {
            ((SqlTab) sqlTabArea.getSelectionModel().getSelectedItem()).addAndLike();
        });
        addAndLikeButton.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));

        addOrLikeButton.setOnAction((e) -> {
            ((SqlTab) sqlTabArea.getSelectionModel().getSelectedItem()).addOrLike();
        });
        addOrLikeButton.setAccelerator(
                new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

        copyButton.setOnAction((e) -> {
            final ClipboardContent content = new ClipboardContent();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            String target = ((SqlTab) sqlTabArea.getSelectionModel().getSelectedItem()).getSelectedCellText();
            if (target != null && !target.isEmpty()) {
                content.putString(target);
                clipboard.setContent(content);
            }
        });
        copyButton.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));

        tabCloseButton.setOnAction((e) -> {
            sqlTabArea.getTabs().remove(sqlTabArea.getSelectionModel().getSelectedItem());
        });
        tabCloseButton.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));

        synchronizeButton.setOnAction((e) -> {
            try {
                StringBuilder sb = new StringBuilder();
                for (Tab sqlTsab : sqlTabArea.getTabs()) {
                    sb.append(((SqlTab) sqlTsab).getInsertSQL());
                }
                final ClipboardContent content = new ClipboardContent();
                Clipboard clipboard = Clipboard.getSystemClipboard();
                content.putString(sb.toString());
                clipboard.setContent(content);
                TargetTableView ttv = new TargetTableView();
                SyncForm form = new SyncForm();
                form.show(getStage());
            } catch (Throwable e1) {
                System.out.println(e1.getMessage());
            }
        });
        synchronizeButton.setAccelerator(new KeyCodeCombination(KeyCode.F2));

    }

    private void InitializeHelpMenu() {
        aboutMeMenu.setOnAction((e) -> {
            AboutMeForm form = new AboutMeForm();
            form.show(getStage());
        });
        aboutMeMenu.setAccelerator(new KeyCodeCombination(KeyCode.F1));
        rightAlignButton.setOnAction((e) -> {
            AboutMeForm form = new AboutMeForm();
            form.show(getStage());
        });
        rightAlignButton.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        leftAlignButton.setOnAction((e) -> {
            AboutMeForm form = new AboutMeForm();
            form.show(getStage());
        });
        //        leftAlignButton.setAccelerator(new KeyCodeCombination(KeyCode.COMMAND));
    }

    private void setTitle(String title) {
        getStage().setTitle(title);
    }

    private void importFile(File file) {
        context.importFile(file, (String tableNm) -> {
            Platform.runLater(() -> {
                tableList.add(tableNm);
                tableList.sort((a, b) -> a.compareTo(b));
                tableListView.setItems(tableList);
            });
        });
        setTitle(file.getName());
    }
}
