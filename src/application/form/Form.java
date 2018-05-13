package application.form;

import java.io.IOException;

import javafx.scene.Parent;
import javafx.stage.Stage;

public interface Form {
    abstract Parent load() throws IOException;
    abstract void show(Stage stage);
    abstract void close();
}
