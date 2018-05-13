package common.ini.version;

import java.util.ArrayList;

public class VersionManager {
    public static final ArrayList<Version> verList = VersionDao.loadVersion();;

    public static Version getLatestVersion() {
        return verList.get(0);
    }
}
