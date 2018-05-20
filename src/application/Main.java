package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import application.form.MainForm;
import common.ini.preference.DBInitializer;
import common.system.log.MultiOutputStream;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static String[] args;
    @Override
    public void start(Stage primaryStage) {
        try {
            DBInitializer.execute();
            InitializeTempFile();
            MainForm form = new MainForm();
            form.show(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Main.args = args;
        setOutPutStream();
        launch(args);
    }

    private void InitializeTempFile() {
        File dir = new File(new File(".").getAbsoluteFile().getParent());
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.exists() == false) {
                continue;
            } else if (file.isFile()) {
                if (file.getPath().matches(".*Temp_.............\\.db")) {
                    if (file.delete()) {
                        System.out.println("Delete " + file.getPath());
                    } else {
                        System.out.println(file.getPath() + " is opened by other application.");
                    }
                }
            }
        }
    }

    private static void setOutPutStream() throws IOException {
        PrintStream oldPrintStream = System.out;
        FileOutputStream sysOutStreeam;
        FileOutputStream sysErrStreeam;
        File outfile = new File("OKSyncOut.log");
        File errfile = new File("OKSyncErr.log");
        sysOutStreeam = new FileOutputStream(outfile);
        sysErrStreeam = new FileOutputStream(errfile);
        MultiOutputStream sysOutMulti = new MultiOutputStream(new PrintStream(sysOutStreeam), oldPrintStream);
        MultiOutputStream sysErrMulti = new MultiOutputStream(new PrintStream(sysErrStreeam), oldPrintStream);
        System.setOut(new PrintStream(sysOutMulti));
        System.setErr(new PrintStream(sysErrMulti));
    }
}
