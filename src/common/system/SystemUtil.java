package common.system;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SystemUtil {
    private static Connection con = null;
    private static final String INI_DB_URL = "jdbc:sqlite:ini.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static URL getResourceURL(String name, ResourceType type) {
        SystemUtil object = new SystemUtil();
        return object.getClass().getClassLoader().getResource(type.dir + "/" + name);
    }

    public enum ResourceType {
        CSS("css"), IMAGE("img"), FXML("fxml"), PROP("properties"), INI("ini");
        public final String dir;
        ResourceType(String dir) {
            this.dir = dir;
        }
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            createConnection();
        }
        return con;
    }

    private static void createConnection() throws SQLException {
        con = DriverManager.getConnection(INI_DB_URL);
        con.setAutoCommit(false);
    }
}
