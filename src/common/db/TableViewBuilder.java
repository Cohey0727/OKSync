package common.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class TableViewBuilder {

    public static TableData build(Connection con, String _sql) throws SQLException {
        ObservableList<ObservableList<String>> data;
        ObservableList<TableColumn<ObservableList<String>, String>> columns;
        data = FXCollections.observableArrayList();
        columns = FXCollections.observableArrayList();
        ResultSet rs = con.createStatement().executeQuery(_sql);
        TableColumn<ObservableList<String>, String> col;
        col = new TableColumn<ObservableList<String>, String>("No");
        col.setCellValueFactory(new SetCellProperty(0));
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            //We are using non property style for making dynamic table
            col = new TableColumn<ObservableList<String>, String>(rs.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(new SetCellProperty(i));
            col.setMinWidth(144);
            col.setResizable(true);
            columns.add(col);
        }

        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }
        return new TableData(data, columns);
    }

    private static class SetCellProperty
            implements Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>> {

        private final int i;

        public SetCellProperty(int i) {
            this.i = i;
        }

        @Override
        public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
            String val = param.getValue().get(i) != null ? param.getValue().get(i).toString() : "";
            return new SimpleStringProperty(val);
        }
    }

}
