package common.db;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;

public class TableData {
    private final ObservableList<ObservableList<String>> data;
    private final ObservableList<TableColumn<ObservableList<String>, String>> columns;

    public TableData(ObservableList<ObservableList<String>> data,
            ObservableList<TableColumn<ObservableList<String>, String>> columns) {
        this.data = data;
        this.columns = columns;
    }

    public ObservableList<ObservableList<String>> getData() {
        return data;
    }

    public ObservableList<TableColumn<ObservableList<String>, String>> getColumns() {
        return columns;
    }

    public void setAllEditable(boolean isEditable) {
        columns.stream().forEach((column) -> {
            column.setEditable(isEditable);
            column.setCellFactory(TextFieldTableCell.forTableColumn());
        });
    }

    public void setOnEditCommit(EventHandler<TableColumn.CellEditEvent<ObservableList<String>, String>> e) {
        columns.stream().forEach((column) -> {
            column.setOnEditCommit(e);
        });
    }
}
