package common.ini.config;

import java.util.Map;

public class ConfigManager {
    private static Map<String, String> config = null;
    public static String get(String key) {
        if (config == null) {
            config = ConfigDao.load();
        }
        return config.get(key);
    }
}
