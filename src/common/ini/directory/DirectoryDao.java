package common.ini.directory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import common.system.SystemUtil;

public class DirectoryDao {
    static HashMap<String, Directory> load() {
        HashMap<String, Directory> dirList = new HashMap<String, Directory>();
        Connection con;
        PreparedStatement ps;
        try {
            con = SystemUtil.getConnection();
            String sql = "SELECT * FROM DIRECTORY ORDER BY ID";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dirList.put(rs.getString("ID"), new Directory(rs.getString("ID"), rs.getString("PATH")));
            }
            return dirList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void update(String key, String path) {
        Connection con;
        PreparedStatement ps;
        try {
            con = SystemUtil.getConnection();
            String sql = "UPDATE DIRECTORY SET PATH = ? WHERE ID = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, path);
            ps.setString(2, key);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
