package application.form;

import java.io.IOException;

import javafx.scene.Scene;
import javafx.stage.Stage;

public interface Form {
    abstract Scene load() throws IOException;
    abstract void show(Stage stage);
    abstract void close();
}
