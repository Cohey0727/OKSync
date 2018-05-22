package application.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.stream.Stream;

import common.db.DataSourceFactory;
import common.db.SQLKeyWord;
import common.db.TableData;
import common.db.TableViewBuilder;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SqlTab extends Tab {
    private final TextArea sqlTextArea = new TextArea();
    private final TableView<ObservableList<String>> resultTableView = new TableView<ObservableList<String>>();
    private final SplitPane splitPane = new SplitPane();

    public SqlTab(String tableNm, EventHandler<KeyEvent> handle) {
        super(tableNm);
        this.getStyleClass().add("sql-tab");
        sqlTextArea.setText("SELECT * FROM " + tableNm);
        sqlTextArea.setPromptText("SQL");
        sqlTextArea.getStyleClass().add("sqlTextArea");
        sqlTextArea.getStyleClass().add("sql-text-area");
        sqlTextArea.addEventFilter(KeyEvent.KEY_PRESSED, handle);
        sqlTextArea.addEventFilter(KeyEvent.KEY_PRESSED, (e) -> {
            KeyCode code = e.getCode();
            if (code == KeyCode.TAB && !e.isShiftDown() && !e.isControlDown()) {
                e.consume();
                Node node = (Node) e.getSource();
                KeyEvent newEvent = new KeyEvent(e.getSource(),
                        e.getTarget(), e.getEventType(),
                        e.getCharacter(), e.getText(),
                        e.getCode(), e.isShiftDown(),
                        true, e.isAltDown(),
                        e.isMetaDown());
                node.fireEvent(newEvent);
            }
        });

        resultTableView.getSelectionModel().setCellSelectionEnabled(true);
        resultTableView.setPlaceholder(new Label("No Data"));
        resultTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        resultTableView.setOnKeyPressed((e) -> {
            switch (e.getCode()) {
            case END:
                resultTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                for (int i = 0; i < resultTableView.getColumns().size(); i++) {
                    resultTableView.getSelectionModel().selectRightCell();
                }
                resultTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

                break;
            case HOME:
                resultTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                for (int i = 0; i < resultTableView.getColumns().size(); i++) {
                    resultTableView.getSelectionModel().selectLeftCell();
                }
                resultTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                resultTableView.getColumns().forEach((col) -> {
                    col.setResizable(true);
                });

                break;
            default:
                break;
            }
        });

        this.setClosable(true);
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.setDividerPositions(0.25);
        splitPane.getItems().add(sqlTextArea);
        splitPane.getItems().add(resultTableView);
        this.setContent(splitPane);
    }

    public String getSQL() {
        return sqlTextArea.getText().trim().isEmpty() ? resetSQL() : sqlTextArea.getText();
    }

    public String resetSQL() {
        String sql = "SELECT * FROM " + getText();
        sqlTextArea.setText(sql);
        return sql;
    }

    public void addAndEqual() {
        String colNm = resultTableView.getSelectionModel().getSelectedCells().get(0).getTableColumn().getText();
        StringBuilder sql = new StringBuilder(sqlTextArea.getText() + " \n");
        if (sql.toString().toUpperCase().contains(SQLKeyWord.WHERE.toString())) {
            sql.append("    " + SQLKeyWord.AND.toString() + " ");
        } else {
            sql.append(SQLKeyWord.WHERE.toString() + " ");
        }
        sql.append(colNm + " = ''");
        sqlTextArea.setText(sql.toString());
        sqlTextArea.requestFocus();
        sqlTextArea.positionCaret(sql.toString().length() - 1);
    }

    public void addOrEqual() {
        String colNm = resultTableView.getSelectionModel().getSelectedCells().get(0).getTableColumn().getText();
        StringBuilder sql = new StringBuilder(sqlTextArea.getText() + " \n");
        if (sql.toString().toUpperCase().contains(SQLKeyWord.WHERE.toString())) {
            sql.append("       " + SQLKeyWord.OR.toString() + " ");
        } else {
            sql.append(SQLKeyWord.WHERE.toString() + " ");
        }
        sql.append(colNm + " = ''");
        sqlTextArea.setText(sql.toString());
        sqlTextArea.requestFocus();
        sqlTextArea.positionCaret(sql.toString().length() - 1);
    }

    public void addAndLike() {
        String colNm = resultTableView.getSelectionModel().getSelectedCells().get(0).getTableColumn().getText();
        StringBuilder sql = new StringBuilder(sqlTextArea.getText() + " \n");
        if (sql.toString().toUpperCase().contains(SQLKeyWord.WHERE.toString())) {
            sql.append("    " + SQLKeyWord.AND.toString() + " ");
        } else {
            sql.append(SQLKeyWord.WHERE.toString() + " ");
        }
        sql.append(colNm + " LIKE '%%'");
        sqlTextArea.setText(sql.toString());
        sqlTextArea.requestFocus();
        sqlTextArea.positionCaret(sql.toString().length() - 2);
    }

    public void addOrLike() {
        String colNm = resultTableView.getSelectionModel().getSelectedCells().get(0).getTableColumn().getText();
        StringBuilder sql = new StringBuilder(sqlTextArea.getText() + " \n");
        if (sql.toString().toUpperCase().contains(SQLKeyWord.WHERE.toString())) {
            sql.append("       " + SQLKeyWord.OR.toString() + " ");
        } else {
            sql.append(SQLKeyWord.WHERE.toString() + " ");
        }
        sql.append(colNm + " LIKE '%%'");
        sqlTextArea.setText(sql.toString());
        sqlTextArea.requestFocus();
        sqlTextArea.positionCaret(sql.toString().length() - 2);
    }

    public void applyTableData(TableData td) {
        resultTableView.getColumns().setAll(td.getColumns());
        resultTableView.setItems(td.getData());
    }

    public ObservableList<TablePosition> getSelectedCellList() {
        return resultTableView.getSelectionModel().getSelectedCells();
    }

    public String getSelectedCellText() {
        ObservableList<TablePosition> cell = getSelectedCellList();
        StringBuilder sb = new StringBuilder();
        int prevRow = -1;
        if (cell != null) {
            for (TablePosition<?, ?> pos : cell) {
                if (prevRow < 0) {
                } else if (pos.getRow() > prevRow) {
                    sb.append("\n");
                } else {
                    sb.append("\t");
                }
                sb.append(pos.getTableColumn().getCellData(pos.getRow()).toString());
                prevRow = pos.getRow();
            }
        }
        return sb.toString();
    }

    public void executeSQL() throws Exception {
        try (Connection con = DataSourceFactory.getConnection()) {
            String sql = getSQL();
            SqlType type = SqlType.getBySql(sql);
            switch (type) {
            case INSERT:
            case UPDATE:
            case DELETE:
            case CREATE:
            case DROP:
            case TRUNCATE:
                int count = con.createStatement().executeUpdate(sql);
                ButtonType ok = new ButtonType("OK", ButtonData.OK_DONE);
                ButtonType cancel = new ButtonType("CANCEL", ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(AlertType.CONFIRMATION, "", ok, cancel);
                alert.setTitle("Execute SQL");
                alert.getDialogPane()
                        .setHeaderText("Execute following sql and " + type.key.toLowerCase() + " " + count + " records.\nDo you want to continue?");
                alert.setContentText(sql);
                ButtonType button = alert.showAndWait().orElse(ButtonType.CANCEL);
                if (button == ok) {
                    con.commit();
                } else {
                    con.rollback();
                }
                break;
            case SELECT:
            default:
                TableData td;
                td = TableViewBuilder.build(con, sql);
                applyTableData(td);
                break;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " SQL:" + getSQL());
            throw new SQLException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public String getInsertSQL() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int max_records = 256;
        for (ObservableList<String> rec : resultTableView.getItems()) {
            if (i % max_records == 0) {
                sb.append("INSERT ALL\n");
            }
            sb.append("    INTO ").append(getText()).append(" VALUES (");
            for (String cell : rec) {
                switch (getDataType(cell)) {
                case Types.TIME:
                    sb.append("TO_DATE('").append(cell.replace('-', '/')).append("', 'YYYY/MM/DD HH24:MI:SS'),");
                    break;
                case Types.DATE:
                    sb.append("TO_DATE('").append(cell.replace('-', '/')).append("', 'YYYY/MM/DD'),");
                    break;
                case Types.NVARCHAR:
                default:
                    sb.append("'").append(cell).append("',");
                    break;
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")\n");
            if (++i % max_records == 0 || i == resultTableView.getItems().size()) {
                sb.append("SELECT * FROM DUAL;\n");
            }
        }
        return sb.toString();
    }

    private int getDataType(String cell) {
        if (cell.matches("(\\d{4})/(\\d{2})/(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})") || cell.matches("(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})")) {
            return Types.TIME;
        } else if (cell.matches("(\\d{4})/(\\d{2})/(\\d{2})") || cell.matches("(\\d{4})-(\\d{2})-(\\d{2})")) {
            return Types.DATE;
        }
        return Types.NVARCHAR;
    }

    enum SqlType {
        SELECT("SELECT"), UPDATE("UPDATE"), INSERT("INSERT"), DELETE("DELETE"), CREATE("CREATE"), TRUNCATE("TRUNCATE"), DROP("DROP");
        SqlType(String key) {
            this.key = key;
        }
        public final String key;

        public static SqlType getBySql(String sql) {
            return Stream.of(SqlType.values()).filter(type -> sql.trim().toUpperCase().indexOf(type.key) > -1)
                    .min((a, b) -> sql.trim().toUpperCase().indexOf(a.key) - sql.trim().toUpperCase().indexOf(b.key)).get();
        }
    }
}
