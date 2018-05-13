package common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import common.system.SystemUtil;

public class DataSourceFactory {

    private static DataSource ds = null;
    private static Properties props = new Properties();
    private static String DB_PRIFIX = "jdbc:sqlite:";

    private static String LOAD_PROP_SQL = "SELECT * FROM DBCP_PROPS";

    static {
        try {
            Connection con = SystemUtil.getConnection();
            Properties props = loadProperties(con);
            ds = BasicDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (Connection con = getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT 1");
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        if (ds == null) {
            throw new IllegalStateException("DataSource initialize failed.");
        }
        return ds;
    }

    private static Properties loadProperties(Connection con) {
        String name = createTempDBName();
        props.put("url", DB_PRIFIX + name);
        props.put("path", name);
        ResultSet rs;
        try {
            rs = con.createStatement().executeQuery(LOAD_PROP_SQL);
            while (rs.next()) {
                props.put(rs.getString("key"), rs.getString("value"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return props;
    }

    private static String createTempDBName() {
        return "Temp_" + System.currentTimeMillis() + ".db";
    }

    public static void updateURL(String url) throws Exception {
        props.put("url", DB_PRIFIX + url);
        props.put("path", url);
        ds = BasicDataSourceFactory.createDataSource(props);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static String getProp(String key) {
        return props.getProperty(key);
    }
}