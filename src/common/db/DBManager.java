package common.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import common.system.data.KakuninViewParser;

public class DBManager {

    public String[] getTableList(boolean isDisplayEmpty) {
        Connection con = null;
        ResultSet rs;
        try {
            con = DataSourceFactory.getDataSource().getConnection();
            ArrayList<String> tableList = new ArrayList<String>();
            rs = con.createStatement().executeQuery(getTableListSql(isDisplayEmpty));
            while (rs.next()) {
                tableList.add(rs.getString("NAME"));
            }
            return tableList.toArray(new String[tableList.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.commit();
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return null;
    }

    private String getTableListSql(boolean isDisplayEmpty) {
        if (isDisplayEmpty) {
            return "SELECT NAME FROM SQLITE_MASTER WHERE TYPE='table'";
        } else {
            return "SELECT NAME FROM SQLITE_MASTER WHERE TYPE='table'  AND sql NOT LIKE '%NoData%'";
        }
    }

    public void importFile(File file, BuildTableListCallback callback) {
        ArrayList<ArrayList<String>> dataList;
        ArrayList<ArrayList<String>> emptyDataList = new ArrayList<ArrayList<String>>();
        ExecutorService executor = null;
        try {
            dataList = KakuninViewParser.divide(file);
            int MAX_THREADS = 8;
            executor = Executors.newFixedThreadPool(MAX_THREADS);
            List<Future<?>> list = new ArrayList<Future<?>>();
            for (ArrayList<String> data : dataList) {
                if (data.size() <= 2) {
                    emptyDataList.add(data);
                } else {
                    executor.submit(new RunnableDBBuilder(data, callback));
                }
            }
            //終了待ち
            for (Future<?> future : list) {
                future.get();
            }
            for (ArrayList<String> data : emptyDataList) {
                executor.submit(new RunnableDBBuilder(data, callback));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (executor != null) {
                executor.shutdown();
            }
        }
    }

}