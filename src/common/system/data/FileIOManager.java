package common.system.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import common.ini.directory.DirectoryManager;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FileIOManager {
    public static File open(Stage stage, String title, ExtensionFilter... extensionFilters) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        File dir = getMostRecentlyDirectry();
        if (dir != null && dir.isDirectory()) {
            fileChooser.setInitialDirectory(dir);
        }
        fileChooser.getExtensionFilters().addAll(extensionFilters);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            updateMostRecentlyDirectry(file.getParent());
        }
        return file;
    }

    public static File save(Stage stage, String title, File tagert, ExtensionFilter... extensionFilters) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        File dir = getMostRecentlyDirectry();
        if (dir != null && dir.isDirectory()) {
            fileChooser.setInitialDirectory(dir);
        }
        fileChooser.getExtensionFilters().addAll(extensionFilters);
        File outFile = fileChooser.showSaveDialog(stage);
        if (!tagert.exists() || outFile == null) {
            return null;
        }
        try {
            updateMostRecentlyDirectry(outFile.getParent());
            Files.copy(tagert.toPath(), outFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outFile;
    }

    private static File getMostRecentlyDirectry() {
        return new File(DirectoryManager.getDirectory("MostRecently").getPath());
    }

    private static void updateMostRecentlyDirectry(String path) {
        DirectoryManager.updateDirectory("MostRecently", path);
    }
}
