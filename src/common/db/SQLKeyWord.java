package common.db;

public enum SQLKeyWord {
    SELECT("SELECT"),
    FROM("FROM"),
    WHERE("WHERE"),
    AND("AND"),
    OR("OR"),
    ORDER("ORDER"),
    BY("BY"),
    ASC("ASC"),
    DESC("DESC"),
    HAVING("HAVING"),
    GROUP("GROUP");
    SQLKeyWord(String _name) {
        name = _name;
    }
    private final String name;
    @Override
    public String toString() {
        return name;
    }
}
