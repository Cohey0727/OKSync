package common.system.log;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class OKSyncPrintStream extends PrintStream {
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ");
    private static final Logger logger = Logger.getLogger("Main");
    public OKSyncPrintStream(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    private String getDateTime() {
        return FORMATTER.format(new Date());
    }

    @Override
    public void print(boolean b) {
        super.print(getDateTime() + b);
        logger.info("error");
    }
    @Override
    public void print(char c) {
        super.print(getDateTime() + c);
    }
    @Override
    public void print(char[] s) {
        super.print(getDateTime() + String.valueOf(s));
    }
    @Override
    public void print(double d) {
        super.print(getDateTime() + d);
    }
    @Override
    public void print(float f) {
        super.print(getDateTime() + f);
    }
    @Override
    public void print(int i) {
        super.print(getDateTime() + i);
    }
    @Override
    public void print(long l) {
        super.print(getDateTime() + l);
    }
    @Override
    public void print(String s) {
        super.print(getDateTime() + s);
    }
    @Override
    public void print(Object obj) {
        super.print(getDateTime() + String.valueOf(obj));
    }
}