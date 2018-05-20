package common.db;

public class AlreadyExistsExeption extends Exception {
    public AlreadyExistsExeption(String table) {
        super("Already Exists Exeption. Tbale name : " + table);
    }

}
