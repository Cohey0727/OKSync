package common.db;

public class NoDataTableExeption extends Exception {
    public NoDataTableExeption(String msg) {
        super("No Data Table Exeption :" + msg);
    }
}
