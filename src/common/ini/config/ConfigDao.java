package common.ini.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.system.SystemUtil;

public class ConfigDao {
    private static String LOAD_SQL = "SELECT * FROM CONFIG";

    public static Map<String, String> load() {
        HashMap<String, String> config = new HashMap<String, String>();
        try (Connection con = SystemUtil.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery(LOAD_SQL);
            while (rs.next()) {
                config.put(rs.getString("KEY"), rs.getString("VALUE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return config;
    }

}
