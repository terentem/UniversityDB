package org.example.sql;

public class SqlScript {
    String sql = "";

    public String getSql(String parameter) {
        if (parameter.equals("professors")) {
            sql = "SELECT * FROM professors";
        }
        return sql;
    }
}
