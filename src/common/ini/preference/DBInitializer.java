package common.ini.preference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.system.SystemUtil;
import common.system.SystemUtil.ResourceType;

public class DBInitializer {
    public static final String INI_FILE_PATH = "ini/db.ini";

    public static synchronized void execute() {
        /*iniファイル読み込み*/
        try {
            ArrayList<ArrayList<String>> sqlGroupList = getSQLGroupList();
            Connection con = SystemUtil.getConnection();
            sqlGroupList.stream().forEach((sqlList) -> {
                if (sqlList.size() == 0) {
                    return;
                }
                String condSql = sqlList.remove(0);
                ResultSet rs;
                try {
                    con.setAutoCommit(true);
                    rs = con.createStatement().executeQuery(condSql);
                    if (!rs.next()) {
                        return;
                    } else {
                        if (rs.getInt(1) == 0) {
                            sqlList.stream().forEach((sql) -> {
                                try {
                                    con.createStatement().executeUpdate(sql);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<ArrayList<String>> getSQLGroupList() throws IOException {
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        InputStream stream = SystemUtil.getResourceURL("db.ini", ResourceType.INI).openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String line;
        ArrayList<String> dataBlock = new ArrayList<String>();
        while ((line = br.readLine()) != null) {
            if (line.length() == 0) {
                list.add(dataBlock);
                dataBlock = new ArrayList<String>();
            } else {
                dataBlock.add(line);
            }
        }
        list.add(dataBlock);
        br.close();
        return list;
    }

}
