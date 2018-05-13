package common.system.data;

import java.io.File;
import java.sql.Connection;

public abstract class FileImporter {
    public abstract void execute(Connection con, File file);

    public static FileImporter createFileImporter(FileType type) {
        return null;

    }

    public enum FileType {
        CSV(".csv"), DB(".db");
        private final String[] extension;
        FileType(String... _extension) {
            extension = _extension;
        }
    }
}
