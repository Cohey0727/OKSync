package common.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVParser;

import common.system.data.KakuninViewParser;

public class RunnableDBBuilder implements Runnable {
    private final ArrayList<String> data;
    private final BuildTableListCallback callback;
    private final String[] DUMMY_HEADER = { "NoData" };
    private static final int MAX_RECORD = 500;

    public RunnableDBBuilder(ArrayList<String> _data, BuildTableListCallback _callBack) {
        data = _data;
        callback = _callBack;
    }

    @Override
    public void run() {
        String tableName = KakuninViewParser.getTableName(data.remove(0));
        if (tableName == null) {
            return;
        }
        String[] headers;
        if (data.size() <= 1) {
            headers = DUMMY_HEADER;
        } else {
            headers = KakuninViewParser.getHeaders(data.remove(0));
        }
        try {
            try {
                createTable(tableName, headers);
                callback.run(tableName);
            } catch (AlreadyExistsExeption e) {
                System.out.println(tableName + " : " + e.getMessage());
            }
            createRecord(tableName, headers, data);
        } catch (SQLException | NoDataTableExeption e) {
            System.err.println(tableName + " : " + e.getMessage());
        }
    }

    private static String getCreateTableSQL(String tableName, String[] headers) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(tableName);
        sb.append(" (");
        for (String header : headers) {
            sb.append("'" + header + "' TEXT,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" )");
        return sb.toString();
    }

    private static String getInsetSQL(final String tableName, final List<String> list, final int size) throws IOException {
        CSVParser cp = new CSVParser();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(tableName).append(" SELECT ");
        String[] firstValues = cp.parseLine(list.remove(0));
        for (String value : firstValues) {
            sb.append("'" + value + "',");
        }
        sb.deleteCharAt(sb.length() - 1);
        list.stream().forEach(line -> {
            try {
                String[] values = cp.parseLine(line);
                if (values.length == size) {
                    sb.append(" UNION ALL SELECT ");
                    for (String value : values) {
                        sb.append("'" + value + "',");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                }
            } catch (IOException e) {
            }
        });
        return sb.toString();
    }

    private void createTable(String tableName, String[] headers) throws SQLException, AlreadyExistsExeption, NoDataTableExeption {
        try (Connection con = DataSourceFactory.getConnection()) {
            con.createStatement().execute(getCreateTableSQL(tableName, headers));
            con.commit();
        } catch (SQLException e) {
            if (e.getErrorCode() == 5) {
                throw new AlreadyExistsExeption(tableName);
            }
            throw e;
        }
    }

    private void createRecord(String tableName, String[] headers, ArrayList<String> data) {
        try (Connection con = DataSourceFactory.getConnection()) {
            for (int i = 0; i < data.size(); i += MAX_RECORD) {
                int fromIndex = i;
                int toIndex = (i + MAX_RECORD) <= data.size() ? (i + MAX_RECORD) : data.size();
                String sql = null;
                sql = getInsetSQL(tableName, data.subList(fromIndex, toIndex), headers.length);
                con.createStatement().executeUpdate(sql);
            }
            con.commit();
        } catch (IOException | SQLException e) {
            System.out.println(tableName + " Message:" + e.getMessage());
        }
    }
}
