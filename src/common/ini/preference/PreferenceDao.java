package common.ini.preference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import common.system.SystemUtil;

public class PreferenceDao {
    public static Preference[] load() {
        ArrayList<Preference> rsList = new ArrayList<Preference>();
        Connection con;
        PreparedStatement ps;
        try {
            con = SystemUtil.getConnection();
            String sql = "SELECT * FROM PREFERENCE";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Preference preference = new Preference(rs);
                rsList.add(preference);
            }
            return rsList.toArray(new Preference[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
