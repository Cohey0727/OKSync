package common.ini.version;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.system.SystemUtil;

public class VersionDao {
    static ArrayList<Version> loadVersion() {
        ArrayList<Version> verList = new ArrayList<Version>();
        Connection con;
        PreparedStatement ps;
        try {
            con = SystemUtil.getConnection();
            String sql = "SELECT * FROM VERSION ORDER BY MAJOR DESC, MINOR DESC, REVISION DESC";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                verList.add(new Version(rs.getInt("major"), rs.getInt("minor"), rs.getInt("revision")));
            }
            return verList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
