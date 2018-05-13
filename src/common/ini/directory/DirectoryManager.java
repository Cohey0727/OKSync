package common.ini.directory;

import java.util.HashMap;

public class DirectoryManager {
    public static final HashMap<String, Directory> dirMap = DirectoryDao.load();
    public static Directory getDirectory(String key) {
        return dirMap.get(key);
    }
    public static void updateDirectory(String key, String path) {
        dirMap.put(key, new Directory(key, path));
        DirectoryDao.update(key, path);
    }
}
